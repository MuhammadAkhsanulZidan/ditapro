package com.example.ditapro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ditapro.dto.ProgramDto;
import com.example.ditapro.model.Course;
import com.example.ditapro.model.Program;
import com.example.ditapro.service.CourseService;
import com.example.ditapro.service.ProgramService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ProgramService programService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{courseUuid}")
    @PreAuthorize("@customPermissionEvaluator.isEnrolledInCourse(authentication.principal.uuid, #courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<Course> getCourseByUuid(@PathVariable UUID courseUuid) {
        Optional<Course> course = courseService.getCourseByUuid(courseUuid);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("@customPermissionEvaluator.hasTrainerRoleForCourse(authentication.principal.uuid, #courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<Course> updateCourse(@PathVariable UUID uuid, @RequestBody Course course) {
        Optional<Course> existingCourse = courseService.getCourseByUuid(uuid);
        if (existingCourse.isPresent()) {
            Course updatedCourse = courseService.updateCourse(existingCourse.get().getId(), course);
            return ResponseEntity.ok(updatedCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID uuid) {
        Optional<Course> course = courseService.getCourseByUuid(uuid);
        if (course.isPresent()) {
            courseService.deleteCourse(course.get().getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{courseUuid}/programs")
    @PreAuthorize("@customPermissionEvaluator.hasTrainerRoleForCourse(authentication.principal.uuid, #courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<ProgramDto> createProgramForCourse(
            @PathVariable UUID courseUuid,
            @RequestBody Program program) {

        ProgramDto programDto = programService.createProgramForCourse(courseUuid, program);
        return ResponseEntity.status(HttpStatus.CREATED).body(programDto);
    }

    @GetMapping("/{courseUuid}/programs")
    @PreAuthorize("@customPermissionEvaluator.isEnrolledInCourse(authentication.principal.uuid, #courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<List<ProgramDto>> getAllProgramsByCourse(@PathVariable UUID courseUuid) {
        List<ProgramDto> programs = programService.getProgramsByCourseUuid(courseUuid);
        return ResponseEntity.ok(programs);
    }

}
