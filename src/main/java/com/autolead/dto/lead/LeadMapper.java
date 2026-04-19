package com.autolead.dto.lead;

import com.autolead.domain.model.Lead;
import com.autolead.dto.lead.CreateLeadRequest;
import com.autolead.dto.lead.LeadResponse;
import org.springframework.stereotype.Component;

@Component
public class LeadMapper {

    public Lead toEntity(CreateLeadRequest request) {
        Lead lead = new Lead();

        lead.setBrand(request.brand());
        lead.setModel(request.model());
        lead.setYear(request.year());
        lead.setMileage(request.mileage());
        lead.setDesiredPrice(request.desiredPrice());
        lead.setDescription(request.description());

        return lead;
    }

    public LeadResponse toResponse(Lead lead, Boolean isFavorited) {
        return new LeadResponse(
                lead.getId(),
                lead.getBrand(),
                lead.getModel(),
                lead.getYear(),
                lead.getMileage(),
                lead.getDesiredPrice(),
                lead.getDescription(),
                isFavorited,
                lead.getStatus(),
                lead.getCreatedAt()
        );
    }
}