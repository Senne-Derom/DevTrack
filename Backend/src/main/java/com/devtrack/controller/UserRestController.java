package com.devtrack.controller;

import com.devtrack.DTO.LoginUserInput;
import com.devtrack.DTO.RegisterUserInput;
import com.devtrack.DTO.UserOutput;
import com.devtrack.model.User;
import com.devtrack.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public UserOutput registerUser(@RequestBody @Valid RegisterUserInput registerUserInput) {
        return userService.registerUser(registerUserInput);
    }

    @PostMapping("/login")
    public UserOutput login(@RequestBody @Valid LoginUserInput loginInput) {
        return userService.login(loginInput);
    }
}
