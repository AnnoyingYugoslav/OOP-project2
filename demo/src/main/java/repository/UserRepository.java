package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import user.userAccount;

public interface UserRepository extends JpaRepository<userAccount, Long> {
    Long findIdByLogin(String login);
    Long findIdByName(String name);
}
