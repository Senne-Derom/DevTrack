package com.devtrack.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseInput(
        @NotBlank
        String name,
        @NotNull
        @Min(value = 0, message = "Study points cannot be negative")
        int study_points
) {
}
