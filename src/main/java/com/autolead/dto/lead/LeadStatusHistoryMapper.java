package com.autolead.dto.lead;

import com.autolead.domain.model.LeadStatusHistory;

public class LeadStatusHistoryMapper {

    public static LeadStatusHistoryResponse toResponse(LeadStatusHistory entity) {
        return new LeadStatusHistoryResponse(
                entity.getId(),
                //entity.getLead().getId(),
                entity.getPreviousStatus(),
                entity.getStatus(),
                //entity.getChangedBy().getId(),
                entity.getUpdatedAt()
        );
    }
}