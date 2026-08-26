package com.devtrack.unit;

import com.devtrack.DTO.UserInput;
import com.devtrack.model.User;
import com.devtrack.repository.UserRepository;
import com.devtrack.service.UserService;
import org.h2.engine.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void givenRegisterUser_whenValidInput_thenReturnSavedUser() {
        UserInput userInput = new UserInput("testuser", "test.user@mail.com", "rawPassword123");
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test.user@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("rawPassword123")).thenReturn("encodedPassword123");
        User mockSavedUser = new User();
        mockSavedUser.setUsername("testuser");
        mockSavedUser.setEmail("test.user@mail.com");
        mockSavedUser.setPassword("encodedPassword123");
        when(userRepository.save(any(User.class))).thenReturn(mockSavedUser);
        User actualSavedUser = userService.registerUser(userInput);
        assertNotNull(actualSavedUser);
        assertEquals("testuser", actualSavedUser.getUsername());
        assertEquals("test.user@mail.com", actualSavedUser.getEmail());
        verify(passwordEncoder, times(1)).encode("rawPassword123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void givenRegisterUser_whenUsernameExists_thenThrowException() {
        UserInput userInput = new UserInput("existinguser", "existing.user@mail.com", "rawPassword123");
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userInput));
    }

    @Test
    void givenRegisterUser_whenEmailExists_thenThrowException() {
        UserInput userInput = new UserInput("newuser", "existing.user@mail.com", "rawPassword123");
        when(userRepository.existsByEmail("existing.user@mail.com")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userInput));
    }
}
