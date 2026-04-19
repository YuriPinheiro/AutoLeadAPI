package com.autolead.repository;

import com.autolead.domain.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    Optional<Favorite> findByUserIdAndLeadId(UUID userId, UUID leadId);

    List<Favorite> findByUserId(UUID userId);

    void deleteByUserIdAndLeadId(UUID userId, UUID leadId);
}
