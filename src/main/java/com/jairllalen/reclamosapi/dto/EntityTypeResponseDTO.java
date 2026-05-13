package com.jairllalen.reclamosapi.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityTypeResponseDTO {

    private Integer idEntityType;

    private String nameTypeEntity;

    private String entitySize;

    private String sector;

    private Integer idUserCreate;

    private Integer idUserUpdate;

    private LocalDate dateCreate;

    private LocalDate dateUpdate;
}