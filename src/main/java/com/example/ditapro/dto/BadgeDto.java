package com.example.ditapro.dto;


import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDto {

    private Long id;
    private UUID uuid;
    private UserDto user;
    private String name;
    private Date issueAt;
    private UUID programUuid;
}
