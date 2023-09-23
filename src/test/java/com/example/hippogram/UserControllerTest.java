package com.example.hippogram;

import com.example.hippogram.entity.User;
import com.example.hippogram.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testRegisterUser() throws Exception {
        String requestBody = "{ \"firstName\": \"Alice\", \"lastName\": \"Johnson\", \"email\": \"alice@example.com\", \"password\": \"password123\" }";

//todo fix this
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
