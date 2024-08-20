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
public class ProgramDto {
    private UUID uuid;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private UUID courseUuid;
    private String courseName;
}
