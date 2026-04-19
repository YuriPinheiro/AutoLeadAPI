package com.autolead.service;

import com.autolead.domain.enums.ImageType;
import com.autolead.domain.enums.UserRole;
import com.autolead.domain.model.*;
import com.autolead.domain.security.LeadAccessValidator;
import com.autolead.dto.image.ImageResponse;
import com.autolead.dto.lead.*;
import com.autolead.domain.enums.LeadStatus;
import com.autolead.repository.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LeadService {

    private final LeadRepository repository;
    private final UserRepository userRepository;
    private final LeadStatusHistoryRepository historyRepository;
    private final FavoriteRepository favoriteRepository;
    private final ImageService imageService;
    private final LeadMapper leadMapper;


    public LeadService(LeadRepository repository, UserRepository userRepository, LeadStatusHistoryRepository historyRepository, ImageService imageService, FavoriteRepository favoriteRepository, LeadMapper leadMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.favoriteRepository = favoriteRepository;
        this.leadMapper = leadMapper;
        this.imageService = imageService;
    }

    public LeadResponse create(CreateLeadRequest request, User user) {
        Lead lead = leadMapper.toEntity(request);
        lead.setUser(user);

        repository.save(lead);

        return leadMapper.toResponse(lead, false);
    }

    public List<LeadResponse> list(User user) {
        List<Lead> leads = repository.findAll();

        Set<UUID> favoritedLeadIds = favoriteRepository
                .findByUserId(user.getId())
                .stream()
                .map(f -> f.getLead().getId())
                .collect(Collectors.toSet());

        return leads.stream()
                .map(lead -> leadMapper.toResponse(
                        lead,
                        favoritedLeadIds.contains(lead.getId())
                ))
                .toList();
    }

    public LeadResponse getById(UUID id, User user){

        Lead lead = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        if (!LeadAccessValidator.canAccessLead(user, lead)) {
            throw new RuntimeException("Access denied");
        }

        boolean isFavorited = favoriteRepository
                .findByUserIdAndLeadId(user.getId(), id)
                .isPresent();

        return leadMapper.toResponse(lead, isFavorited);
    }

    public List<LeadResponse> listByUser(User user) {

        List<Lead> leads = repository.findByUserId(user.getId());

        Set<UUID> favoritedLeadIds = favoriteRepository
                .findByUserId(user.getId())
                .stream()
                .map(f -> f.getLead().getId())
                .collect(Collectors.toSet());

        return leads.stream()
                .map(lead -> leadMapper.toResponse(
                        lead,
                        favoritedLeadIds.contains(lead.getId())
                ))
                .toList();
    }

    public LeadStatusHistoryResponse updateStatus(UUID leadId, CreateLeadStatusHistoryRequest request, User user) {

        Lead lead = repository.findById(leadId)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        LeadStatus oldStatus = lead.getStatus();
        LeadStatus newStatus = request.status();

        LeadStatusHistory history = new LeadStatusHistory();
        history.setLead(lead);
        history.setPreviousStatus(oldStatus);
        history.setStatus(newStatus);
        history.setChangedBy(user);

        historyRepository.save(history);

        lead.setStatus(newStatus);

        repository.save(lead);

        return LeadStatusHistoryMapper.toResponse(history);
    }

    public List<LeadStatusHistoryResponse> listHistoryByLeadId (UUID id, User user){

        Lead lead = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        //REGRA DE ACESSO
        if (!LeadAccessValidator.canAccessLead(user, lead)) {
            throw new RuntimeException("Access denied");
        }


        return historyRepository.findByLeadId(id)
                .stream()
                .map(LeadStatusHistoryMapper::toResponse)
                .toList();
    }

    public List<ImageResponse> listImages(UUID leadId, User user){
        Lead lead = repository.findById(leadId)
                .orElseThrow();

        if(!LeadAccessValidator.canAccessLead(user, lead)){
            throw new RuntimeException("Access denied");
        }

        return imageService.listByLeadId(leadId);
    }

    public ImageResponse uploadLeadImage(UUID leadId, MultipartFile file, ImageType type, User user){
        Lead lead = repository.findById(leadId)
                .orElseThrow();

        if(!LeadAccessValidator.canAccessLead(user, lead)){
            throw new RuntimeException("Access denied");
        }

        VehicleImage image = imageService.upload(file, lead);

        return new ImageResponse(image.getId(), image.getUrl());
    }

    public void deleteImage(UUID leadId, UUID imageId, User user){
        Lead lead = repository.findById(leadId)
                .orElseThrow();

        if(!LeadAccessValidator.canAccessLead(user, lead)){
            throw new RuntimeException("Access denied");
        }

        imageService.deleteImage(imageId, leadId);

    }

}
