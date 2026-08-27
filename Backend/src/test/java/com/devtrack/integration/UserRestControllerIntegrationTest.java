package com.devtrack.integration;

import com.devtrack.DbCleaner;
import com.devtrack.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DbCleaner dbCleaner;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        dbCleaner.cleanDatabase();
    }

    @Test
    void givenValidInput_whenAddUser_thenReturnAddedUser() throws Exception {
        String userInputJson = """
                {
                    "username": "newuser",
                    "password": "password123",
                    "email": "new.user@mail.com"
                }
                """;

        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(userInputJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.email").value("new.user@mail.com"));
    }

    @Test
    void givenEmptyUsername_whenAddUser_thenReturnBadRequest() throws Exception {
        String userInputJson = """
                {
                    "username": "",
                    "password": "password123",
                    "email": "invalid-email"
                }
                """;

        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(userInputJson))
                .andExpect(status().isBadRequest());

        assertThat(userRepository.existsByUsername("")).isFalse();
    }

    @Test
    void givenEmptyEmail_whenAddUser_thenReturnBadRequest() throws Exception {
        String userInputJson = """
                {
                    "username": "invaliduser",
                    "password": "password123",
                    "email": ""
                }
                """;

        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(userInputJson))
                .andExpect(status().isBadRequest());

        assertThat(userRepository.existsByUsername("invaliduser")).isFalse();
    }

    @Test
    void givenEmptyPassword_whenAddUser_thenReturnBadRequest() throws Exception {
        String userInputJson = """
                {
                    "username": "invaliduser",
                    "password": "",
                    "email": "invalid-email"
                }
                """;

        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(userInputJson))
                .andExpect(status().isBadRequest());

        assertThat(userRepository.existsByUsername("invaliduser")).isFalse();
    }
}
