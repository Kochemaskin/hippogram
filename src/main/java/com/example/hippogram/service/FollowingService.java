package com.example.hippogram.service;

import com.example.hippogram.repository.FollowingRepository;
import com.example.hippogram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowingRepository followingRepository;

    public void followUser(Long userIdToFollow) {
        // todo
    }

    public void unfollowUser(Long userIdToUnfollow) {
        // todo
    }


}