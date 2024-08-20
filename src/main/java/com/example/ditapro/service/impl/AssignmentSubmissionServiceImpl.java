package com.example.ditapro.service.impl;

import java.util.List;
import java.util.UUID;

import com.example.ditapro.model.AssignmentSubmission;
import com.example.ditapro.repository.AssignmentSubmissionRepository;
import com.example.ditapro.service.AssignmentSubmissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {

    @Autowired
    private AssignmentSubmissionRepository submissionRepository;

    @Override
    public List<AssignmentSubmission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public AssignmentSubmission getSubmissionByUuid(UUID uuid) {
        return submissionRepository.findByUuid(uuid).orElse(null);
    }

    @Override
    public AssignmentSubmission getSubmissionById(Long id) {
        return submissionRepository.findById(id).orElse(null);
    }

    @Override
    public AssignmentSubmission createSubmission(AssignmentSubmission submission) {
        return submissionRepository.save(submission);
    }

    @Override
    public AssignmentSubmission updateSubmission(Long id, AssignmentSubmission submission) {
        AssignmentSubmission existingSubmission = submissionRepository.findById(id).orElse(null);
        if (existingSubmission != null) {
            existingSubmission.setSubmissionText(submission.getSubmissionText());
            existingSubmission.setSubmissionFile(submission.getSubmissionFile());
            return submissionRepository.save(existingSubmission);
        }
        return null;
    }

    @Override
    public void deleteSubmission(Long id) {
        submissionRepository.deleteById(id);
    }
}
