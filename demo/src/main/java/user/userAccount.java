package user;

import image.imageBasic;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.DiscriminatorType;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "entity_type", discriminatorType = DiscriminatorType.STRING)
public abstract class userAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected String login;
    protected String password; //needs to be hashed
    protected imageBasic logo;

    //private String hashPassword(String rawPassword);
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //return passwordEncoder.encode(rawPassword);
    


   // public userAccount(){}

    //public userAccount(String name, String login, String rawPassword, String Logo){
       // this.name = name;
       // this.login = login;
        //this.password = hashPassword(rawPassword);
        //this.logo = logo;
    //}
    public abstract Boolean checkUserAccount(String login, String password);
       // if(this.login == login){
        //    if(this.password == password){
         //       return true;
        //    }
       // }
      //  return false;
    
    public abstract void changeDetail(String name, imageBasic logo, String login, String password);
       // if(correctUserAccount(id, login, password)){
        //    this.name = name;
    public abstract void changePassowrd(String login, String password, String newPassword);

    public abstract String loginUser(String login, String password); //change from void
}
