package com.devtrack.controller;

import com.devtrack.service.CourseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController (CourseService courseService) {
        this.courseService = courseService;
    }
}
