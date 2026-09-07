package com.devtrack.DTO;


public record AuthenticationResponse(
        String message,
        String authToken,
        String username,
        String email
) {
}
