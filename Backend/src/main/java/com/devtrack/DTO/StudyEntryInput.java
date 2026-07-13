package com.devtrack.DTO;

import com.devtrack.model.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record StudyEntryInput(
        @NotNull
        Course course,
        @NotBlank
        String description,
        @NotNull
        double time,
        @NotNull
        LocalDate date
) {
}
