package com.example.hippogram.service;

import com.example.hippogram.entity.Following;
import com.example.hippogram.entity.User;
import com.example.hippogram.exception.*;
import com.example.hippogram.repository.FollowingRepository;
import com.example.hippogram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowingRepository followingRepository;

    public void followUser(Long userIdToFollow) {
        User currentUser = getCurrentUser();

        User userToFollow = userRepository.findById(userIdToFollow)
                .orElseThrow(() -> new UserNotFoundException("User to follow not found"));

        if (isFollowing(currentUser, userToFollow)) {
            throw new AlreadyFollowingException("You are already following this user");
        }

        Following following = new Following();
        following.setFollower(currentUser);
        following.setFollowing(userToFollow);

        followingRepository.save(following);
    }

    public void unfollowUser(Long userIdToUnfollow) {
        User currentUser = getCurrentUser();

        User userToUnfollow = userRepository.findById(userIdToUnfollow)
                .orElseThrow(() -> new UserNotFoundException("User to unfollow not found"));

        Following existingFollowing = followingRepository.findByFollowerAndFollowing(currentUser, userToUnfollow);
        if (existingFollowing != null) {
            followingRepository.delete(existingFollowing);
        } else {
            throw new NotFollowingException("You are not following this user");
        }
    }


    private boolean isFollowing(User follower, User following) {
        return followingRepository.findByFollowerAndFollowing(follower, following) != null;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Optional<User> optUser = userRepository.findByUsername(username);

            if (optUser.isPresent()) {
                User currentUser = optUser.get();

                return currentUser;
            } else {
                throw new UnauthorizedException("User not found");
            }
        } else {
            throw new UnauthorizedException("User is not authenticated");
        }
    }
}