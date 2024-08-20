package com.example.ditapro.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.ditapro.model.Course;

public interface CourseService {
    List<Course> getAllCourses();
    Optional<Course> getCourseByUuid(UUID uuid);
    Course getCourseById(Long id);
    Course createCourse(Course course);
    Course updateCourse(Long id, Course course);
    void deleteCourse(Long id);
}

