package com.example.ditapro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ditapro.model.InactiveUser;

public interface InactiveUserRepository extends JpaRepository<InactiveUser, Long>{
    Optional<InactiveUser>findByUserId(Long id);
    void deleteByUserId(Long id);
}
