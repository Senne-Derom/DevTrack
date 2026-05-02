package com.devtrack.controller;

import com.devtrack.DTO.StudyEntryInput;
import com.devtrack.model.StudyEntry;
import com.devtrack.service.StudyEntryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("study_entries")
public class StudyEntryController {
    private final StudyEntryService studyEntryService;

    public StudyEntryController(StudyEntryService studyEntryService) {
        this.studyEntryService = studyEntryService;
    }

    @PostMapping("/addStudyEntry")
    public StudyEntry addStudyEntry(@RequestBody @Valid StudyEntryInput studyEntryInput) {
        return studyEntryService.addStudyEntry(studyEntryInput);
    }
}
