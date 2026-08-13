package com.devtrack.controller;

import com.devtrack.DTO.CourseInput;
import com.devtrack.model.Course;
import com.devtrack.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseRestController {
    private final CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping("/addCourse")
    public Course addCourse(@RequestBody @Valid CourseInput courseInput) {
        return courseService.addCourse(courseInput);
    }
}
