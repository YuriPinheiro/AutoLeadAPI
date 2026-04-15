package com.autolead.service;

import com.autolead.dto.lead.CreateLeadRequest;
import com.autolead.dto.lead.LeadMapper;
import com.autolead.dto.lead.LeadResponse;
import com.autolead.domain.model.Lead;
import com.autolead.domain.enums.LeadStatus;
import com.autolead.domain.model.User;
import com.autolead.repository.LeadRepository;
import com.autolead.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeadService {

    private final LeadRepository repository;
    private final UserRepository userRepository;
    private final LeadMapper leadMapper;

    public LeadService(LeadRepository repository, UserRepository userRepository, LeadMapper leadMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.leadMapper = leadMapper;
    }

    public LeadResponse create(CreateLeadRequest request, User user) {
        Lead lead = leadMapper.toEntity(request);
        lead.setUser(user);

        repository.save(lead);

        return leadMapper.toResponse(lead);
    }

    public List<LeadResponse> list() {
        return repository.findAll()
                .stream()
                .map(leadMapper::toResponse)
                .toList();
    }

    public List<LeadResponse> listByUser(User user) {
        return repository.findByUserId(user.getId())
                .stream()
                .map(leadMapper::toResponse)
                .toList();
    }

}
