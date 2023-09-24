package com.example.hippogram.repository;

import com.example.hippogram.entity.Like;
import com.example.hippogram.entity.Post;
import com.example.hippogram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);

    Optional<Like> findByUserAndPost(User user, Post post);
}
