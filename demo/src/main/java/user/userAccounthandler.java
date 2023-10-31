package user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import repository.UserRepository;

@RestController
public class userAccounthandler {

    @Autowired
    private UserRepository userRepository;
    
    //profile handling
    @GetMapping("/login") //get 1:login and 2:password -> return 1:true, 2:name and 3:logo || 1:false, 2:"", 3:""
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

    @GetMapping("/createaccount") //get 1:login, 2:password and 3:name -> return 1:success || 1:error
    public Map<Integer, Object> createAccount(@RequestBody Map<Integer, Object> newMapData){
        Integer loginKey = 1;
        Integer passwordKey = 2;
        Integer nameKey = 3;
        String login = (String) newMapData.get(loginKey);
        String password = (String) newMapData.get(passwordKey);
        String name = (String) newMapData.get(nameKey);
        userAccount user = userRepository.findIdByLogin(login);
        Map<Integer, Object> resultMap = new HashMap<>();
        if(user != null){
            resultMap.put(1,false);
            return resultMap;
        }
        resultMap.put(1,true);
        userViewer newUserViewer = new userViewer(name,login,password);
        userRepository.save(newUserViewer);
        return newMapData;
    }

    @GetMapping("/changepassword") // get login, password and new password -> return success  && fail
    public String changePassword(){
        return "";
    }



    //images handling
    @GetMapping("/getrandomimages") //get int number of -> return images, ids
    public String getRandomImages(){
        return "";
    }

    @GetMapping("/getimageinfo") //get image id -> return all image info + creator info
    public String getImageInfo(){
        return "";
    }

    @GetMapping("/gettagimages") //get int number of and list of tags -> return images, ids
    public String getTaggedImages(){
        return "";
    }

    @GetMapping("/getnewimages") //get int number -> return images and ids
    public String getNewImages(){
        return "";
    } 

    @GetMapping("/createimage") //get user and image id, login, password, image, imagename, private -> return success or fail
    public String createImage(){
        return "";
    }
}
