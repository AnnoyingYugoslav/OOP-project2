package com.example.demo.user;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.image.imageBasic;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "artist")
public class userArtist extends userAccount{


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "artist", orphanRemoval = true)
    private List<imageBasic> listOfArt;

    public userArtist(){}

    public userArtist(String name, String login, String rawPassword, String Logo){
        this.name = name;
        this.login = login;
        this.password = hashPassword(rawPassword);
        this.listOfArt = new ArrayList<>();
         //this.logo = logo;
    }

    public void newImage(imageBasic newImageData){
        this.listOfArt.add(newImageData);
    }
    public void removeImage(imageBasic imageData){
        this.listOfArt.remove(imageData);
    }
    public List<imageBasic> getListOfArt(){
        return this.listOfArt;
    }
    public imageBasic getLogo(){
        if(!this.listOfArt.isEmpty()){
            return this.listOfArt.get(0);
        }
        System.out.println("the fuck y mean");
        imageBasic nowe = new imageBasic();
        return nowe;
    }
    public void changeLogo(imageBasic newlogo){
        this.listOfArt.set(0, newlogo);
    }
    public void addImage(imageBasic newImage){
        this.listOfArt.add(newImage);
    }

    /* 
    public List<imageBasic> getListOfPublicArt(){
        List<imageBasic> listOfPublicArt = new ArrayList<>();
        Integer counter = listOfArt.size();
        for(int i = 0; i < counter; i++){
            imageBasic temPorary = listOfArt.get(i);
            if(temPorary.getPublic()){
                listOfPublicArt.add(temPorary);
            }
        }
        return listOfPublicArt;
    }
    */
}