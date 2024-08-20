package com.example.ditapro.service.impl;

import com.example.ditapro.dto.BadgeDto;
import com.example.ditapro.model.Badges;
import com.example.ditapro.repository.BadgeRepository;
import com.example.ditapro.service.BadgeService;

import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;
    private final ModelMapper modelMapper;

    public BadgeServiceImpl(BadgeRepository badgeRepository, ModelMapper modelMapper) {
        this.badgeRepository = badgeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BadgeDto createBadge(BadgeDto badgeDto) {
        Badges badge = modelMapper.map(badgeDto, Badges.class);
        Badges savedBadge = badgeRepository.save(badge);
        return modelMapper.map(savedBadge, BadgeDto.class);
    }

    @Override
    public BadgeDto getBadgeByUuid(UUID uuid) {
        Badges badge = badgeRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Badge not found"));
        return modelMapper.map(badge, BadgeDto.class);
    }

    @Override
    public List<BadgeDto> getAllBadges() {
        return badgeRepository.findAll()
                .stream()
                .map(badge -> modelMapper.map(badge, BadgeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BadgeDto updateBadge(UUID uuid, BadgeDto badgeDto) {
        Badges badge = badgeRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Badge not found"));
        badge.setName(badgeDto.getName());
        badge.setIssueAt(badgeDto.getIssueAt());
        Badges updatedBadge = badgeRepository.save(badge);
        return modelMapper.map(updatedBadge, BadgeDto.class);
    }

    @Override
    public void deleteBadge(UUID uuid) {
        Badges badge = badgeRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Badge not found"));
        badgeRepository.delete(badge);
    }
}
