package com.example.hippogram.controller;

import com.example.hippogram.dto.LikeDTO;
import com.example.hippogram.exception.LikeAlreadyExistsException;
import com.example.hippogram.exception.LikeNotFoundException;
import com.example.hippogram.exception.PostNotFoundException;
import com.example.hippogram.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<?> likePost(@RequestBody LikeDTO likeDTO) {
        try {
            likeService.likePost(likeDTO);
            return ResponseEntity.ok("Post liked successfully");
        } catch (PostNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (LikeAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("Post already liked by the user");
        }
    }

    @PostMapping("/unlike")
    public ResponseEntity<?> unlikePost(@RequestBody LikeDTO likeDTO) {
        try {
            likeService.unlikePost(likeDTO);
            return ResponseEntity.ok("Post unliked successfully");
        } catch (PostNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (LikeNotFoundException e) {
            return ResponseEntity.badRequest().body("Like not found");
        }
    }
}
