package com.devtrack.service;

import com.devtrack.DTO.CourseInput;
import com.devtrack.model.Course;
import com.devtrack.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService (CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(@Valid CourseInput courseInput) {
        Optional<Object> existingCourses = courseRepository.findByName(courseInput.name());
        if (existingCourses.isPresent()) {
            throw new RuntimeException("Course with name " + courseInput.name() + " already exists");
        }

        Course newCourse = new Course();
        newCourse.setName(courseInput.name());
        return courseRepository.save(newCourse);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
