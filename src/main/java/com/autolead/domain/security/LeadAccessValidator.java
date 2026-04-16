package com.autolead.domain.security;

import com.autolead.domain.enums.UserRole;
import com.autolead.domain.model.Lead;
import com.autolead.domain.model.User;

public class LeadAccessValidator {

    public static boolean canAccessLead(User user, Lead lead) {
        boolean isOwner = lead.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == UserRole.ADMIN;

        return isOwner || isAdmin;
    }
}