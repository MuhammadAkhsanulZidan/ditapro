package com.example.ditapro.service;

import java.util.List;
import java.util.UUID;

import com.example.ditapro.dto.AssignmentDto;
import com.example.ditapro.model.Assignment;

public interface AssignmentService {
    List<AssignmentDto> getAllAssignments();

    List<AssignmentDto> getAssignmentsByProgramUuid(UUID programUuid);

    AssignmentDto getAssignmentById(Long id);

    AssignmentDto getAssignmentByUuid(UUID uuid);

    AssignmentDto createAssignmentForProgram(UUID programUuid, AssignmentDto assignmentDto);

    AssignmentDto createAssignment(Assignment assignment);

    AssignmentDto updateAssignment(UUID uuid, Assignment assignment);

    void deleteAssignment(UUID uuid);
}
