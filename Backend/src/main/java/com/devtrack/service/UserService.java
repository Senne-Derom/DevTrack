package com.devtrack.service;

import com.devtrack.DTO.AuthenticationResponse;
import com.devtrack.DTO.LoginUserInput;
import com.devtrack.DTO.RegisterUserInput;
import com.devtrack.DTO.UserOutput;
import com.devtrack.model.User;
import com.devtrack.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserOutput registerUser(RegisterUserInput registerUserInput) {
        User user = new User();
        if (userRepository.existsByUsername(registerUserInput.username())) {
            throw new RuntimeException("Username already exists");
        } else if (userRepository.existsByEmail(registerUserInput.email())) {
            throw new RuntimeException("Email already exists");
        } else {
            user.setUsername(registerUserInput.username());
            user.setEmail(registerUserInput.email());
            String encodedPassword = passwordEncoder.encode(registerUserInput.password());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return new UserOutput(user.getId(), user.getUsername(), user.getEmail());
        }
    }

    public AuthenticationResponse login(LoginUserInput loginInput) {
        final var usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(loginInput.username(), loginInput.password());
        final var authentication = authenticationManager.authenticate(usernamePasswordAuthentication);
        final var user = ((UserDetailsImpl) authentication.getPrincipal()).user();
        final var token = jwtService.generateToken(user);
        return new AuthenticationResponse(
                "Authentication successful.",
                token,
                user.getUsername(),
                user.getEmail()
        );
    }
}
