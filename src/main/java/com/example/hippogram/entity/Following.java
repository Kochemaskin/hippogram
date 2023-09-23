package com.example.hippogram.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="followings")
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User follower;

    @ManyToOne
    private User following;


}
