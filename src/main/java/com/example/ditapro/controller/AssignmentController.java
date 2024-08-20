package com.example.ditapro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ditapro.dto.AssignmentDto;
import com.example.ditapro.model.Assignment;
import com.example.ditapro.service.AssignmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    //only admin can get all assignments
    @GetMapping
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public List<AssignmentDto> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    //only those who are enrolled in the course can get the assignment
    @GetMapping("/{assignmentUuid}")
    @PreAuthorize("@customPermissionEvaluator.isEnrolledInCourse(authentication.principal.uuid, @programServiceImpl.getProgramByUuid(@assignmentServiceImpl.getAssignmentByUuid(#assignmentUuid).programUuid).courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<AssignmentDto> getAssignmentByUuid(@PathVariable UUID assignmentUuid) {
        AssignmentDto assignment = assignmentService.getAssignmentByUuid(assignmentUuid);
        return assignment != null ? ResponseEntity.ok(assignment) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public AssignmentDto createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.createAssignment(assignment);
    }

    //only admin and course trainer can update the assignment
    @PutMapping("/{assignmentUuid}")
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication) or @customPermissionEvaluator.hasTrainerRoleForCourse(authentication.principal.uuid, @programServiceImpl.getProgramByUuid(@assignmentServiceImpl.getAssignmentByUuid(#assignmentUuid).programUuid).courseUuid)")
    public ResponseEntity<AssignmentDto> updateAssignment(@PathVariable UUID assignmentUuid, @RequestBody Assignment assignment) {
        AssignmentDto existingAssignment = assignmentService.getAssignmentByUuid(assignmentUuid);
        AssignmentDto updatedAssignment = assignmentService.updateAssignment(existingAssignment.getUuid(), assignment);
        return updatedAssignment != null ? ResponseEntity.ok(updatedAssignment) : ResponseEntity.notFound().build();
    }

    //only admin and course trainer can delete the assignment
    @DeleteMapping("/{assignmentUuid}")
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication) or @customPermissionEvaluator.hasTrainerRoleForCourse(authentication.principal.uuid, @programServiceImpl.getProgramByUuid(@assignmentServiceImpl.getAssignmentByUuid(#assignmentUuid).programUuid).courseUuid)")
    public ResponseEntity<Void> deleteAssignment(@PathVariable UUID assignmentUuid) {
        AssignmentDto existingAssignment = assignmentService.getAssignmentByUuid(assignmentUuid);
        assignmentService.deleteAssignment(existingAssignment.getUuid());
        return ResponseEntity.noContent().build();
    }
}
