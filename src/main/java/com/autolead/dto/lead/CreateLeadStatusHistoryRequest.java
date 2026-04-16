package com.autolead.dto.lead;

import com.autolead.domain.enums.LeadStatus;
import jakarta.validation.constraints.NotNull;

public record CreateLeadStatusHistoryRequest(

        @NotNull
        LeadStatus status

) {}