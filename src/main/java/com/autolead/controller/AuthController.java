package com.autolead.controller;

import com.autolead.dto.auth.LoginRequest;
import com.autolead.dto.auth.LoginResponse;
import com.autolead.dto.user.CreateUserRequest;
import com.autolead.dto.user.UserResponse;
import com.autolead.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody CreateUserRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return service.login(request);
    }
}
