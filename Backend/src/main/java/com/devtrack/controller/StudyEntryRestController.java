package com.devtrack.controller;

import com.devtrack.DTO.StudyEntryInput;
import com.devtrack.model.StudyEntry;
import com.devtrack.service.StudyEntryService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("study_entries")
public class StudyEntryRestController {
    private final StudyEntryService studyEntryService;

    public StudyEntryRestController(StudyEntryService studyEntryService) {
        this.studyEntryService = studyEntryService;
    }

    @GetMapping
    public List<StudyEntry> getStudyEntries() {
        return studyEntryService.getStudyEntries();
    }

    @PostMapping("/addStudyEntry")
    public StudyEntry addStudyEntry(@RequestBody @Valid StudyEntryInput studyEntryInput) {
        return studyEntryService.addStudyEntry(studyEntryInput);
    }
}
