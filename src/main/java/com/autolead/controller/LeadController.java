package com.autolead.controller;

import com.autolead.domain.model.User;
import com.autolead.dto.lead.CreateLeadRequest;
import com.autolead.dto.lead.CreateLeadStatusHistoryRequest;
import com.autolead.dto.lead.LeadResponse;
import com.autolead.dto.lead.LeadStatusHistoryResponse;
import com.autolead.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public LeadResponse get(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        return service.getById(id, user);
    }

    @GetMapping
    public List<LeadResponse> list(
            @AuthenticationPrincipal User user
    ) {
        return service.listByUser(user);
    }

    @GetMapping("/{id}/history")
    public List<LeadStatusHistoryResponse> listLeadHistory(
        @PathVariable UUID id,
        @AuthenticationPrincipal User user
    ) {
        return service.listHistoryByLeadId(id, user);
    }
}