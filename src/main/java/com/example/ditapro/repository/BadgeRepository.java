package com.example.ditapro.repository;

import com.example.ditapro.model.Badges;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface BadgeRepository extends JpaRepository<Badges, Long> {
    Optional<Badges> findByUuid(UUID uuid);
}
