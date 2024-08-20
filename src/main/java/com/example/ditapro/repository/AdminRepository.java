package com.example.ditapro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ditapro.model.Admin;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUser_Uuid(UUID userUuid);
}

