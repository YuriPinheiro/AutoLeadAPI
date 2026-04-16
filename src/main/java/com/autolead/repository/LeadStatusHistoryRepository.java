package com.autolead.repository;

import com.autolead.domain.model.LeadStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LeadStatusHistoryRepository extends JpaRepository<LeadStatusHistory, UUID> {
    List<LeadStatusHistory> findByLeadId(UUID leadId);
}
