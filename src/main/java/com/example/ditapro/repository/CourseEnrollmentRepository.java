package com.example.ditapro.repository;

import com.example.ditapro.model.Admin;
import com.example.ditapro.model.CourseEnrollment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    List<CourseEnrollment> findByUser_Uuid(UUID userUuid);

    Optional<CourseEnrollment> findByCourse_UuidAndUser_UuidAndRole(UUID courseUuid, UUID userUuid, String role);

    boolean existsByUser_UuidAndCourse_Uuid(UUID userUuid, UUID courseUuid);

    Optional<Admin> findByUser_UuidAndRole(UUID userUuid, String string);
}
