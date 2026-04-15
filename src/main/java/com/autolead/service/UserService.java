package com.autolead.service;

import com.autolead.dto.user.CreateUserRequest;
import com.autolead.dto.user.UserMapper;
import com.autolead.dto.user.UserResponse;
import com.autolead.domain.model.User;
import com.autolead.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(CreateUserRequest request) {

        repository.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new RuntimeException("Email already in use");
                });

        User user = userMapper.toEntity(request);
        //HASH da senha
        user.setPassword(passwordEncoder.encode(request.password()));

        return repository.save(user);
    }
}