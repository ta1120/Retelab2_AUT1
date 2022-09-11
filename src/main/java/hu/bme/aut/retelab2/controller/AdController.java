package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.SecretGenerator;
import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {

    @Autowired
    private AdRepository adRepository;

    @PostMapping
    public Ad create(@RequestBody Ad ad){
        ad.setId(null);
        ad.setCreated(LocalDateTime.now());
        ad.setOwnerId(SecretGenerator.generate());
        return adRepository.save(ad);
    }

    @GetMapping
    public List<Ad> getByPrice(@RequestParam(required = false, defaultValue = "0") int minPrice, @RequestParam(required = false, defaultValue = "10000000") int maxPrice){
        List<Ad> result = adRepository.findByPrice(minPrice, maxPrice);
        for(Ad a : result) { a.setOwnerId("");}
        return result;
    }

    @PutMapping
    public ResponseEntity<Ad> update(@RequestBody Ad ad){
        Ad a = adRepository.updateAd(ad);
        if(a == ad)return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if(a != null) return ResponseEntity.ok().build();
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("{tag}")
    public List<Ad> getByTag(@PathVariable String tag){
        List<Ad> result = adRepository.findByTag(tag);
        for(Ad a : result) { a.setOwnerId("");}
        return result;
    }

    @Scheduled(fixedDelay=60000)
    @Transactional
    public void manageLifecycle(){
        List<Ad> ads = adRepository.getAll();
        for(Ad a : ads){
            LocalDateTime eol = a.getEndOfLife();
            if(eol != null){
                if(eol.isBefore(LocalDateTime.now())) adRepository.deleteAd(a);
            }
        }
    }
}
