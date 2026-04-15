package com.autolead.service;

import com.autolead.config.JwtService;
import com.autolead.domain.model.User;
import com.autolead.dto.auth.LoginRequest;
import com.autolead.dto.auth.LoginResponse;
import com.autolead.dto.user.CreateUserRequest;
import com.autolead.dto.user.UserMapper;
import com.autolead.dto.user.UserResponse;
import com.autolead.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserService userService;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserService userService,
                       UserMapper userMapper,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;

        this.userService = userService;
        this.jwtService = jwtService;
    }

    public UserResponse register(CreateUserRequest request) {

        User saved = userService.create(request);

        return userMapper.toResponse(saved);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
    }

}
