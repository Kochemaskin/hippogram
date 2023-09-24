package com.example.hippogram.service;

import com.example.hippogram.entity.User;
import com.example.hippogram.entity.UserSubscription;
import com.example.hippogram.exception.AlreadyFollowingException;
import com.example.hippogram.exception.NotFollowingException;
import com.example.hippogram.exception.UnauthorizedException;
import com.example.hippogram.exception.UserNotFoundException;
import com.example.hippogram.repository.FollowersRepository;
import com.example.hippogram.repository.UserRepository;
import com.example.hippogram.repository.UserSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    public void followUser(Long userIdToFollow) {
        User follower = getCurrentUser();
        User following = userRepository.findById(userIdToFollow)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (isFollowing(follower, following)) {
            throw new AlreadyFollowingException("You are already following this user");
        }
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setFollower(follower);
        userSubscription.setFollowing(following);
        userSubscriptionRepository.save(userSubscription);
    }

    public void unfollowUser(Long userIdToUnfollow) {
        User follower = getCurrentUser();

        User following = userRepository.findById(userIdToUnfollow)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!isFollowing(follower, following)) {
            throw new NotFollowingException("You are not following this user");
        }

        userSubscriptionRepository.deleteByFollowerAndFollowing(follower, following);
    }


    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());

            if (userOptional.isPresent()) {
                return userOptional.get();
            }

            throw new UserNotFoundException("User not found");
        }

        throw new UnauthorizedException("Authentication is not valid");
    }


    private boolean isFollowing(User follower, User following) {

        return userSubscriptionRepository.existsByFollowerAndFollowing(follower, following);
    }
}
