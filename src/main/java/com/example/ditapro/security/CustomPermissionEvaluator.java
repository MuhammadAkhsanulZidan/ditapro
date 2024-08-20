package com.example.ditapro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.ditapro.repository.AdminRepository;
import com.example.ditapro.repository.CourseEnrollmentRepository;
import com.example.ditapro.service.ProgramService;

import java.io.Serializable;
import java.util.UUID;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private ProgramService programService; // Spring should now find this bean

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
            Object permission) {
        return false;
    }

    public boolean isAdmin(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return false;
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID userUuid = userDetails.getUuid();
        return adminRepository.findByUser_Uuid(userUuid).isPresent();
    }

    public boolean isEnrolledInCourse(UUID userUuid, UUID courseUuid) {
        return courseEnrollmentRepository.existsByUser_UuidAndCourse_Uuid(userUuid, courseUuid);
    }

    public boolean hasTrainerRoleForCourse(UUID userUuid, UUID courseUuid) {
        return courseEnrollmentRepository.findByCourse_UuidAndUser_UuidAndRole(courseUuid, userUuid, "trainer")
                .isPresent();
    }

    public boolean canAssignBadge(Authentication authentication, UUID traineeUuid, UUID programUuid) {
        // Check if the user is an admin
        if (isAdmin(authentication)) {
            return true;
        }

        // Check if the user is a trainer for the program
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID trainerUuid = userDetails.getUuid();

        return hasTrainerRoleForCourse(trainerUuid, programUuid) &&
                courseEnrollmentRepository.existsByUser_UuidAndCourse_Uuid(traineeUuid, programUuid);
    }

}
