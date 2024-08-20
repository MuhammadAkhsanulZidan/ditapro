package com.example.ditapro.service;

import java.util.List;
import java.util.UUID;

import com.example.ditapro.dto.ProgramDto;
import com.example.ditapro.model.Program;

public interface ProgramService {
    List<ProgramDto> getAllPrograms();

    List<ProgramDto> getProgramsByCourseUuid(UUID courseUuid);

    ProgramDto getProgramByUuid(UUID id);

    ProgramDto getProgramById(Long id);

    ProgramDto createProgramForCourse(UUID courseUuid, Program program);

    ProgramDto updateProgram(UUID id, Program Program);

    void deleteProgram(UUID uuid);
}
