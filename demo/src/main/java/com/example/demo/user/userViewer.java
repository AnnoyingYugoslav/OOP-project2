package com.example.demo.user;

import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.image.imageBasic;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "normal")
public class userViewer extends userAccount {
    //constructor
    public userViewer(){}

    public userViewer(String name, String login, String rawPassword){
         this.name = name;
         this.login = login;
         this.password = hashPassword(rawPassword);
         this.dateCreated = System.currentTimeMillis();
    }
    //additional functions
}
