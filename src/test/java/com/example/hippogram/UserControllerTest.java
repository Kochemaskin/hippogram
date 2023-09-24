package com.example.hippogram;


import com.example.hippogram.dto.UserRegistrationDTO;
import com.example.hippogram.entity.User;
import com.example.hippogram.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterUser() throws Exception {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setFirstName("Alice");
        registrationDTO.setLastName("Johnson");
        registrationDTO.setEmail("alice@example.com");
        registrationDTO.setPassword("password123");
        String requestBody = objectMapper.writeValueAsString(registrationDTO);
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
        User registeredUser = userRepository.findByEmail("alice@example.com").orElse(null);
        assertNotNull(registeredUser);
        assertEquals("Alice", registeredUser.getFirstName());
        assertEquals("Johnson", registeredUser.getLastName());
        assertEquals("password123", registeredUser.getPassword());
    }
}
