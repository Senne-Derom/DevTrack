package com.devtrack.DTO;

import com.devtrack.model.Course;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.aspectj.lang.annotation.Before;

import java.time.LocalDate;

public record StudyEntryInput(
        @NotNull
        Course course,
        @NotBlank
        String description,
        @NotNull
        @Min(value = 0, message = "Time spent cannot be negative")
        double timeSpent,
        @NotNull
        @PastOrPresent(message = "Date cannot be in the future")
        LocalDate date
) {
}
