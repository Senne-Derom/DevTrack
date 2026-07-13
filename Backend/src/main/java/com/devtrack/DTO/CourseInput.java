package com.devtrack.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseInput(
        @NotBlank
        String name,
        @NotNull
        int study_points
) {
}
