package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.image.imageBasic;
import com.example.demo.repository.UserRepository;
import com.example.demo.user.userAccount;
import com.example.demo.user.userArtist;
import com.example.demo.user.userViewer;

@RestController
public class userAccounthandler {

    @Autowired
    private UserRepository userRepository;

    //test
    @RequestMapping(value = "/con")
    public String sendViaResponseEntity() {
        System.out.println("Hello, World!");
        String test  = "test";
        return test;
    }  
    ///test

    //profile handling
    @PostMapping("/login") //get 1:login and 2:password -> return 1:true, 2:name and 3:logo || 1:false, 2:"", 3:""
    public Map<Integer, Object> loginAccount(@RequestBody Map<Integer, Object> newMapData) {
        Integer loginKey = 1;
        Integer passwordKey = 2;
        String login = (String) newMapData.get(loginKey);
        String password = (String) newMapData.get(passwordKey);
        userAccount user = userRepository.findIdByLogin(login);
        Map<Integer, Object> resultMap = new HashMap<>();
        resultMap.put(1,false);
        if(user instanceof userViewer){
            userViewer viewer = (userViewer) user;
            resultMap = viewer.loginUser(login,password);
           }
           return resultMap;
        }

    @PostMapping("/createaccount") //get 1:login, 2:password and 3:name -> return 1:success || 1:error
    public Map<Integer, Object> createAccount(@RequestBody Map<Integer, Object> newMapData){
        Integer loginKey = 1;
        Integer passwordKey = 2;
        Integer nameKey = 3;
        String login = (String) newMapData.get(loginKey);
        String password = (String) newMapData.get(passwordKey);
        String name = (String) newMapData.get(nameKey);
        Map<Integer, Object> resultMap = new HashMap<>();
        try{
        userAccount user = userRepository.findIdByLogin(login);
        if(user != null){
            resultMap.put(1,false);
            return resultMap;
        }
        resultMap.put(1,true);
        userViewer newUserViewer = new userViewer(name,login,password);
        userRepository.save(newUserViewer);
        return newMapData;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(1,false);
            return resultMap;
        }
    }

    @PostMapping("/changepassword") // get 1:login, 2:password and 3:new password -> return 1:success  || 1:fail
    public Map<Integer, Object> changePassword(@RequestBody Map<Integer, Object> newMapData){
        Integer loginKey = 1;
        Integer passwordKey = 2;
        Integer newpasswordKey = 3;
        String login = (String) newMapData.get(loginKey);
        String password = (String) newMapData.get(passwordKey);
        String newpassword = (String) newMapData.get(newpasswordKey);
        userAccount user = userRepository.findIdByLogin(login);
        Map<Integer, Object> resultMap = new HashMap<>();
        resultMap.put(1,false);
        try{
        if(user instanceof userViewer){
            userViewer viewer = (userViewer) user;
            viewer.changePassowrd(login,password,newpassword);
            resultMap.put(1,true);
            return resultMap;
           }
        } catch (Exception e) {
           return resultMap;
        }
        return resultMap;
    }



    //images handling
    @PostMapping("/getrandomimages") //get 1:int number of images -> return 1: number you get, from 2 to - number+1 : object Images (imageBasic)
    public Map<Integer, Object> getRandomImages(@RequestBody Map<Integer, Object> newMapData){
        List<Long> userArtists = userRepository.findUserArtistIds();
        Integer numberKey = 1;
        Integer number = (Integer) newMapData.get(numberKey);
        Integer counter = 0;
        Map<Integer, Object> resultMap = new HashMap<>();
        for(int i = 0; i < number; i++){
            Integer randomValue = (int) (Math.random() * userArtists.size());
            Long userId = userArtists.get(randomValue);
            userArtist user = userRepository.findUserArtistById(userId);
            List<imageBasic> publicImages= user.getListOfPublicArt();
            if(publicImages.size() > 0){
                Integer randomValue2 = (int) (Math.random() * publicImages.size());
                imageBasic temPorary = publicImages.get(randomValue2);
                resultMap.put(i+2, temPorary);
                counter++;
            }
        }
        resultMap.put(1,counter);
        return resultMap;
    }

    @PostMapping("/getimageinfo") //get image id -> return all image info + creator info
    public String getImageInfo(){
        return "";
    }

    @PostMapping("/gettagimages") //get int number of and list of tags -> return images, ids
    public String getTaggedImages(){
        return "";
    }

    @PostMapping("/getnewimages") //get int number -> return images and ids
    public String getNewImages(){
        return "";
    } 

    @PostMapping("/createimage") //get user and image id, login, password, image, imagename, private -> return success or fail
    public String createImage(){
        return "";
    }
}
