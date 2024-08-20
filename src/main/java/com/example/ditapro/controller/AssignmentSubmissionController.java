package com.example.ditapro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ditapro.model.AssignmentSubmission;
import com.example.ditapro.service.AssignmentSubmissionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/submissions")
public class AssignmentSubmissionController {

    @Autowired
    private AssignmentSubmissionService submissionService;

    @GetMapping
    public List<AssignmentSubmission> getAllSubmissions() {
        return submissionService.getAllSubmissions();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AssignmentSubmission> getSubmissionById(@PathVariable UUID uuid) {
        AssignmentSubmission submission = submissionService.getSubmissionByUuid(uuid);
        return submission != null ? ResponseEntity.ok(submission) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public AssignmentSubmission createSubmission(@RequestBody AssignmentSubmission submission) {
        return submissionService.createSubmission(submission);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<AssignmentSubmission> updateSubmission(@PathVariable UUID uuid, @RequestBody AssignmentSubmission submission) {
        AssignmentSubmission existingSubmission = submissionService.getSubmissionByUuid(uuid);
        AssignmentSubmission updatedSubmission = submissionService.updateSubmission(existingSubmission.getId(), submission);
        return updatedSubmission != null ? ResponseEntity.ok(updatedSubmission) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable UUID uuid) {
        AssignmentSubmission existingSubmission = submissionService.getSubmissionByUuid(uuid);
        submissionService.deleteSubmission(existingSubmission.getId());
        return ResponseEntity.noContent().build();
    }
}
