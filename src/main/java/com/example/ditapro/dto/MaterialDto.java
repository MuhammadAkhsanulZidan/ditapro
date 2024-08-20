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
public class MaterialDto {

    private UUID uuid;
    private String name;
    private UUID programUuid;
    private String materialType;
    private String materialUrl;
    private Date uploadedDate;

}
