package com.example.hippogram.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User author;

    @ManyToOne
    private Post post;

    private String text;
    private Date createdAt;


}

