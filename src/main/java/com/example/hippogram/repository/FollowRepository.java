package com.example.hippogram.repository;

import com.example.hippogram.entity.user.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByFollowingId(Long followingId);

    List<Follow> findAllByFollowerId(Long followerId);

}