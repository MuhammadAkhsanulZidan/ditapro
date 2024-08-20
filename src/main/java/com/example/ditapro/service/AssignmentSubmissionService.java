package com.example.ditapro.service;

import java.util.List;
import java.util.UUID;

import com.example.ditapro.model.AssignmentSubmission;

public interface AssignmentSubmissionService {
    List<AssignmentSubmission> getAllSubmissions();
    AssignmentSubmission getSubmissionByUuid(UUID id);
    AssignmentSubmission getSubmissionById(Long id);
    AssignmentSubmission createSubmission(AssignmentSubmission submission);
    AssignmentSubmission updateSubmission(Long id, AssignmentSubmission submission);
    void deleteSubmission(Long id);
}
