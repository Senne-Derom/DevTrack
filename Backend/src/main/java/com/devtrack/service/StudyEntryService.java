package com.devtrack.service;

import com.devtrack.DTO.StudyEntryInput;
import com.devtrack.model.Course;
import com.devtrack.model.StudyEntry;
import com.devtrack.repository.CourseRepository;
import com.devtrack.repository.StudyEntryRepository;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StudyEntryService {
    private final StudyEntryRepository studyEntryRepository;
    private final CourseRepository courseRepository;

    public StudyEntryService(StudyEntryRepository studyEntryRepository, CourseRepository courseRepository) {
        this.studyEntryRepository = studyEntryRepository;
        this.courseRepository = courseRepository;
    }

    public StudyEntry addStudyEntry(@Valid StudyEntryInput studyEntryInput) {
        StudyEntry newStudyEntry = new StudyEntry();
        newStudyEntry.setCourse(studyEntryInput.course());
        newStudyEntry.setDescription(studyEntryInput.description());
        newStudyEntry.setTimeSpent(studyEntryInput.timeSpent());
        newStudyEntry.setDate(studyEntryInput.date());

        Course foundCourse = studyEntryInput.course();
        courseRepository.save(foundCourse);

        return studyEntryRepository.save(newStudyEntry);
    }

    public List<StudyEntry> getStudyEntries() {
        return studyEntryRepository.findAll();
    }
}
