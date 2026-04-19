package com.autolead.dto.lead;

import com.autolead.domain.enums.LeadStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record LeadSummaryResponse(
        UUID id,
        String brand,
        String model,
        Integer year,
        BigDecimal price,
        LeadStatus status,
        String thumbnailUrl,
        boolean isFavorited
) {}
