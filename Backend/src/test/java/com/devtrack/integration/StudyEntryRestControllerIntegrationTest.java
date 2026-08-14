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
public class StudyEntryRestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DbCleaner dbCleaner;

    @Test
    void givenGetAllStudyEntries_whenStudyEntriesExist_thenReturnListOfStudyEntries() throws Exception {
        mockMvc.perform(get("/study_entries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void givenGetAllStudyEntries_whenNoStudyEntriesExist_thenReturnEmptyList() throws Exception {
        dbCleaner.emptyDatabase();
        mockMvc.perform(get("/study_entries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void givenAddStudyEntry_whenValidStudyEntryInput_thenReturnAddedStudyEntry() throws Exception {
        String studyEntryInputJson = """
                {
                    "course": {
                        "name": "Programming 1",
                        "study_points": 6
                    },
                    "description": "Studied for the exam",
                    "timeSpent": 120,
                    "date": "2023-01-01"
                }
                """;

        mockMvc.perform(post("/study_entries/addStudyEntry")
                        .contentType("application/json")
                        .content(studyEntryInputJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Studied for the exam"))
                .andExpect(jsonPath("$.timeSpent").value(120))
                .andExpect(jsonPath("$.date").value("2023-01-01"))
                .andExpect(jsonPath("$.courseName").value("Programming 1"));

        assertThat(mockMvc.perform(get("/study_entries"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4)));
    }

    @Test
    void givenAddStudyEntry_whenDescriptionIsEmpty_thenReturnBadRequest() throws Exception {
        String studyEntryInputJson = """
                {
                    "course": {
                        "name": "Programming 1",
                        "study_points": 6
                    },
                    "description": "",
                    "timeSpent": 120,
                    "date": "2023-01-01"
                }
                """;

        mockMvc.perform(post("/study_entries/addStudyEntry")
                        .contentType("application/json")
                        .content(studyEntryInputJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAddStudyEntry_whenTimeSpentIsNegative_thenReturnBadRequest() throws Exception {
        String studyEntryInputJson = """
                {
                    "course": {
                        "name": "Programming 1",
                        "study_points": 6
                    },
                    "description": "Studied for the exam",
                    "timeSpent": -120,
                    "date": "2023-01-01"
                }
                """;

        mockMvc.perform(post("/study_entries/addStudyEntry")
                        .contentType("application/json")
                        .content(studyEntryInputJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAddStudyEntry_whenDateIsInFuture_thenReturnBadRequest() throws Exception {
        String studyEntryInputJson = """
                {
                    "course": {
                        "name": "Programming 1",
                        "study_points": 6
                    },
                    "description": "Studied for the exam",
                    "timeSpent": 120,
                    "date": "3023-01-01"
                }
                """;

        mockMvc.perform(post("/study_entries/addStudyEntry")
                        .contentType("application/json")
                        .content(studyEntryInputJson))
                .andExpect(status().isBadRequest());
    }

    @BeforeEach
    void cleanUp() {
        dbCleaner.cleanDatabase();
    }
}
