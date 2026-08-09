package com.devtrack.unit;

import com.devtrack.DTO.CourseInput;
import com.devtrack.model.Course;
import com.devtrack.repository.CourseRepository;
import com.devtrack.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void givenGetAllCourses_whenCoursesExist_thenReturnListOfCourses() {
        Course course1 = new Course("Course 1", 3);
        Course course2 = new Course("Course 2", 4);
        when(courseRepository.findAll()).thenReturn(List.of(course1, course2));
        List<Course> courses = courseService.getAllCourses();
        assertEquals(2, courses.size());
    }

    @Test
    void givenGetAllCourses_whenNoCoursesExist_thenReturnEmptyList() {
        when(courseRepository.findAll()).thenReturn(List.of());
        List<Course> courses = courseService.getAllCourses();

        assertEquals(0, courses.size());
    }

    @Test
    void givenAddCourse_whenValidInput_thenReturnSavedCourse() {
        CourseInput input = new CourseInput("Course 1", 3);
        Course savedCourse = new Course("Course 1", 3);
        when(courseRepository.findByName(input.name())).thenReturn(Optional.empty());
        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);
        Course result = courseService.addCourse(input);

        assertEquals(savedCourse.getName(), result.getName());
        assertEquals(savedCourse.getStudy_points(), result.getStudy_points());

        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void givenAddCourse_whenCourseAlreadyExists_thenThrowException() {
        CourseInput input = new CourseInput("Course 1", 3);
        when(courseRepository.findByName(input.name())).thenReturn(Optional.of(new Course()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseService.addCourse(input));
        assertEquals("Course with name Course 1 already exists", exception.getMessage());

        verify(courseRepository, never()).save(any(Course.class));
    }
}
