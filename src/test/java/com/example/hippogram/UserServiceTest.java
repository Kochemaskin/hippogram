package com.example.hippogram;

import com.example.hippogram.dto.UserRegistrationDTO;
import com.example.hippogram.entity.User;
import com.example.hippogram.repository.UserRepository;
import com.example.hippogram.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testRegisterUser() {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setEmail("john.doe@example.com");
        registrationDTO.setPassword("password123");


        User registeredUser = userService.registerUser(registrationDTO);

        assertNotNull(registeredUser.getId());
        assertEquals("John", registeredUser.getFirstName());
        assertEquals("Doe", registeredUser.getLastName());
        assertEquals("john.doe@example.com", registeredUser.getEmail());
        assertEquals("password123", registeredUser.getPassword());
    }

}
