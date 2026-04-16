package com.autolead.domain.model;

import com.autolead.domain.enums.LeadStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "lead_status_history")
public class LeadStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    @Enumerated(EnumType.STRING)
    private LeadStatus previousStatus;

    @Enumerated(EnumType.STRING)
    private LeadStatus status;

    @ManyToOne
    @JoinColumn(name = "changed_by", nullable = false)
    private User changedBy;

    private Instant updatedAt;

    @PrePersist
    public void prePersist(){
        this.updatedAt = Instant.now();
    }
}
