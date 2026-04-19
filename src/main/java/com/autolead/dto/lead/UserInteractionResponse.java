package com.autolead.dto.lead;

import com.autolead.domain.enums.InteractionType;

import java.time.Instant;
import java.util.UUID;

public record UserInteractionResponse(
        UUID id,
        String description,
        InteractionType type,
        Instant createdAt,

        UUID leadId,
        String brand,
        String model,

        UUID adminId,
        String adminEmail
) {
}
