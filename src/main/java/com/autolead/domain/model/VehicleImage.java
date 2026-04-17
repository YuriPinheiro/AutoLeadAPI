package com.autolead.domain.model;

import com.autolead.domain.enums.ImageType;
import com.autolead.domain.enums.LeadStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vehicle_image")
public class VehicleImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    //Cloudinary
    private String url;
    private String publicId;

    @Enumerated(EnumType.STRING)
    private ImageType type;


}
