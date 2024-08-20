package com.example.ditapro.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ditapro.dto.ProgramDto;
import com.example.ditapro.model.Course;
import com.example.ditapro.model.Program;
import com.example.ditapro.repository.CourseRepository;
import com.example.ditapro.repository.ProgramRepository;
import com.example.ditapro.service.ProgramService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProgramDto> getAllPrograms() {
        return programRepository.findAll().stream()
                .map(program -> modelMapper.map(program, ProgramDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgramDto> getProgramsByCourseUuid(UUID courseUuid) {
        Course course = courseRepository.findByUuid(courseUuid)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with UUID: " + courseUuid));

        return course.getPrograms().stream()
                .map(program -> modelMapper.map(program, ProgramDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProgramDto getProgramById(Long id) {
        return modelMapper.map(
                programRepository.findById(id).orElse(null), ProgramDto.class);
    }

    @Override
    public ProgramDto getProgramByUuid(UUID uuid) {
        return modelMapper.map(programRepository.findByUuid(uuid).orElse(null), ProgramDto.class);
    }

    @Override
    public ProgramDto createProgramForCourse(UUID courseUuid, Program program) {
        Course course = courseRepository.findByUuid(courseUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        program.setCourse(course);
        Program savedProgram = programRepository.save(program);

        return modelMapper.map(savedProgram, ProgramDto.class);
    }

    @Override
    public ProgramDto updateProgram(UUID uuid, Program Program) {
        Program existingProgram = programRepository.findByUuid(uuid).orElse(null);
        if (existingProgram != null) {
            existingProgram.setName(Program.getName());
            existingProgram.setDescription(Program.getDescription());
            existingProgram.setStartDate(Program.getStartDate());
            existingProgram.setEndDate(Program.getEndDate());
            return modelMapper.map(
                programRepository.save(existingProgram),
                ProgramDto.class);
        }
        return null;
    }

    @Override
    public void deleteProgram(UUID uuid) {
        programRepository.deleteByUuid(uuid);
    }
}
