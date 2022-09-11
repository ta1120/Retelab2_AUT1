package hu.bme.aut.retelab2.domain;

import hu.bme.aut.retelab2.SecretGenerator;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ad {

    @Id
    @GeneratedValue
    private Long id;

    private String ownerId;

    private String title;

    private String description;

    private int price;

    private LocalDateTime created;

    private LocalDateTime endOfLife;

    @ElementCollection
    private List<String> tags = new ArrayList<>();




    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getOwnerId() {return ownerId;}
    public void setOwnerId(String ownerId) {this.ownerId = ownerId;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public int getPrice() {return price;}
    public void setPrice(int price) {this.price = price;}

    public LocalDateTime getCreated() {return created;}
    public void setCreated(LocalDateTime created) {this.created = created;}

    public LocalDateTime getEndOfLife() {return endOfLife;}
    public void setEndOfLife(LocalDateTime endOfLife) {this.endOfLife = endOfLife;}

    public List<String> getTags() {return tags;}
    public void setTags(List<String> tags) {this.tags = tags;}
}
