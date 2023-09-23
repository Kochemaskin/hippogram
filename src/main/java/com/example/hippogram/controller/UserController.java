package com.example.hippogram.controller;

import com.example.hippogram.dto.UserLoginDTO;
import com.example.hippogram.dto.UserRegistrationDTO;
import com.example.hippogram.entity.User;
import com.example.hippogram.exception.UserNotFoundException;
import com.example.hippogram.repository.UserRepository;
import com.example.hippogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        if (userRepository.isExistByEmail(registrationDTO.getEmail())) {
            return ResponseEntity.badRequest().body("User already exist");
        }

        User user = new User();
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());

        userRepository.save(user);

        return ResponseEntity.ok("User successfully created");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO loginDTO) {

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));



        if ( loginDTO.getPassword().equals( user.getPassword())) {
            return ResponseEntity.badRequest().body("Wrong password");
        }


        return ResponseEntity.ok("Login completed successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        // todo
    }


}
