package user;

import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import image.imageBasic;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("entityViewer")
public class userViewer extends userAccount {
    //constructor
    public userViewer(){}

    public userViewer(String name, String login, String rawPassword, imageBasic Logo){
         this.name = name;
         this.login = login;
         this.password = hashPassword(rawPassword);
         this.logo = logo;
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
    public String loginUser(String login, String password){
        if(checkUserAccount(login, password)){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> imageData = new HashMap<>();
                imageData.put("imageBase64", logo.getImage());
                imageData.put("name", name);
                return objectMapper.writeValueAsString(imageData);
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        else{
            return null;
        }
    }
}
