package com.example.ditapro.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ditapro.dto.MaterialDto;
import com.example.ditapro.model.Material;
import com.example.ditapro.model.Program;
import com.example.ditapro.repository.MaterialRepository;
import com.example.ditapro.repository.ProgramRepository;
import com.example.ditapro.service.MaterialService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MaterialDto> getAllMaterials() {
        return materialRepository.findAll().stream()
                .map(material -> modelMapper.map(material, MaterialDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MaterialDto> getMaterialsByProgramUuid(UUID programUuid) {
        return materialRepository.findByProgram_Uuid(programUuid);
    }

    @Override
    public MaterialDto getMaterialByUuid(UUID uuid) {
        Material material = materialRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Material not found with UUID: " + uuid));
        return modelMapper.map(material, MaterialDto.class);
    }

    @Override
    public MaterialDto getMaterialById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found with ID: " + id));
        return modelMapper.map(material, MaterialDto.class);
    }

    @Override
    public MaterialDto createMaterialForProgram(UUID programUuid, MaterialDto materialDto) {
        Program program = programRepository.findByUuid(programUuid)
                .orElseThrow(() -> new EntityNotFoundException("Program not found with UUID: " + programUuid));

        Material material = modelMapper.map(materialDto, Material.class);
        material.setProgram(program);
        return modelMapper.map(materialRepository.save(material), MaterialDto.class);
    }

    @Override
    public MaterialDto createMaterial(Material material) {
        return modelMapper.map(materialRepository.save(material), MaterialDto.class);
    }

    @Override
    public MaterialDto updateMaterial(UUID uuid, MaterialDto materialDto) {
        Material existingMaterial = materialRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Material not found with UUID: " + uuid));
    
        existingMaterial.setName(materialDto.getName());
        existingMaterial.setType(materialDto.getMaterialType());
        existingMaterial.setUrl(materialDto.getMaterialUrl());
        existingMaterial.setUploadedDate(materialDto.getUploadedDate());
    
        return modelMapper.map(materialRepository.save(existingMaterial), MaterialDto.class);
    }
    

    @Override
    public void deleteMaterial(UUID id) {
        materialRepository.deleteByUuid(id);
    }
}
