package com.example.hippogram.repository;

import com.example.hippogram.entity.Comment;
import com.example.hippogram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
