package com.example.hippogram.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private Long postId;
    private String username;
    private String content;

}