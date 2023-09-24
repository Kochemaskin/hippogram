package com.example.hippogram.controller;

import com.example.hippogram.exception.AlreadyFollowingException;
import com.example.hippogram.exception.NotFollowingException;
import com.example.hippogram.exception.UserNotFoundException;
import com.example.hippogram.service.FollowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/followers")
public class FollowersController {

    @Autowired
    private FollowersService followersService;

    @PostMapping("/follow/{userIdToFollow}")
    public ResponseEntity<?> followUser(@PathVariable Long userIdToFollow) {
        try {
            followersService.followUser(userIdToFollow);
            return ResponseEntity.ok("You are now following the user");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AlreadyFollowingException e) {
            return ResponseEntity.badRequest().body("You are already following this user");
        }
    }

    @PostMapping("/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long userIdToUnfollow) {
        try {
            followersService.unfollowUser(userIdToUnfollow);
            return ResponseEntity.ok("You have unfollowed the user");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotFollowingException e) {
            return ResponseEntity.badRequest().body("You are not following this user");
        }
    }
}
