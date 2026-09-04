package com.devtrack.DTO;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserInput(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password) {
}
