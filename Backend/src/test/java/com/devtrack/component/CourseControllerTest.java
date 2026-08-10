package com.devtrack.component;

import com.devtrack.DbInitializer;
import com.devtrack.repository.CourseRepository;
import com.devtrack.repository.StudyEntryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class CourseControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudyEntryRepository studyEntryRepository;

    @Autowired
    private DbInitializer dbInitializer;

    @Test
    void givenGetAllCourses_whenCoursesExist_thenReturnListOfCourses() {
        webTestClient.get()
                .uri("/courses")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(3);
    }

    @Test
    void givenAddCourse_whenValidInput_thenReturnSavedCourse() {
        webTestClient.post()
                .uri("/courses/addCourse")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                        "name": "Testing 1",
                        "study_points": 3
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Testing 1")
                .jsonPath("$.study_points").isEqualTo(3);
    }

    @Test
    void givenAddCourse_whenCourseAlreadyExists_thenThrowException() {
        webTestClient.post()
                .uri("/courses/addCourse")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                        "name": "Programming 1",
                        "study_points": 3
                        }
                        """)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void givenAddCourse_whenNameIsEmpty_thenBadRequestIsReturned() {
        webTestClient.post()
                .uri("/courses/addCourse")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                        "name": "",
                        "study_points": 3
                        }
                        """)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void givenAddCourse_whenStudyPointsAreNegative_thenBadRequestIsReturned() {
        webTestClient.post()
                .uri("/courses/addCourse")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                        "name": "Testing 1",
                        "study_points": -3
                        }
                        """)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @AfterEach
    void cleanUp() {
        studyEntryRepository.deleteAll();
        courseRepository.deleteAll();
        dbInitializer.initialize();
    }
}
