package com.example.ditapro.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ditapro.dto.MaterialDto;
import com.example.ditapro.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByUuid(UUID uuid);
    List<MaterialDto> findByProgram_Uuid(UUID programUuid);
    void deleteByUuid(UUID id);
}
