package image;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class imageBasic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageBase64;
    private Boolean isPublic;
    private List<String> tags;
    private Long lastModified;
    private Long dateCreated;

    //constructors
    public imageBasic(){
         this.lastModified = System.currentTimeMillis();
        this.dateCreated = System.currentTimeMillis();
    }

    public imageBasic(String imageBase64, Boolean isPublic, List<String> tags){
        this.imageBase64 = imageBase64;
        this.tags = tags;
        this.isPublic = isPublic;
        this.lastModified = System.currentTimeMillis();
        this.dateCreated = System.currentTimeMillis();
    }
    //setters
    public void newModification(){
        this.lastModified = System.currentTimeMillis();
    }
    
    //getters
    public String getImage(){
        return this.imageBase64;
    }
    public Boolean getPublic(){
        return this.isPublic;
    }
    public List<String> getTags(){
        return this.tags;
    }
    public Long getModification(){
        return this.lastModified;
    }
    public Long getCreated(){
        return this.dateCreated;
    }

}
