package com.example.demo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.image.imageBasic;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.transaction.Transactional;
import jakarta.persistence.DiscriminatorType;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@MappedSuperclass
public abstract class userAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected String login;
    protected String password; //needs to be hashed
    protected Long dateCreated;

    protected String hashPassword(String rawPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPassword);
    }
    


   // public userAccount(){}

    //public userAccount(String name, String login, String rawPassword, String Logo){
    //    this.name = name;
    //    this.login = login;
    //    this.password = hashPassword(rawPassword);
    //    this.logo = logo;
    //}
    public  Boolean checkUserAccount(String login, String password){
        if(this.login.equals(login)){
         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
             if(passwordEncoder.matches(password, this.password)){
                 return true;
             }
         }
         return false;
     }
     public Integer changeDetail(String name, String login, String password){
        if(checkUserAccount(login, password)){
            this.name = name;
            return 0;
        }
        return 1;
    }
    @Transactional
    public Integer changePassowrd(String login, String password2, String newPassword){
        if(checkUserAccount(login, password2)){
            this.password = hashPassword(newPassword);
            return 0;
        }
        return 1;
    }
    public Map<Integer, Object> loginUser(String login, String password) {
        Map<Integer, Object> fail = new HashMap<>();
        fail.put(1, false);
        fail.put(2, "");
        fail.put(3, "");
    
        Map<Integer, Object> newMapData = new HashMap<>();
    
        if (checkUserAccount(login, password)) {
            try {
                newMapData.put(1, true);
                newMapData.put(2, name);
                newMapData.put(3, /*logo*/"");
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
//    public imageBasic getLogo(){
//        return logo;
//    }

}
