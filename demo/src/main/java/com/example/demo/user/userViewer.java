package com.example.demo.user;

import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.image.imageBasic;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("entityViewer")
public class userViewer extends userAccount {
    //constructor
    public userViewer(){}

    public userViewer(String name, String login, String rawPassword/*, imageBasic Logo*/){
         this.name = name;
         this.login = login;
         this.password = hashPassword(rawPassword);
         /*this.logo = logo;*/
         this.dateCreated = System.currentTimeMillis();
    }
    //additional functions
    private String hashPassword(String rawPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }

    public  Boolean checkUserAccount(String login, String password){
       if(this.login.equals(login)){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(passwordEncoder.matches(password, this.password)){
                return true;
            }
        }
        return false;
    }
    //setters
    public void changeDetail(String name, imageBasic logo, String login, String password){
        if(checkUserAccount(login, password)){
            this.name = name;
            this.logo = logo;
        }
    }
    public void changePassowrd(String login, String password, String newPassword){
        if(checkUserAccount(login, password)){
            this.password = hashPassword(newPassword);
        }
    }
    //login getters
    public Map<Integer, Object> loginUser(String login, String password) {
        Map<Integer, Object> fail = new HashMap<>();
        fail.put(1, false);
        fail.put(2, "");
        fail.put(3, "");
    
        Map<Integer, Object> newMapData = new HashMap<>();
    
        if (checkUserAccount(login, password)) {
            try {
                newMapData.put(1, true);
                newMapData.put(2, id);
                newMapData.put(3, logo);
            } catch (Exception e) {
                e.printStackTrace();
                return fail;
            }
        } else {
            return fail;
        }
    
        return newMapData;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public Long getId(){
        return id;
    }
}
