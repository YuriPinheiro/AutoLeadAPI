package com.autolead.controller;

import com.autolead.dto.lead.LeadResponse;
import com.autolead.service.LeadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}