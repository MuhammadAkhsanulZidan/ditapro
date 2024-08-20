package com.example.ditapro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ditapro.dto.AssignmentDto;
import com.example.ditapro.dto.BadgeDto;
import com.example.ditapro.dto.MaterialDto;
import com.example.ditapro.dto.ProgramDto;
import com.example.ditapro.model.Program;
import com.example.ditapro.service.AssignmentService;
import com.example.ditapro.service.BadgeService;
import com.example.ditapro.service.MaterialService;
import com.example.ditapro.service.ProgramService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/training-programs")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private BadgeService badgeService;

    @GetMapping
    @PreAuthorize("@customPermissionEvaluator.isAdmin(authentication)")
    public List<ProgramDto> getAllPrograms() {
        return programService.getAllPrograms();
    }

    @GetMapping("/{programUuid}")
    @PreAuthorize("@customPermissionEvaluator.isEnrolledInCourse(authentication.principal.uuid, @programServiceImpl.getProgramByUuid(#programUuid).courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<ProgramDto> getProgramByUuid(@PathVariable UUID programUuid) {
        ProgramDto program = programService.getProgramByUuid(programUuid);
        return program != null ? ResponseEntity.ok(program) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{programUuid}")
    @PreAuthorize("@customPermissionEvaluator.hasTrainerRoleForCourse(authentication.principal.uuid, @programServiceImpl.getProgramByUuid(#programUuid).courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable UUID programUuid, @RequestBody Program program) {
        ProgramDto existingProgram = programService.getProgramByUuid(programUuid);
        ProgramDto updatedProgram = programService.updateProgram(existingProgram.getUuid(), program);
        return updatedProgram != null ? ResponseEntity.ok(updatedProgram) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{programUuid}")
    @PreAuthorize("@customPermissionEvaluator.hasTrainerRoleForCourse(authentication.principal.uuid, @programServiceImpl.getProgramByUuid(#programUuid).courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<Void> deleteProgram(@PathVariable UUID uuid) {
        programService.deleteProgram(uuid);
        return ResponseEntity.noContent().build();
    }

    // materials
    @GetMapping("{programUuid}/materials")
    @PreAuthorize("@customPermissionEvaluator.isEnrolledInCourse(authentication.principal.uuid, @programServiceImpl.getProgramByUuid(#programUuid).courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<List<MaterialDto>> getMaterialsByProgram(@PathVariable UUID programUuid) {
        List<MaterialDto> materials = materialService.getMaterialsByProgramUuid(programUuid);
        return ResponseEntity.ok(materials);
    }

    @PostMapping("/{programUuid}/materials")
    @PreAuthorize("@customPermissionEvaluator.hasTrainerRoleForCourse(authentication.principal.uuid, @programServiceImpl.getProgramByUuid(#programUuid).courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<MaterialDto> createMaterialForProgram(@PathVariable UUID programUuid,
            @RequestBody MaterialDto materialDto) {
        MaterialDto material = materialService.createMaterialForProgram(programUuid, materialDto);
        return ResponseEntity.ok(material);
    }

    // assignments
    @PostMapping("/{programUuid}/assignments")
    @PreAuthorize("@customPermissionEvaluator.hasTrainerRoleForCourse(authentication.principal.uuid, @programServiceImpl.getProgramByUuid(#programUuid).courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<AssignmentDto> createAssignmentForProgram(@PathVariable UUID programUuid,
            @RequestBody AssignmentDto assignmentDto) {
        AssignmentDto createdAssignment = assignmentService.createAssignmentForProgram(programUuid, assignmentDto);
        return ResponseEntity.ok(createdAssignment);
    }

    @GetMapping("/{programUuid}/assignments")
    @PreAuthorize("@customPermissionEvaluator.isEnrolledInCourse(authentication.principal.uuid, @programServiceImpl.getProgramByUuid(#programUuid).courseUuid) or @customPermissionEvaluator.isAdmin(authentication)")
    public ResponseEntity<List<AssignmentDto>> getAssignmentsByProgram(@PathVariable UUID programUuid) {
        List<AssignmentDto> assignments = assignmentService.getAssignmentsByProgramUuid(programUuid);
        return ResponseEntity.ok(assignments);
    }

    // badges
    @PreAuthorize("@customPermissionEvaluator.canAssignBadge(authentication, #badgeDto.user.uuid, #programUuid)")
    @PostMapping("/{programUuid}/badges")
    public ResponseEntity<BadgeDto> createBadge(@PathVariable UUID programUuid, @RequestBody BadgeDto badgeDto) {
        badgeDto.setProgramUuid(programUuid);
        BadgeDto createdBadge = badgeService.createBadge(badgeDto);
        return new ResponseEntity<>(createdBadge, HttpStatus.CREATED);
    }
}
