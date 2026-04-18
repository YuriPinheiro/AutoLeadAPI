package com.autolead.dto.lead;

import com.autolead.domain.enums.InteractionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLeadInteractionRequest(
        @NotBlank
        String description,
        @NotNull
        InteractionType type
        ){}
