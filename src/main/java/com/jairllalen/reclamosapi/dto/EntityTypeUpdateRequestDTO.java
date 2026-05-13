package com.jairllalen.reclamosapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityTypeUpdateRequestDTO {

    @NotBlank(message = "El nombre del tipo de entidad es obligatorio")
    @Size(max = 45, message = "El nombre del tipo de entidad no puede superar los 45 caracteres")
    private String nameTypeEntity;

    @NotBlank(message = "El tamaño de la entidad es obligatorio")
    @Size(max = 45, message = "El tamaño de la entidad no puede superar los 45 caracteres")
    private String entitySize;

    @NotBlank(message = "El sector es obligatorio")
    @Size(max = 45, message = "El sector no puede superar los 45 caracteres")
    private String sector;

    @NotNull(message = "El ID del usuario modificador es obligatorio")
    @Positive(message = "El ID del usuario modificador debe ser positivo")
    private Integer idUserUpdate;
}