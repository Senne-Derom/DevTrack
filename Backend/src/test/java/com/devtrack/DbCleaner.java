package com.devtrack;

import com.devtrack.repository.CourseRepository;
import com.devtrack.repository.StudyEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbCleaner {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudyEntryRepository studyEntryRepository;

    @Autowired
    private DbInitializer dbInitializer;

    public void cleanDatabase() {
        studyEntryRepository.deleteAll();
        courseRepository.deleteAll();
        dbInitializer.initialize();
    }

    public void emptyDatabase() {
        studyEntryRepository.deleteAll();
        courseRepository.deleteAll();
    }
}
