package com.example.ditapro.service;

import com.example.ditapro.dto.CourseEnrollmentDto;
import com.example.ditapro.model.CourseEnrollment;

import java.util.List;
import java.util.UUID;

public interface CourseEnrollmentService {

    CourseEnrollment createEnrollment(UUID userUuid, UUID courseUuid, String role);

    List<CourseEnrollment> getAllCourseEnrollments();

    CourseEnrollment getCourseEnrollmentById(Long id);

    CourseEnrollment updateCourseEnrollment(Long id, CourseEnrollment courseEnrollment);

    void deleteCourseEnrollment(Long id);

    public List<CourseEnrollmentDto> findEnrollmentsByUserUuid(UUID userUuid);
}
