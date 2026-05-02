package com.devtrack.controller;

import com.devtrack.DTO.CourseInput;
import com.devtrack.model.Course;
import com.devtrack.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController (CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/addCourse")
    public Course addCourse(@RequestBody @Valid CourseInput courseInput) {
        return courseService.addCourse(courseInput);
    }
}
