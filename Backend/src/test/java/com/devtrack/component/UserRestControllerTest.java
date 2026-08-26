package com.devtrack.component;

import com.devtrack.DbCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class UserRestControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DbCleaner dbCleaner;

    @BeforeEach
    void cleanDatabase() {
        dbCleaner.cleanDatabase();
    }

    @Test
    void givenValidInput_whenRegisterUser_thenReturnSavedUser() {
        webTestClient.post()
                .uri("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "username": "testuser",
                            "email": "test.user@mail.com",
                            "password": "testpassword"
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isEqualTo("testuser")
                .jsonPath("$.email").isEqualTo("test.user@mail.com");
    }

    @Test
    void givenExistingUsername_whenRegisterUser_thenReturnBadRequest() {
        webTestClient.post()
                .uri("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "username": "John Doe",
                            "email": "test.user@mail.com",
                            "password": "testpassword"
                        }
                        """)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void givenExistingEmail_whenRegisterUser_thenReturnBadRequest() {
        webTestClient.post()
                .uri("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "username": "testuser",
                            "email": "johndoe@mail.com",
                            "password": "testpassword"
                        }
                        """)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void givenUsernameIsEmpty_whenRegisterUser_thenReturnBadRequest() {
        webTestClient.post()
                .uri("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "username": "",
                            "email": "test.user@mail.com",
                            "password": "testpassword"
                        }
                        """)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void givenEmailIsEmpty_whenRegisterUser_thenReturnBadRequest() {
        webTestClient.post()
                .uri("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "username": "testuser",
                            "email": "",
                            "password": "testpassword"
                        }
                        """)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void givenPasswordIsEmpty_whenRegisterUser_thenReturnBadRequest() {
        webTestClient.post()
                .uri("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "username": "testuser",
                            "email": "test.user@mail.com",
                            "password": ""
                        }
                        """)
                .exchange()
                .expectStatus().isBadRequest();
    }
}