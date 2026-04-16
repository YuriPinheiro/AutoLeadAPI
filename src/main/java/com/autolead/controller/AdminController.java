package com.autolead.controller;

import com.autolead.domain.model.User;
import com.autolead.dto.lead.CreateLeadStatusHistoryRequest;
import com.autolead.dto.lead.LeadResponse;
import com.autolead.dto.lead.LeadStatusHistoryResponse;
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

    public AdminController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping("/leads")
    public List<LeadResponse> listAll() {
        return leadService.list();
    }

    @PatchMapping("/leads/{id}/status")
    public LeadStatusHistoryResponse updateStatus(
            @PathVariable UUID id,
            @RequestBody @Valid CreateLeadStatusHistoryRequest request,
            @AuthenticationPrincipal User user
    ){
        return leadService.updateStatus(id, request, user);
    }
}