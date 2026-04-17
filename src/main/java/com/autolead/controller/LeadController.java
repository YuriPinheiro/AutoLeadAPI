package com.autolead.controller;

import com.autolead.domain.enums.ImageType;
import com.autolead.domain.model.User;
import com.autolead.dto.image.ImageResponse;
import com.autolead.dto.lead.CreateLeadRequest;
import com.autolead.dto.lead.CreateLeadStatusHistoryRequest;
import com.autolead.dto.lead.LeadResponse;
import com.autolead.dto.lead.LeadStatusHistoryResponse;
import com.autolead.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leads")
public class LeadController {

    private final LeadService service;

    public LeadController(LeadService service) {
        this.service = service;
    }

    @PostMapping
    public LeadResponse create(
            @Valid @RequestBody CreateLeadRequest request,
            @AuthenticationPrincipal User user
    ){
        return service.create(request, user);
    }

    @GetMapping
    public List<LeadResponse> list(
            @AuthenticationPrincipal User user
    ) {
        return service.listByUser(user);
    }

    @GetMapping("/{id}")
    public LeadResponse get(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        return service.getById(id, user);
    }

    @GetMapping("/{id}/history")
    public List<LeadStatusHistoryResponse> listLeadHistory(
        @PathVariable UUID id,
        @AuthenticationPrincipal User user
    ) {
        return service.listHistoryByLeadId(id, user);
    }

    @PostMapping("/{id}/images")
    public ImageResponse uploadImage(
            @PathVariable UUID id,
            @RequestParam("file")MultipartFile file,
            @RequestParam(value = "type", required = false) ImageType type,
            @AuthenticationPrincipal User user
    ){
        return service.uploadLeadImage(id, file, type, user);
    }

    @GetMapping("/{id}/images")
    public List<ImageResponse> listImages(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        return service.listImages(id, user);
    }

    @DeleteMapping("/{leadId}/images/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable UUID leadId,
            @PathVariable UUID imageId,
            @AuthenticationPrincipal User user
    ) {
        service.deleteImage(leadId, imageId, user);
        return ResponseEntity.noContent().build();
    }
}