package com.example.ditapro.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ditapro.dto.AssignmentDto;
import com.example.ditapro.model.Assignment;
import com.example.ditapro.model.Program;
import com.example.ditapro.repository.AssignmentRepository;
import com.example.ditapro.repository.ProgramRepository;
import com.example.ditapro.service.AssignmentService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AssignmentDto> getAllAssignments() {
        return assignmentRepository.findAll().stream().map(
            assignment->modelMapper.map(assignment, AssignmentDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDto> getAssignmentsByProgramUuid(UUID programUuid) {
        return assignmentRepository.findByProgram_Uuid(programUuid)
                .stream()
                .map(assignment -> modelMapper.map(assignment, AssignmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentDto getAssignmentById(Long id) {
        return modelMapper.map(assignmentRepository.findById(id).orElse(null), AssignmentDto.class);
    }

    @Override
    public AssignmentDto getAssignmentByUuid(UUID uuid) {
        return modelMapper.map(assignmentRepository.findByUuid(uuid).orElse(null), AssignmentDto.class);
    }

    @Override
    public AssignmentDto createAssignment(Assignment assignment) {
        return modelMapper.map(assignmentRepository.save(assignment), AssignmentDto.class);
    }

    @Override
    public AssignmentDto createAssignmentForProgram(UUID programUuid, AssignmentDto assignmentDto) {
        Program program = programRepository.findByUuid(programUuid)
                .orElseThrow(() -> new EntityNotFoundException("Program not found with UUID: " + programUuid));

        Assignment assignment = modelMapper.map(assignmentDto, Assignment.class);
        assignment.setProgram(program);

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return modelMapper.map(savedAssignment, AssignmentDto.class);
    }

    @Override
    public AssignmentDto updateAssignment(UUID uuid, Assignment assignment) {
        Assignment existingAssignment = assignmentRepository.findByUuid(uuid).orElse(null);
        if (existingAssignment != null) {
            existingAssignment.setTitle(assignment.getTitle());
            existingAssignment.setDescription(assignment.getDescription());
            existingAssignment.setDueDate(assignment.getDueDate());
            return modelMapper.map(assignmentRepository.save(existingAssignment), AssignmentDto.class);
        }
        return null;
    }

    @Override
    public void deleteAssignment(UUID id) {
        assignmentRepository.deleteByUuid(id);
    }
}
