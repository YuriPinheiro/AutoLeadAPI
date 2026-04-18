package com.autolead.repository;

import com.autolead.domain.enums.InteractionType;
import com.autolead.domain.model.LeadInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LeadInteractionRepository extends JpaRepository<LeadInteraction, UUID> {

    List<LeadInteraction> findByLeadId(UUID leadId);

    List<LeadInteraction> findByUserId(UUID userId);

}
