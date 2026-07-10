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
        Course course1 = new Course("Mathematics");
        Course course2 = new Course("Physics");
        Course course3 = new Course("Chemistry");
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);

        studyEntryRepository.save(new StudyEntry((Course) courseRepository.findByName("Mathematics").orElseThrow(), "Calculus homework", LocalDate.now()));
        studyEntryRepository.save(new StudyEntry((Course) courseRepository.findByName("Physics").orElseThrow(), "Lab report", LocalDate.now()));
        studyEntryRepository.save(new StudyEntry((Course) courseRepository.findByName("Chemistry").orElseThrow(), "Organic chemistry notes", LocalDate.now()));
    }
}
