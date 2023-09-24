package com.example.hippogram.service;

import com.example.hippogram.dto.CommentDTO;
import com.example.hippogram.entity.Comment;
import com.example.hippogram.entity.User;
import com.example.hippogram.exception.CommentNotFoundException;
import com.example.hippogram.exception.PostNotFoundException;
import com.example.hippogram.exception.UnauthorizedException;
import com.example.hippogram.repository.CommentRepository;
import com.example.hippogram.repository.PostRepository;
import com.example.hippogram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment createComment(CommentDTO commentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Optional<User> optUser = userRepository.findByUsername(username);

            if (optUser.isPresent()) {
                User currentUser = optUser.get();

                Comment comment = new Comment();
                comment.setAuthor(currentUser);


                comment.setPost(postRepository.findById(commentDTO.getPostId())
                        .orElseThrow(() -> new PostNotFoundException("Post not found")));
                comment.setText(commentDTO.getContent());
                comment.setCreatedAt(new Date());

                return commentRepository.save(comment);
            } else {
                throw new UnauthorizedException("User not found");
            }
        } else {
            throw new UnauthorizedException("User is not authenticated");
        }
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    public void deleteComment(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Optional<User> optUser = userRepository.findByUsername(username);
            if (optUser.isPresent()) {
                User currentUser = optUser.get();

                Optional<Comment> optComment = commentRepository.findById(commentId);
                if (optComment.isPresent()) {
                    Comment comment = optComment.get();

                    if (currentUser.getId().equals(comment.getAuthor().getId())) {
                        commentRepository.delete(comment);
                    } else {
                        throw new UnauthorizedException("Unauthorized to delete this comment");
                    }
                } else {
                    throw new CommentNotFoundException("Comment not found");
                }
            } else {
                throw new UnauthorizedException("User not found");
            }
        } else {
            throw new UnauthorizedException("User is not authenticated");
        }
    }

}
