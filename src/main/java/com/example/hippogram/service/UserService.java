package com.example.hippogram.service;

import com.example.hippogram.dto.UserLoginDTO;
import com.example.hippogram.dto.UserProfileDTO;
import com.example.hippogram.dto.UserRegistrationDTO;
import com.example.hippogram.entity.User;
import com.example.hippogram.exception.InvalidPasswordException;
import com.example.hippogram.exception.UserAlreadyExistsException;
import com.example.hippogram.exception.UserNotFoundException;
import com.example.hippogram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;




    public User registerUser(UserRegistrationDTO registrationDTO) {

        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            try {
                throw new UserAlreadyExistsException("The user with this email is already registered.");
            } catch (UserAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }


        User user = new User();
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());

        return userRepository.save(user);
    }

    public User loginUser(UserLoginDTO loginDTO) {

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (loginDTO.getPassword().equals(user.getPassword())) {
            throw new InvalidPasswordException("Wrong password");
        }

        return user;
    }

    public User updateUserProfile(Long userId, UserProfileDTO profileDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        user.setFirstName(profileDTO.getFirstName());
        user.setLastName(profileDTO.getLastName());
        user.setAvatarUrl(profileDTO.getAvatarUrl());


        return userRepository.save(user);
    }

}
