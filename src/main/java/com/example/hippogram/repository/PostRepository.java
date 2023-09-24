package com.example.hippogram.repository;

import com.example.hippogram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long userId);

    Optional<Post> findById(Long postId);

//    List<Post> getPostsByUserId(Long userId);


    Post getPostById(Long postId);
}
