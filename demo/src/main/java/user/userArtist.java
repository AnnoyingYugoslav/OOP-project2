package user;


import java.util.List;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import image.imageBasic;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("entityArtist")
public class userArtist extends userViewer{

    private List<imageBasic> listOfArt; //tymczasowe

   /* public userArtist(){}

    public userArtist(String name, String login, String rawPassword, String Logo){
         this.name = name;
         this.login = login;
         this.password = hashPassword(rawPassword);
         this.logo = logo;
    }

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
    
    public void changeDetail(String name, String logo, String login, String password){
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
    public  Boolean loginUser(String login, String password){
        if(checkUserAccount(login, password)){
            return true;
        }
        return false;
    }
    */
    public void newImage(imageBasic newImageData){
        this.listOfArt.add(newImageData);
    }
    public void removeImage(imageBasic imageData){
        this.listOfArt.remove(imageData);
    }
}
