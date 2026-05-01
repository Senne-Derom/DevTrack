package com.devtrack.service;

import com.devtrack.repository.StudyEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class StudyEntryService {
    private final StudyEntryRepository studyEntryRepository;

    public StudyEntryService(StudyEntryRepository studyEntryRepository) {
        this.studyEntryRepository = studyEntryRepository;
    }
}
