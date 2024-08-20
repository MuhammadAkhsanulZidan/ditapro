package com.example.ditapro.service.impl;

import com.example.ditapro.dto.CourseDto;
import com.example.ditapro.dto.CourseEnrollmentDto;
import com.example.ditapro.model.Course;
import com.example.ditapro.model.CourseEnrollment;
import com.example.ditapro.model.User;
import com.example.ditapro.repository.CourseEnrollmentRepository;
import com.example.ditapro.repository.CourseRepository;
import com.example.ditapro.repository.UserRepository;
import com.example.ditapro.service.CourseEnrollmentService;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseEnrollmentServiceImpl implements CourseEnrollmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CourseEnrollment createEnrollment(UUID userUuid, UUID courseUuid, String role) {
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new EntityNotFoundException("User not found with UUID: " + userUuid));
        Course course = courseRepository.findByUuid(courseUuid)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with UUID: " + courseUuid));

        CourseEnrollment enrollment = new CourseEnrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(new Date());
        enrollment.setRole(role); // Set the role
        enrollment.setCompleted(false);

        return courseEnrollmentRepository.save(enrollment);
    }

    @Override
    public List<CourseEnrollment> getAllCourseEnrollments() {
        return courseEnrollmentRepository.findAll();
    }

    @Override
    public List<CourseEnrollmentDto> findEnrollmentsByUserUuid(UUID userUuid) {
        return courseEnrollmentRepository.findByUser_Uuid(userUuid)
            .stream()
            .map(enrollment -> {
                CourseDto courseDto = modelMapper.map(enrollment.getCourse(), CourseDto.class);
                return new CourseEnrollmentDto(courseDto, enrollment.getRole());
            })
            .collect(Collectors.toList());
    }

    @Override
    public CourseEnrollment getCourseEnrollmentById(Long id) {
        return courseEnrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CourseEnrollment not found with id: " + id));
    }

    @Override
    public CourseEnrollment updateCourseEnrollment(Long id, CourseEnrollment courseEnrollment) {
        if (!courseEnrollmentRepository.existsById(id)) {
            throw new RuntimeException("CourseEnrollment not found with id: " + id);
        }
        courseEnrollment.setId(id);
        return courseEnrollmentRepository.save(courseEnrollment);
    }

    @Override
    public void deleteCourseEnrollment(Long id) {
        if (!courseEnrollmentRepository.existsById(id)) {
            throw new RuntimeException("CourseEnrollment not found with id: " + id);
        }
        courseEnrollmentRepository.deleteById(id);
    }
}
