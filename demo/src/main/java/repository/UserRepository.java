package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import image.imageBasic;
import user.userAccount;

public interface UserRepository extends JpaRepository<userAccount, Long> {
    userAccount findIdByLogin(String login);
    userAccount findIdByName(String name);
}
