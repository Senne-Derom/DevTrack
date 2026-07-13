package com.devtrack.service;

import com.devtrack.DTO.CourseInput;
import com.devtrack.model.Course;
import com.devtrack.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService (CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(@Valid CourseInput courseInput) {
        Course newCourse = new Course();
        newCourse.setName(courseInput.name());
        return courseRepository.save(newCourse);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
