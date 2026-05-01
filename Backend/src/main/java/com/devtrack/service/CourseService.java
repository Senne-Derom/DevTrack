package com.devtrack.service;

import com.devtrack.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService (CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
}
