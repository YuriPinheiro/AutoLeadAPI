package com.autolead.controller;

import com.autolead.domain.model.User;
import com.autolead.dto.lead.CreateLeadRequest;
import com.autolead.dto.lead.LeadResponse;
import com.autolead.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}