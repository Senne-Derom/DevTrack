package com.devtrack.unit;

import com.devtrack.DTO.StudyEntryInput;
import com.devtrack.model.Course;
import com.devtrack.model.StudyEntry;
import com.devtrack.repository.CourseRepository;
import com.devtrack.repository.StudyEntryRepository;
import com.devtrack.service.StudyEntryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudyEntryServiceTest {
    @Mock
    StudyEntryRepository studyEntryRepository;

    @Mock
    CourseRepository courseRepository;

    @InjectMocks
    StudyEntryService studyEntryService;

    @Test
    void givenGetStudyEntries_whenStudyEntriesExist_thenReturnListOfStudyEntries() {
        Course course = new Course("Course 1", 3);
        Course course2 = new Course("Course 2", 4);
        StudyEntry studyEntry1 = new StudyEntry(course, "Description 1", 2, LocalDate.of(2023, 1, 1));
        StudyEntry studyEntry2 = new StudyEntry(course2, "Description 2", 3, LocalDate.of(2023, 2, 1));

        when(studyEntryRepository.findAll()).thenReturn(Arrays.asList(studyEntry1, studyEntry2));
        List<StudyEntry> result = studyEntryService.getStudyEntries();
        assertEquals(2, result.size());
    }

    @Test
    void givenGetStudyEntries_whenNoStudyEntriesExist_thenReturnEmptyList() {
        when(studyEntryRepository.findAll()).thenReturn(List.of());
        List<StudyEntry> result = studyEntryService.getStudyEntries();
        assertEquals(0, result.size());
    }

    @Test
    void givenAddStudyEntry_whenValidInput_thenReturnSavedStudyEntry() {
        Course course = new Course("Course 1", 3);
        LocalDate testDate = LocalDate.of(2023, 1, 1);
        StudyEntryInput input = new StudyEntryInput(course, "Description 1", 2, testDate);
        StudyEntry savedEntry = new StudyEntry(course, "Description 1", 2, testDate);

        when(courseRepository.findByName(input.course().getName())).thenReturn(Optional.of(course));
        when(studyEntryRepository.save(any(StudyEntry.class))).thenReturn(savedEntry);
        StudyEntry result = studyEntryService.addStudyEntry(input);

        assertEquals("Description 1", result.getDescription());
        assertEquals(2, result.getTimeSpent());
        assertEquals(testDate, result.getDate());
        assertEquals(course, result.getCourse());
    }

    @Test
    void givenAddStudyEntry_whenDescriptionIsEmpty_thenThrowException() {
        Course course = new Course("Course 1", 3);
        StudyEntryInput input = new StudyEntryInput(course, "", 2, LocalDate.of(2023, 1, 1));
        when(courseRepository.findByName(input.course().getName())).thenReturn(Optional.of(course));
        try {
            studyEntryService.addStudyEntry(input);
        } catch (Exception e) {
            assertEquals("Description cannot be empty", e.getMessage());
        }
    }

    @Test
    void givenAddStudyEntry_whenTimeSpentIsNegative_thenThrowException() {
        Course course = new Course("Course 1", 3);
        StudyEntryInput input = new StudyEntryInput(course, "", 2, LocalDate.of(2023, 1, 1));
        when(courseRepository.findByName(input.course().getName())).thenReturn(Optional.of(course));
        try {
            studyEntryService.addStudyEntry(input);
        } catch (Exception e) {
            assertEquals("Time spent cannot be negative", e.getMessage());
        }
    }

    @Test
    void givenAddStudyEntry_whenDateIsInFuture_thenThrowException() {
        Course course = new Course("Course 1", 3);
        StudyEntryInput input = new StudyEntryInput(course, "", 2, LocalDate.of(2023, 1, 1));
        when(courseRepository.findByName(input.course().getName())).thenReturn(Optional.of(course));
        try {
            studyEntryService.addStudyEntry(input);
        } catch (Exception e) {
            assertEquals("Date cannot be in the future", e.getMessage());
        }
    }
}
