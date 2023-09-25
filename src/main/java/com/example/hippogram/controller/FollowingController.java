package com.example.hippogram.controller;

import com.example.hippogram.dto.FollowingDTO;
import com.example.hippogram.exception.AlreadyFollowingException;
import com.example.hippogram.exception.NotFollowingException;
import com.example.hippogram.exception.UserNotFoundException;
import com.example.hippogram.service.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/following")
public class FollowingController {

    @Autowired
    private FollowingService followingService;

    @PostMapping("/follow")
    public ResponseEntity<?> followUser(@RequestBody FollowingDTO followingDTO) {
        try {
            followingService.followUser(followingDTO.getUserIdToFollow());
            return ResponseEntity.ok("You are now following the user");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AlreadyFollowingException e) {
            return ResponseEntity.badRequest().body("You are already following this user");
        }
    }

    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollowUser(@RequestBody FollowingDTO followingDTO) {
        try {
            followingService.unfollowUser(followingDTO.getUserIdToFollow());
            return ResponseEntity.ok("You have unfollowed the user");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotFollowingException e) {
            return ResponseEntity.badRequest().body("You are not following this user");
        }
    }
}