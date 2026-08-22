package com.devtrack.controller;

import com.devtrack.DTO.UserInput;
import com.devtrack.model.User;
import com.devtrack.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User registerUser(UserInput userInput) {
        return userService.registerUser(userInput);
    }
}
