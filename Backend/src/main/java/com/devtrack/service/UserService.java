package com.devtrack.service;

import com.devtrack.DTO.UserInput;
import com.devtrack.model.User;
import com.devtrack.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserInput userInput) {
        User user = new User();
        if (userRepository.existsByUsername(userInput.username())) {
            throw new RuntimeException("Username already exists");
        } else if (userRepository.existsByEmail(userInput.email())) {
            throw new RuntimeException("Email already exists");
        } else {
            user.setUsername(userInput.username());
            user.setEmail(userInput.email());
            String encodedPassword = passwordEncoder.encode(userInput.password());
            user.setPassword(encodedPassword);
            return userRepository.save(user);
        }
    }
}
