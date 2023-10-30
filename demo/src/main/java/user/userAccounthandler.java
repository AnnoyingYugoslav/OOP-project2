package user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userAccounthandler {

    @GetMapping("/login") //get login and password -> return true, name and logo && false, null, null
    public String process1() {
        return "P";
    }

    @GetMapping("/createaccount") //get login, password and name -> return success and id && error
    public String createAccount(){
        return "";
    }
}
