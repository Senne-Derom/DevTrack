package com.devtrack;

import com.devtrack.model.Course;
import com.devtrack.model.StudyEntry;
import com.devtrack.model.User;
import com.devtrack.repository.CourseRepository;
import com.devtrack.repository.StudyEntryRepository;
import com.devtrack.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DbInitializer {
    private final CourseRepository courseRepository;
    private final StudyEntryRepository studyEntryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DbInitializer(CourseRepository courseRepository, StudyEntryRepository studyEntryRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.courseRepository = courseRepository;
        this.studyEntryRepository = studyEntryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void initialize() {
        Course course1 = new Course("Programming 1", 6);
        Course course2 = new Course("Server and System Management", 6);
        Course course3 = new Course("Software Engineering", 6);

        StudyEntry entry1 = new StudyEntry(course1, "Completed exercises from class", 1, LocalDate.now());
        StudyEntry entry2 = new StudyEntry(course2, "Made DNS assignment", 2.5, LocalDate.now());
        StudyEntry entry3 = new StudyEntry(course3, "Implemented Flyway", 2, LocalDate.now());

        User user1 = new User("John Doe", "johndoe@mail.com", passwordEncoder.encode("John1234"));
        User user2 = new User("Jane Smith", "janesmith@mail.com", passwordEncoder.encode("Jane1234"));

        userRepository.save(user1);
        userRepository.save(user2);

        course1.setUser(user1);
        course2.setUser(user1);
        course3.setUser(user2);

        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);

        studyEntryRepository.save(entry1);
        studyEntryRepository.save(entry2);
        studyEntryRepository.save(entry3);
    }
}
