package com.devtrack;

import com.devtrack.model.Course;
import com.devtrack.model.StudyEntry;
import com.devtrack.repository.CourseRepository;
import com.devtrack.repository.StudyEntryRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DbInitializer {
    private final CourseRepository courseRepository;
    private final StudyEntryRepository studyEntryRepository;

    public DbInitializer(CourseRepository courseRepository, StudyEntryRepository studyEntryRepository) {
        this.courseRepository = courseRepository;
        this.studyEntryRepository = studyEntryRepository;
    }

    @PostConstruct
    @Transactional
    public void initialize() {
        Course course1 = new Course("Programming 1", 6);
        Course course2 = new Course("Server and System Management", 6);
        Course course3 = new Course("Software Engineering", 6);
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);

        studyEntryRepository.save(new StudyEntry((Course) courseRepository.findByName("Programming 1").orElseThrow(), "Completed exercises from class", 1, LocalDate.now()));
        studyEntryRepository.save(new StudyEntry((Course) courseRepository.findByName("Server and System Management").orElseThrow(), "Made DNS assignment", 2.5, LocalDate.now()));
        studyEntryRepository.save(new StudyEntry((Course) courseRepository.findByName("Software Engineering").orElseThrow(), "Implemented Flyway", 2, LocalDate.now()));
    }
}
