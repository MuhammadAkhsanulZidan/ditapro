package com.example.ditapro.service;

import java.util.List;
import java.util.UUID;

import com.example.ditapro.dto.MaterialDto;
import com.example.ditapro.model.Material;

public interface MaterialService {
    List<MaterialDto> getAllMaterials();
    MaterialDto createMaterialForProgram(UUID programUuid, MaterialDto materialDto);
    List<MaterialDto> getMaterialsByProgramUuid(UUID programUuid);
    MaterialDto getMaterialByUuid(UUID uuid);
    MaterialDto getMaterialById(Long id);
    MaterialDto createMaterial(Material material);
    MaterialDto updateMaterial(UUID id, MaterialDto material);
    void deleteMaterial(UUID uuid);
}
