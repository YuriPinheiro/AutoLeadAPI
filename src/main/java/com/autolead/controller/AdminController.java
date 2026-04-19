package com.autolead.controller;

import com.autolead.domain.model.User;
import com.autolead.dto.lead.*;
import com.autolead.service.InteractionService;
import com.autolead.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final LeadService leadService;
    private final InteractionService interactionService;

    public AdminController(LeadService leadService, InteractionService interactionService) {
        this.leadService = leadService;
        this.interactionService = interactionService;
    }

    @GetMapping("/leads")
    public List<LeadResponse> listAll(
            @AuthenticationPrincipal User user
    ) {
        return leadService.list(user);
    }

    @GetMapping("/leads/{id}")
    public LeadResponse get(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        return leadService.getById(id, user);
    }

    @PatchMapping("/leads/{id}/status")
    public LeadStatusHistoryResponse updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid CreateLeadStatusHistoryRequest request,
            @AuthenticationPrincipal User user
    ){
        return leadService.updateStatus(id, request, user);
    }

    @GetMapping("/user/{id}/interactions")
    public List<UserInteractionResponse> getInterationsByUser(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ){
        return interactionService.listUserInteractions(id, user);
    }
}