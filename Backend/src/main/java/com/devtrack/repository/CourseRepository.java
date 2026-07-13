package com.devtrack.repository;

import com.devtrack.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Object> findByName(String mathematics);
}
