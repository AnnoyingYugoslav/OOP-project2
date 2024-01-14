package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.example.demo.repository.ArtistRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.user.userAccount;
import com.example.demo.user.userArtist;
import com.example.demo.user.userViewer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class userAccounthandler {


    private String convertMapToJson(Map<Integer, Object> map) {
        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the Map to a JSON string
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            // Handle the exception if the conversion fails
            e.printStackTrace(); // You might want to log the error or throw a custom exception
            return "{\"error\": \"Failed to convert Map to JSON\"}";
        }
    }


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ImageRepository imageRepository;

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
    public String loginAccount(@RequestBody Map<Integer, Object> newMapData) {
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
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        user = artistRepository.findIdByLogin(login);
        if(user instanceof userArtist){
            userArtist viewer = (userArtist) user;
            resultMap = viewer.loginUser(login,password);
            resultMap.put(3, viewer.getLogo());
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
        }

    @PostMapping("/createaccount") //get 1:login, 2:password and 3:name -> return 1:success || 1:error
    public String createAccount(@RequestBody Map<Integer, Object> newMapData){
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
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        user = artistRepository.findIdByLogin(login);
        if(user != null){
            resultMap.put(1,false);
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        resultMap.put(1,true);
        userViewer newUserViewer = new userViewer(name,login,password);
        userRepository.save(newUserViewer);
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(1,false);
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
    }

    @PostMapping("/changepassword") // get 1:login, 2:password and 3:new password -> return 1:success  || 1:fail
    public String changePassword(@RequestBody Map<Integer, Object> newMapData){
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
            if(0 == viewer.changePassowrd(login,password,newpassword)){
                userRepository.save(viewer);
                resultMap.put(1,true);
                String jsonResponse = convertMapToJson(resultMap);
                return jsonResponse;
            }
        }
        user = artistRepository.findIdByLogin(login);
        if(user instanceof userArtist){
            userArtist viewer = (userArtist) user;
            if(0 == viewer.changePassowrd(login,password,newpassword)){
                artistRepository.save(viewer);
                resultMap.put(1,true);
                String jsonResponse = convertMapToJson(resultMap);
                return jsonResponse;
            }
        }
        } catch (Exception e) {
           String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }

    @PostMapping("/changdetail") // get 1:login, 2:password, 3:new name 4:new logo -> return 1:success  || 1:fail
    public String changeName(@RequestBody Map<Integer, Object> newMapData){
        Integer loginKey = 1;
        Integer passwordKey = 2;
        Integer newnameKey = 3;
        Integer newlogoKey = 4;
        String login = (String) newMapData.get(loginKey);
        String password = (String) newMapData.get(passwordKey);
        String newname = (String) newMapData.get(newnameKey);
        String newlogo = (String) newMapData.get(newlogoKey);
        userAccount user = artistRepository.findIdByLogin(login);
        imageBasic image = imageRepository.findIdByImageData(newlogo);
        Map<Integer, Object> resultMap = new HashMap<>();
        resultMap.put(1,false);
        try{
            if(user instanceof userArtist){
                userArtist viewer = (userArtist) user;
                if(0 == viewer.changeDetail(newname, login,password)){
                    if(!(image instanceof imageBasic)){
                        imageBasic newImage = new imageBasic(newlogo, true, viewer);
                        Set<String> mySet = new HashSet<>();
                        mySet.add("logo");
                        newImage.setTags(mySet);
                        image = newImage;
                        viewer.changeLogo(newImage);
                        artistRepository.save(viewer);
                        imageRepository.save(newImage);
                        resultMap.put(1,true);
                        String jsonResponse = convertMapToJson(resultMap);
                        return jsonResponse;
                }
            }
        }
        } catch (Exception e) {
           String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        user = userRepository.findIdByLogin(login);
        try{
        if(user instanceof userViewer){
            userViewer viewer = (userViewer) user;
            if(0 == viewer.changeDetail(newname, login,password)){
                userRepository.save(viewer);
                resultMap.put(1,true);
                String jsonResponse = convertMapToJson(resultMap);
                return jsonResponse;
            }
        }
        } catch (Exception e) {
           String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }

    @PostMapping("/convert") // get 1:login, 2:password, 3: logo -> return 1:success  || 1:fail
    public String changeViewerToArtist(@RequestBody Map<Integer, Object> newMapData){
        Integer loginKey = 1;
        Integer passwordKey = 2;
        Integer newlogoKey = 3;
        String login = (String) newMapData.get(loginKey);
        String password = (String) newMapData.get(passwordKey);
        String newlogo = (String) newMapData.get(newlogoKey);
        userAccount user = userRepository.findIdByLogin(login);
        Map<Integer, Object> resultMap = new HashMap<>();
        resultMap.put(1,false);
        try{
        if(user instanceof userViewer){
            userViewer viewer = (userViewer) user;
            if(viewer.checkUserAccount(login, password)){
                userArtist artist =  new userArtist(viewer.getName(), login,password, newlogo);
                imageBasic newImage = new imageBasic(newlogo, true, artist);
                Set<String> mySet = new HashSet<>();
                mySet.add("logo");
                newImage.setTags(mySet);
                artist.getListOfArt().add(newImage);
                artistRepository.save(artist);
                userRepository.delete(viewer);
                resultMap.put(1,true);
                String jsonResponse = convertMapToJson(resultMap);
                return jsonResponse;
            }
        }
        } catch (Exception e) {
           String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }


        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }

    //images handling
    
    @PostMapping("/getrandomimages") //get 1:int number of images -> return 1: number you get, from 2 to - number+1 : object Images (imageBasic)
    public String getRandomImages(@RequestBody Map<Integer, Object> newMapData){
        Map<Integer, Object> resultMap = new HashMap<>();
        List<Long> userArtists = artistRepository.findAllIds();
        if(userArtists.isEmpty()){
            resultMap.put(0, 0);
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
        }
        Integer numberKey = 1;
        Integer number = (Integer) newMapData.get(numberKey);
        Integer counter = 0;
        for(int i = 0; i < number; i++){
            Integer randomValue = (int) (Math.random() * userArtists.size());
            Long userId = userArtists.get(randomValue);
            userArtist user = artistRepository.findUserById(userId);
            if(user instanceof userArtist){
                List<imageBasic> publicImages= user.getListOfArt();
                if(!publicImages.isEmpty()){
                    if(publicImages.size() > 0){
                        Integer randomValue2 = (int) (Math.random() * publicImages.size());
                        imageBasic temPorary = publicImages.get(randomValue2);
                        if(temPorary.getIsPublic()){
                            resultMap.put(counter+2, temPorary);
                            counter++;
                        }
                    }
                }
            }
        }
        resultMap.put(0, counter);
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }

    @PostMapping("/gettagimages") //get 1: int number of and 2: tag -> return 1: number of returns 2 to n: images basic
    public String getTaggedImages(@RequestBody Map<Integer, Object> newMapData){
        Integer numberKey = 1;
        Integer tagKey = 2;
        Integer counter = 0;
        Integer number = (Integer) newMapData.get(numberKey);
        String tag = (String) newMapData.get(tagKey);
        Map<Integer, Object> resultMap = new HashMap<>();
        List<imageBasic> art = imageRepository.findByTagsContaining(tag);
        if(art.isEmpty()){
            resultMap.put(0, 0);
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        for(int i = 0; i < number; i++){
            Integer randomValue = (int) (Math.random() * art.size());
            imageBasic temp = art.get(randomValue);
            if(temp.getIsPublic()){
                resultMap.put(counter+2, temp);
                counter++;
            }
        }
        resultMap.put(0, counter);
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }

    @PostMapping("/getnewimages") //get 1: int number of -> return 1: number of returns 2 to n: images basic
    public String getNewImages(@RequestBody Map<Integer, Object> newMapData){
        Integer numberKey = 1;
        Integer counter = 0;
        Integer number = (Integer) newMapData.get(numberKey);
        Map<Integer, Object> resultMap = new HashMap<>();
        List<imageBasic> art = imageRepository.findAllByOrderByDateCreatedDesc();
        if(art.isEmpty()){
            resultMap.put(0, 0);
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        for(int i = 0; i < number; i++){
            imageBasic temp = art.get(i);
            if(temp.getIsPublic()){
                resultMap.put(counter+2, temp);
                counter++;
            }
        }
        resultMap.put(0, counter);
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }
    

     @PostMapping("/getuserimages") //get 1: user id -> return 1: number of returns 2 to n: images basic
    public String getUserImages(@RequestBody Map<Integer, Object> newMapData){
        Integer idKey = 1;
        String id = (String) newMapData.get(idKey);
        Map<Integer, Object> resultMap = new HashMap<>();
        userArtist user = artistRepository.findUserByName(id);
        Integer counter = 0;
        resultMap.put(0, 0);
        if(!(user instanceof userArtist)){
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        List<imageBasic> temp = user.getListOfArt();
        for(Integer i = 0; i < temp.size(); i++){
            if(temp.get(i).getIsPublic()){
                resultMap.put(counter+2, temp.get(i));
                counter++;
            }
        }
        resultMap.put(0, counter);
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }

    @PostMapping("/createimage") //get 1:login 2:password 3:imagedata 4:imagename 5: number of tags 6:ispublic 7: - x tags -> return 1:success || 1:fail
    public String createImage(@RequestBody Map<Integer, Object> newMapData){
        Map<Integer, Object> resultMap = new HashMap<>();
        Integer loginKey = 1;
        Integer passwordKey = 2;
        Integer imageKey = 3;
        Integer nameKey = 4;
        Integer tagsKey = 5;
        Integer publicKey = 6;
        Integer currentTag = 7;
        String login = (String) newMapData.get(loginKey);
        String password = (String) newMapData.get(passwordKey);
        String image = (String) newMapData.get(imageKey);
        String name = (String) newMapData.get(nameKey);
        Integer tags = (Integer) newMapData.get(tagsKey);
        Boolean isPublic = (Boolean) newMapData.get(publicKey);
        String newTag;
        userArtist user = artistRepository.findIdByLogin(login);
        resultMap.put(0, "fail");
        if(!(user instanceof userArtist)){
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        if(user.checkUserAccount(login, password)){
            imageBasic newImage = new imageBasic(image, isPublic, user);
            user.addImage(newImage);
            Set<String> toAdd = new HashSet<>() ;
            for(int i = 0; i < tags; i++){
                currentTag = currentTag + i;
                newTag = (String) newMapData.get(currentTag);
                toAdd.add(newTag);
            }
            newImage.addTags(toAdd);
            user.addImage(newImage);
            artistRepository.save(user);
            imageRepository.save(newImage);
            resultMap.put(0, "success");
        }
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }

    @PostMapping("/deleteimage") ///get 1:login 2:password 3:imagedata 4:imagename 5:ispublic -> return 1:success || 1:fail
    public String deleteImage(){
        return "";
    }

    @PostMapping("/getdatafromid") ///get 1:user id -> return 1: name 2: logo
    public String getUserInfo(@RequestBody Map<Integer, Object> newMapData){
        Integer idKey = 1;
        Long id = (Long) newMapData.get(idKey);
        Map<Integer, Object> resultMap = new HashMap<>();
        userArtist user = artistRepository.findUserById(id);
        Integer counter = 0;
        resultMap.put(0, "fail");
        if(!(user instanceof userArtist)){
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        String name = user.getName();
        imageBasic logo = user.getLogo();
        resultMap.put(0, name);
        resultMap.put(1, logo);
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }

    @PostMapping("/changeispublic") //get 1: login 2: password 3: image id -> return 1:success
    public String postMethodName(@RequestBody Map<Integer, Object> newMapData) {
        Integer loginKey = 1;
        Integer passwordKey = 2;
        Integer imageKey = 3;
        String login = (String) newMapData.get(loginKey);
        String password = (String) newMapData.get(passwordKey);
        Integer id1 = (Integer) newMapData.get(imageKey);
        Long id = id1.longValue();
        userArtist user = artistRepository.findIdByLogin(login);
        Map<Integer, Object> resultMap = new HashMap<>();
        imageBasic image = imageRepository.findImageById(id);
        resultMap.put(0, "fail");
        if(!(user instanceof userArtist)){
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        if(!(image instanceof imageBasic)){
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        if(user.checkUserAccount(login, password)){
            if(image.getArtist() == user){
                if(image.getIsPublic()){
                    image.setIsPublic(false);
                }
                else{
                    image.setIsPublic(true);
                }
                resultMap.put(0, "success");
                imageRepository.save(image);
                String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
            }
        }
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }
    @PostMapping("/getmyimages") //get 1: login 2: password -> return 1: number of returns 2 to n: images basic
    public String getMyImages(@RequestBody Map<Integer, Object> newMapData){
        Integer loginKey = 1;
        Integer passwordKey = 2;
        String login = (String) newMapData.get(loginKey);
        String password = (String) newMapData.get(passwordKey);
        Map<Integer, Object> resultMap = new HashMap<>();
        userArtist user = artistRepository.findIdByLogin(login);
        Integer counter = 0;
        resultMap.put(0, 0);
        if(!(user instanceof userArtist)){
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        if(user.checkUserAccount(login, password)){
            List<imageBasic> temp = user.getListOfArt();
            for(Integer i = 0; i < temp.size(); i++){
                resultMap.put(counter+2, temp.get(i));
                counter++;
            }
        }
        resultMap.put(0, counter);
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }
    @PostMapping("/getuserfromimage") //get 1: id image -> return 1: false/true 2: name (if true)
    public String getUserFromImage(@RequestBody Map<Integer, Object> newMapData){
        Integer idKey = 1;
        Integer id = (Integer) newMapData.get(idKey);
        Long trueId = id.longValue();
        Map<Integer, Object> resultMap = new HashMap<>();
        resultMap.put(0, false);
        imageBasic image = imageRepository.findImageById(trueId);
        if(!(image instanceof imageBasic)){
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        userArtist user = image.getArtist();
        if(!(user instanceof userArtist)){
            String jsonResponse = convertMapToJson(resultMap);
            return jsonResponse;
        }
        String name = user.getName();
        resultMap.put(0, true);
        resultMap.put(1, name);
        String jsonResponse = convertMapToJson(resultMap);
        return jsonResponse;
    }
}