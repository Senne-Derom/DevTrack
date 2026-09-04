package com.devtrack.DTO;


public record AuthenticationResponse(
        String message,
        String token,
        String username,
        String email
) {
}
