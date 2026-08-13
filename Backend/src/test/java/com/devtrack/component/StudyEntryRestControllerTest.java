package com.devtrack.component;

import com.devtrack.DbInitializer;
import com.devtrack.repository.CourseRepository;
import com.devtrack.repository.StudyEntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class StudyEntryRestControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private StudyEntryRepository studyEntryRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DbInitializer dbInitializer;

    @Test
    void givenGetAllStudyEntries_whenStudyEntriesExist_thenReturnListOfStudyEntries() {
        webTestClient.get()
                .uri("/study_entries")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(3);
    }

    @Test
    void givenAddStudyEntry_whenValidInput_thenReturnSavedStudyEntry() {
        webTestClient.post()
                .uri("/study_entries/addStudyEntry")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "course": {
                                "name": "Programming 1",
                                "study_points": 6
                            },
                            "date": "2023-01-01",
                            "timeSpent": 120,
                            "description": "Studied for the exam"
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.date").isEqualTo("2023-01-01")
                .jsonPath("$.timeSpent").isEqualTo(120)
                .jsonPath("$.description").isEqualTo("Studied for the exam")
                .jsonPath("$.courseName").isEqualTo("Programming 1");
    }

    @Test
    void givenAddStudyEntry_whenCourseDoesNotExist_thenInternalServerErrorIsReturned() {
        webTestClient.post()
                .uri("/study_entries/addStudyEntry")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "course": {
                                "name": "Nonexistent Course",
                                "study_points": 6
                            },
                            "date": "2023-01-01",
                            "timeSpent": 120,
                            "description": "Studied for the exam"
                        }
                        """)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void givenAddStudyEntry_whenDateIsInFuture_thenBadRequestIsReturned() {
        webTestClient.post()
                .uri("/study_entries/addStudyEntry")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "course": {
                                "name": "Programming 1",
                                "study_points": 6
                            },
                            "date": "3023-01-01",
                            "timeSpent": 120,
                            "description": "Studied for the exam"
                        }
                        """)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void givenAddStudyEntry_whenTimeSpentIsNegative_thenBadRequestIsReturned() {
        webTestClient.post()
                .uri("/study_entries/addStudyEntry")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "course": {
                                "name": "Programming 1",
                                "study_points": 6
                            },
                            "date": "2023-01-01",
                            "timeSpent": -120,
                            "description": "Studied for the exam"
                        }
                        """)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void givenAddStudyEntry_whenDescriptionIsEmpty_thenBadRequestIsReturned() {
        webTestClient.post()
                .uri("/study_entries/addStudyEntry")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "course": {
                                "name": "Programming 1",
                                "study_points": 6
                            },
                            "date": "2023-01-01",
                            "timeSpent": 120,
                            "description": ""
                        }
                        """)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @BeforeEach
    void cleanUp() {
        studyEntryRepository.deleteAll();
        courseRepository.deleteAll();
        dbInitializer.initialize();
    }
}
