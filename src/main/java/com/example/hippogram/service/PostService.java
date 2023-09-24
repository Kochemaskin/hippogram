package com.example.hippogram.service;

import com.example.hippogram.dto.PostDTO;
import com.example.hippogram.entity.Post;
import com.example.hippogram.entity.User;
import com.example.hippogram.exception.PostNotFoundException;
import com.example.hippogram.exception.UnauthorizedException;
import com.example.hippogram.repository.PostRepository;
import com.example.hippogram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    public Post createPost(PostDTO postDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Optional<User> optUser = userRepository.findByUsername(username);

            if (optUser.isPresent()) {
                User currentUser = optUser.get();

                Post post = new Post();
                post.setAuthor(currentUser);
                post.setDescription(postDTO.getDescription());
                post.setImageUrl(postDTO.getImageUrl());
                post.setCreatedAt(new Date());

                return postRepository.save(post);
            } else {
                throw new UnauthorizedException("User not found");
            }
        } else {
            throw new UnauthorizedException("User is not authenticated");
        }
    }

    public void deletePost(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Optional<User> optUser = userRepository.findByUsername(username);
            if (optUser.isPresent()) {
                User currentUser = optUser.get();

                Optional<Post> optPost = postRepository.findById(postId);
                if (optPost.isPresent()) {
                    Post post = optPost.get();

                    if (currentUser.getId().equals(post.getAuthor().getId())) {
                        postRepository.delete(post);
                    } else {
                        throw new UnauthorizedException("Unauthorized to delete this post");
                    }
                } else {
                    throw new PostNotFoundException("Post not found");
                }
            } else {
                throw new UnauthorizedException("User not found");
            }
        } else {
            throw new UnauthorizedException("User is not authenticated");
        }
    }


}
