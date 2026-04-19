package com.autolead.service;

import com.autolead.domain.model.Lead;
import com.autolead.domain.model.LeadInteraction;
import com.autolead.domain.model.User;
import com.autolead.domain.security.LeadAccessValidator;
import com.autolead.dto.lead.*;
import com.autolead.repository.LeadInteractionRepository;
import com.autolead.repository.LeadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InteractionService {

    private final LeadInteractionRepository repository;
    private final LeadRepository leadRepository;

    public InteractionService(LeadInteractionRepository repository, LeadRepository leadRepository){
        this.repository = repository;
        this.leadRepository = leadRepository;
    }

    public void registerLeadInteraction(UUID leadId, CreateLeadInteractionRequest request, User user){
        if(!LeadAccessValidator.isAdmin(user)){
            throw new RuntimeException("Access denied");
        }

        Lead lead = leadRepository.findById(leadId)
                .orElseThrow();


        LeadInteraction interaction = LeadInteractionMapper.toEntity(request);
        interaction.setLead(lead);
        interaction.setUser(user);

        repository.save(interaction);

    }

    public List<LeadInteractionResponse> listLeadInteractions(UUID id, User user){
        Lead lead = leadRepository.findById(id)
                .orElseThrow();

        if(!LeadAccessValidator.canAccessLead(user, lead)){
            throw new RuntimeException("Access denied");
        }

        return repository.findByLeadId(id)
                .stream()
                .map(LeadInteractionMapper::toResponse)
                .toList();
    }

    public List<UserInteractionResponse> listUserInteractions(UUID id, User user){
        if(!LeadAccessValidator.isAdmin(user)){
            throw new RuntimeException("Access denied");
        }

        return repository.findByUserId(id)
                .stream()
                .map(UserInteractionMapper::toResponse)
                .toList();
    }

    public void deleteLeadInteraction(UUID leadId, UUID interactionId, User user){
        if(!LeadAccessValidator.isAdmin(user)){
            throw new RuntimeException("Access denied");
        }

        LeadInteraction interaction = repository.findById(interactionId)
                .orElseThrow();

        if(!interaction.getLead().getId().equals(leadId)){
            throw new RuntimeException("Interaction does'nt belong this lead");
        }

        repository.delete(interaction);
    }


}
