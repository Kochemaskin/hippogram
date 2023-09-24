package com.example.hippogram.controller;

import com.example.hippogram.dto.CommentDTO;
import com.example.hippogram.entity.Comment;
import com.example.hippogram.exception.CommentNotFoundException;
import com.example.hippogram.exception.PostNotFoundException;
import com.example.hippogram.exception.UnauthorizedException;
import com.example.hippogram.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO) {
        try {
            Comment createdComment = commentService.createComment(commentDTO);
            return ResponseEntity.ok(createdComment);
        } catch (PostNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to create this comment");
        }
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable Long commentId) {
        try {
            Comment comment = commentService.getCommentById(commentId);
            return ResponseEntity.ok(comment);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Comment deleted successfully");
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to delete this comment");
        }
    }
}
