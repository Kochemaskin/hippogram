package com.example.hippogram.service;

import com.example.hippogram.dto.LikeDTO;
import com.example.hippogram.entity.Like;
import com.example.hippogram.entity.Post;
import com.example.hippogram.entity.User;
import com.example.hippogram.exception.LikeAlreadyExistsException;
import com.example.hippogram.exception.LikeNotFoundException;
import com.example.hippogram.exception.PostNotFoundException;
import com.example.hippogram.exception.UserNotFoundException;
import com.example.hippogram.repository.LikeRepository;
import com.example.hippogram.repository.PostRepository;
import com.example.hippogram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public void likePost(LikeDTO likeDTO) {

        Post post = postRepository.findById(likeDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found"));


        User user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new LikeAlreadyExistsException("Post already liked by the user");
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);
    }

    public void unlikePost(LikeDTO likeDTO) {

        Post post = postRepository.findById(likeDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        User user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new LikeNotFoundException("Like not found"));

        likeRepository.delete(like);
    }
}
