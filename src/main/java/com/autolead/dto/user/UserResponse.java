package com.autolead.dto.user;

import com.autolead.domain.enums.UserRole;

import java.util.UUID;

public record UserResponse(

        String name,
        String email,
        String phone,
        UserRole role

) {}