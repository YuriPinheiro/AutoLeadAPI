package com.autolead.dto.lead;

import com.autolead.domain.model.LeadInteraction;

public class LeadInteractionMapper {

    public static LeadInteractionResponse toResponse(LeadInteraction entity) {
        if (entity == null) return null;

        return new LeadInteractionResponse(
                entity.getId(),
                entity.getDescription(),
                entity.getType(),
                entity.getCreatedAt(),

                entity.getLead().getId(),

                entity.getUser().getId(),
                entity.getUser().getEmail()
        );
    }

    public static LeadInteraction toEntity(CreateLeadInteractionRequest request) {
        if (request == null) return null;

        LeadInteraction entity = new LeadInteraction();
        entity.setDescription(request.description());
        entity.setType(request.type());

        return entity;
    }
}
