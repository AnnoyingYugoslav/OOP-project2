package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.image.imageBasic;
import com.example.demo.user.userAccount;
import com.example.demo.user.userArtist;
import com.example.demo.user.userViewer;

public interface UserRepository extends JpaRepository<userViewer, Long> {
    userViewer findIdByLogin(String login);
    userViewer findIdByName(String name);
    userViewer findUserById(Long id);
}
