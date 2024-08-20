package com.example.ditapro.controller;

import com.example.ditapro.dto.CourseDto;
import com.example.ditapro.dto.CourseEnrollmentDto;
import com.example.ditapro.model.CourseEnrollment;
import com.example.ditapro.service.CourseEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/course-enrollments")
public class CourseEnrollmentController {

    @Autowired
    private CourseEnrollmentService courseEnrollmentService;

    @PostMapping
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<CourseEnrollment> createCourseEnrollment(
            @RequestParam UUID user_uuid,
            @RequestParam UUID course_uuid,
            @RequestParam String role) { // Include role here
        CourseEnrollment enrollment = courseEnrollmentService.createEnrollment(user_uuid, course_uuid, role);
        return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public List<CourseEnrollment> getAllCourseEnrollments() {
        return courseEnrollmentService.getAllCourseEnrollments();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public CourseEnrollment getCourseEnrollmentById(@PathVariable Long id) {
        return courseEnrollmentService.getCourseEnrollmentById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public CourseEnrollment updateCourseEnrollment(
            @PathVariable Long id,
            @RequestBody CourseEnrollment courseEnrollment) {
        return courseEnrollmentService.updateCourseEnrollment(id, courseEnrollment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public void deleteCourseEnrollment(@PathVariable Long id) {
        courseEnrollmentService.deleteCourseEnrollment(id);
    }

    @GetMapping("/user/{userUuid}")
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public List<CourseEnrollmentDto> getEnrollmentsByUser(@PathVariable UUID userUuid) {
        return courseEnrollmentService.findEnrollmentsByUserUuid(userUuid);
    }
}
