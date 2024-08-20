package com.example.ditapro.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ditapro.model.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Optional<Assignment>findByUuid(UUID uuid);

    List<Assignment> findByProgram_Uuid(UUID programUuid);

    void deleteByUuid(UUID id);
}
