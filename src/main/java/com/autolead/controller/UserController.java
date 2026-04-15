package com.autolead.controller;

import com.autolead.domain.model.User;
import com.autolead.dto.user.CreateUserRequest;
import com.autolead.dto.user.UserResponse;
import com.autolead.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        User user = service.create(request);

        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }
}