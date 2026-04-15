package com.autolead.dto.lead;

import com.autolead.domain.enums.LeadStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record LeadResponse(

        UUID id,
        String brand,
        String model,
        Integer year,
        Integer mileage,
        BigDecimal desiredPrice,
        String description,
        LeadStatus status,
        Long createdAt

) {}