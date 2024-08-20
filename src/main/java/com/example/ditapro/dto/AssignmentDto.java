package com.example.ditapro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDto {

    private UUID uuid;
    private String title;
    private String description;
    private Date dueDate;
    private UUID programUuid;  
}

