package com.example.ditapro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ditapro.model.Program;
import java.util.UUID;


@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    Optional<Program> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
