package com.example.ditapro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ditapro.dto.MaterialDto;
import com.example.ditapro.model.Material;
import com.example.ditapro.service.MaterialService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping
    public List<MaterialDto> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MaterialDto> getMaterialById(@PathVariable UUID uuid) {
        MaterialDto material = materialService.getMaterialByUuid(uuid);
        return material != null ? ResponseEntity.ok(material) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public MaterialDto createMaterial(@RequestBody Material material) {
        return materialService.createMaterial(material);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<MaterialDto> updateMaterial(@PathVariable UUID uuid, @RequestBody MaterialDto material) {
        MaterialDto existingMaterial = materialService.getMaterialByUuid(uuid);
        MaterialDto updatedMaterial = materialService.updateMaterial(existingMaterial.getUuid(), material);
        return updatedMaterial != null ? ResponseEntity.ok(updatedMaterial) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable UUID uuid) {
        materialService.deleteMaterial(uuid);
        return ResponseEntity.noContent().build();
    }
}
