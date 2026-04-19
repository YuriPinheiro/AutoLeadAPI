package com.autolead.service;

import com.autolead.domain.model.Favorite;
import com.autolead.domain.model.Lead;
import com.autolead.domain.model.User;
import com.autolead.dto.lead.LeadSummaryResponse;
import com.autolead.repository.FavoriteRepository;
import com.autolead.repository.LeadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteService {

    private final FavoriteRepository repository;
    private final LeadRepository leadRepository;

    public FavoriteService(FavoriteRepository repository, LeadRepository leadRepository) {
        this.repository = repository;
        this.leadRepository = leadRepository;
    }

    public void favorite(UUID leadId, User user) {

        boolean exists = repository
                .findByUserIdAndLeadId(user.getId(), leadId)
                .isPresent();

        if (exists) return;

        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setLead(lead);

        repository.save(favorite);
    }

    public void unfavorite(UUID leadId, User user) {
        repository.deleteByUserIdAndLeadId(user.getId(), leadId);
    }

    public List<LeadSummaryResponse> listMyFavorites(User user) {
        return repository.findByUserId(user.getId())
                .stream()
                .map(f -> {
                    Lead lead = f.getLead();
                    return new LeadSummaryResponse(
                            lead.getId(),
                            lead.getBrand(),
                            lead.getModel(),
                            lead.getYear(),
                            lead.getDesiredPrice(),
                            lead.getStatus(),
                            null,
                            true
                    );
                })
                .toList();
    }

}