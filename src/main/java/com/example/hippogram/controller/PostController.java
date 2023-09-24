package com.example.hippogram.controller;

import com.example.hippogram.dto.PostDTO;
import com.example.hippogram.entity.Post;
import com.example.hippogram.exception.PostNotFoundException;
import com.example.hippogram.exception.UnauthorizedException;
import com.example.hippogram.repository.PostRepository;
import com.example.hippogram.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        Post createdPost = postService.createPost(postDTO);

        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        Post post = postRepository.getPostById(postId);

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPostsByUserId(@PathVariable Long userId) {
        List<Post> userPosts = postRepository.findByAuthorId(userId);

        return ResponseEntity.ok(userPosts);
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (PostNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to delete this post");
        }
    }
}