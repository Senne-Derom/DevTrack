package com.devtrack.integration;

import com.devtrack.DbCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DbCleaner dbCleaner;

    @Test
    void givenGetAllCourses_whenCoursesExist_thenReturnListOfCourses() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void givenGetAllCourses_whenNoCoursesExist_thenReturnEmptyList() throws Exception {
        dbCleaner.emptyDatabase();
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void givenAddCourse_whenValidCourseInput_thenReturnAddedCourse() throws Exception {
        String courseInputJson = """
                {
                    "name": "New Course",
                    "study_points": 3
                }
                """;

        mockMvc.perform(post("/courses/addCourse")
                        .contentType("application/json")
                        .content(courseInputJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Course"))
                .andExpect(jsonPath("$.study_points").value(3));

        assertThat(mockMvc.perform(get("/courses")).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$.length()").value(4)));
    }

    @Test
    void givenAddCourse_whenNameIsEmpty_thenReturnBadRequest() throws Exception {
        String courseInputJson = """
                {
                    "name": "",
                    "study_points": 3
                }
                """;

        mockMvc.perform(post("/courses/addCourse")
                        .contentType("application/json")
                        .content(courseInputJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAddCourse_whenstudy_pointsIsNegative_thenReturnBadRequest() throws Exception {
        String courseInputJson = """
                {
                    "name": "New Course",
                    "study_points": -1
                }
                """;

        mockMvc.perform(post("/courses/addCourse")
                        .contentType("application/json")
                        .content(courseInputJson))
                .andExpect(status().isBadRequest());
    }

    @BeforeEach
    void cleanUp() {
        dbCleaner.cleanDatabase();
    }
}
