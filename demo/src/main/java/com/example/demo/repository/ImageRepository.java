package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.image.imageBasic;
import com.example.demo.user.userArtist;

public interface ImageRepository extends JpaRepository<imageBasic, Long> {
    imageBasic findIdByImageData(String imageData);
    List<imageBasic> findByTagsContaining(String tag);
    List<imageBasic> findAllByOrderByDateCreatedDesc();
    imageBasic findImageById(Long id);
}