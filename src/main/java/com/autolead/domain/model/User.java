package com.autolead.domain.model;

import com.autolead.domain.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = "vehicleLeads")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean isActive;

    private Instant createdAt;

    // Relacionamento LeadModel
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Lead> vehicleLeads;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();

        if (this.isActive == null) {
            this.isActive = true;
        }

        if (this.role == null) {
            this.role = UserRole.CLIENT;
        }
    }
}