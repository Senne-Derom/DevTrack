package com.devtrack.service;

import com.devtrack.DTO.LoginUserInput;
import com.devtrack.DTO.RegisterUserInput;
import com.devtrack.DTO.UserOutput;
import com.devtrack.model.User;
import com.devtrack.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public UserOutput login(@Valid LoginUserInput loginInput) {
        User user = userRepository.findByUsername(loginInput.username())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (passwordEncoder.matches(loginInput.password(), user.getPassword())) {
            return new UserOutput(user.getId(), user.getUsername(), user.getEmail());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
