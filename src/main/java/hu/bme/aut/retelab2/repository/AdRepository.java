package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class AdRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Ad save(Ad ad) {return em.merge(ad);}

    public List<Ad> findByPrice(int minPrice, int maxPrice){
        return em.createQuery("SELECT a FROM Ad a WHERE a.price BETWEEN ?1 AND ?2",Ad.class).setParameter(1,minPrice).setParameter(2,maxPrice).getResultList();
    }

    @Transactional
    public Ad updateAd(Ad ad){
        Ad result = em.find(Ad.class, ad.getId());
        if(result != null)
        {
            ad.setCreated(result.getCreated());
            if(ad.getOwnerId().equals(result.getOwnerId())) save(ad);
            else return ad;
        }
        return result;
    }

    public List<Ad> findByTag(String tag){
        return em.createQuery("SELECT a FROM Ad a JOIN a.tags result WHERE result = ?1",Ad.class).setParameter(1,tag).getResultList();
    }

    public List<Ad> getAll() {return em.createQuery("SELECT a FROM Ad a", Ad.class).getResultList();}

    public void deleteAd(Ad ad) {em.remove(ad);}
}
