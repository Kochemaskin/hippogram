package com.example.hippogram.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name="followers")
public class Followers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private User follower;


}
