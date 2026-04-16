package com.autolead.dto.lead;

import com.autolead.domain.enums.LeadStatus;

import java.time.Instant;
import java.util.UUID;

public record LeadStatusHistoryResponse(

        UUID id,
        //UUID leadId,
        LeadStatus previousStatus,
        LeadStatus status,
        //UUID changedBy,
        Instant updatedAt

) {}