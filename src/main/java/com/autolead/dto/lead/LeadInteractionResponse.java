package com.autolead.dto.lead;

import com.autolead.domain.enums.InteractionType;

import java.time.Instant;
import java.util.UUID;

public record LeadInteractionResponse(
        UUID id,
        String description,
        InteractionType type,
        Instant createdAt,

        UUID leadId,

        UUID adminId,
        String adminEmail
) {
}
