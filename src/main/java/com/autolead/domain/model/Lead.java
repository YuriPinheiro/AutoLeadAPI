package com.autolead.domain.model;

import com.autolead.domain.enums.LeadStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_leads")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Quem cadastrou
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    private Integer year;

    private Integer mileage;

    private BigDecimal desiredPrice;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private LeadStatus status;

    private Instant updatedAt;

    @JsonIgnore
    private Instant createdAt;

    @JsonProperty("createdAt")
    public Long getCreatedAt() {
        return createdAt != null ? createdAt.toEpochMilli() : null;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();

        if (this.status == null) {
            this.status = LeadStatus.NEW;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}