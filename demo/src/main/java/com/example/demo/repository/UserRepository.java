package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.image.imageBasic;
import com.example.demo.user.userAccount;
import com.example.demo.user.userArtist;

public interface UserRepository extends JpaRepository<userAccount, Long> {
    userAccount findIdByLogin(String login);
    userAccount findIdByName(String name);
    userAccount findUserById(Long id);
    userArtist findUserArtistById(Long id);
    @Query("SELECT ua.id FROM userArtist ua")
    List<Long> findUserArtistIds();
}
