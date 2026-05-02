package com.devtrack.DTO;

import jakarta.validation.constraints.NotBlank;

public record CourseInput(
        @NotBlank
        String name
) {
}
