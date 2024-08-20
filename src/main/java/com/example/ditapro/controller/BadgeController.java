package com.example.ditapro.controller;

import com.example.ditapro.dto.BadgeDto;
import com.example.ditapro.service.BadgeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/badges")
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BadgeDto> getBadgeByUuid(@PathVariable UUID uuid) {
        BadgeDto badge = badgeService.getBadgeByUuid(uuid);
        return new ResponseEntity<>(badge, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BadgeDto>> getAllBadges() {
        List<BadgeDto> badges = badgeService.getAllBadges();
        return new ResponseEntity<>(badges, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<BadgeDto> updateBadge(@PathVariable UUID uuid, @RequestBody BadgeDto badgeDto) {
        BadgeDto updatedBadge = badgeService.updateBadge(uuid, badgeDto);
        return new ResponseEntity<>(updatedBadge, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteBadge(@PathVariable UUID uuid) {
        badgeService.deleteBadge(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
