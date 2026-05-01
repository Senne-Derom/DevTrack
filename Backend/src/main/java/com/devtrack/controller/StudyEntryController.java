package com.devtrack.controller;

import com.devtrack.service.StudyEntryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("study_entries")
public class StudyEntryController {
    private final StudyEntryService studyEntryService;

    public StudyEntryController(StudyEntryService studyEntryService) {
        this.studyEntryService = studyEntryService;
    }
}
