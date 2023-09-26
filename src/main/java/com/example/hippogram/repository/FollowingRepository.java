package com.example.hippogram.repository;

import com.example.hippogram.entity.Following;
import com.example.hippogram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {

    Following findByFollowerAndFollowing(User currentUser, User userToUnfollow);
}