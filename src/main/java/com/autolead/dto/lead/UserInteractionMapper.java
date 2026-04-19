package com.autolead.dto.lead;

import com.autolead.domain.model.LeadInteraction;

public class UserInteractionMapper {

    public static UserInteractionResponse toResponse(LeadInteraction entity) {
        if (entity == null) return null;

        return new UserInteractionResponse(
                entity.getId(),
                entity.getDescription(),
                entity.getType(),
                entity.getCreatedAt(),

                entity.getLead().getId(),
                entity.getLead().getBrand(),
                entity.getLead().getModel(),

                entity.getUser().getId(),
                entity.getUser().getEmail()
        );
    }

}
