package com.example.hippogram.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String email;
    private String lastName;
    private String username;
    private String password;
    private String avatarUrl;


}