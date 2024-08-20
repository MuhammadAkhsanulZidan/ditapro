package com.example.ditapro.service;

import com.example.ditapro.dto.BadgeDto;
import java.util.List;
import java.util.UUID;

public interface BadgeService {
    BadgeDto createBadge(BadgeDto badgeDto);
    BadgeDto getBadgeByUuid(UUID uuid);
    List<BadgeDto> getAllBadges();
    BadgeDto updateBadge(UUID uuid, BadgeDto badgeDto);
    void deleteBadge(UUID uuid);
}

