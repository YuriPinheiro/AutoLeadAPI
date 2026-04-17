package com.autolead.service;

import com.autolead.domain.enums.ImageType;
import com.autolead.domain.enums.UserRole;
import com.autolead.domain.model.LeadStatusHistory;
import com.autolead.domain.model.VehicleImage;
import com.autolead.domain.security.LeadAccessValidator;
import com.autolead.dto.image.ImageResponse;
import com.autolead.dto.lead.*;
import com.autolead.domain.model.Lead;
import com.autolead.domain.enums.LeadStatus;
import com.autolead.domain.model.User;
import com.autolead.repository.ImageRepository;
import com.autolead.repository.LeadRepository;
import com.autolead.repository.LeadStatusHistoryRepository;
import com.autolead.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class LeadService {

    private final LeadRepository repository;
    private final UserRepository userRepository;
    private final LeadStatusHistoryRepository historyRepository;
    private final ImageService imageService;
    private final LeadMapper leadMapper;


    public LeadService(LeadRepository repository, UserRepository userRepository, LeadStatusHistoryRepository historyRepository, ImageService imageService, LeadMapper leadMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.leadMapper = leadMapper;
        this.imageService = imageService;
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

    public LeadResponse getById(UUID id, User user){
        Lead lead = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        //REGRA DE ACESSO
        if (!LeadAccessValidator.canAccessLead(user, lead)) {
            throw new RuntimeException("Access denied");
        }

        return leadMapper.toResponse(lead);
    }

    public List<LeadResponse> listByUser(User user) {
        return repository.findByUserId(user.getId())
                .stream()
                .map(leadMapper::toResponse)
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
