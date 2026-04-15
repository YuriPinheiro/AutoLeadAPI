package com.autolead.dto.user;

import com.autolead.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {
       User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPhone(request.phone());

        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }
}
