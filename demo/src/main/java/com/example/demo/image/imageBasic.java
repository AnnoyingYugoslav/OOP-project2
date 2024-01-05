package com.example.demo.image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.demo.user.userAccount;
import com.example.demo.user.userArtist;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class imageBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String imageData;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private userArtist artist;

    public userArtist getArtist() {
        return artist;
    }
    public void setArtist(userArtist artist) {
        this.artist = artist;
    }

    private Boolean isPublic;
    private Long lastModified;
    private Long dateCreated;

    @ElementCollection
    private Set<String> tags;

    public imageBasic(){
        this.imageData = "fail";
    }

    public imageBasic(String imageData, Boolean isPublic, userArtist user, Long dateCreated) {
        this.imageData = imageData;
        this.isPublic = isPublic;
        this.dateCreated = dateCreated;
        this.dateCreated = dateCreated;
        this.lastModified = dateCreated;
        this.artist = user;
    }
    public imageBasic(String imageData, Boolean isPublic, userArtist user) {
        this.artist = user;
        this.imageData = imageData;
        this.isPublic = isPublic;
        this.dateCreated = System.currentTimeMillis();
        this.lastModified = System.currentTimeMillis();
    }
    public void newModification(){
        this.lastModified = System.currentTimeMillis();
    }
    


    //getters
 
    public void addTags(Set<String> additionalTags) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        tags.addAll(additionalTags);
    }
    public Set<String> getTags() {
        return tags;
    }
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Boolean getPublic(){
        return this.isPublic;
    }
    public Long getModification(){
        return this.lastModified;
    }
    public Long getCreated(){
        return this.dateCreated;
    }
    public Long getId() {
        return id;
    }
    public String getImageData() {
        return imageData;
    }
    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

}
