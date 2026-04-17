package com.autolead.repository;

import com.autolead.domain.model.VehicleImage;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<VehicleImage, UUID> {

    List<VehicleImage> findByLeadId(UUID leadId);
}