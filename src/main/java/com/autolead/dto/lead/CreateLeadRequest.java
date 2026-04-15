package com.autolead.dto.lead;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateLeadRequest(

        @NotNull
        UUID userId,

        @NotBlank
        String brand,

        @NotBlank
        String model,

        Integer year,
        Integer mileage,
        BigDecimal desiredPrice,
        String description

) {}