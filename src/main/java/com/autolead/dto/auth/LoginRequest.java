package com.autolead.dto.auth;

public record LoginRequest(
        String email,
        String password
) {}