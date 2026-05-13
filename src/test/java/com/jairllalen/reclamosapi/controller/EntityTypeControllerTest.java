package com.jairllalen.reclamosapi.controller;

import com.jairllalen.reclamosapi.dto.EntityTypeResponseDTO;
import com.jairllalen.reclamosapi.exception.EntityTypeNotFoundException;
import com.jairllalen.reclamosapi.exception.GlobalExceptionHandler;
import com.jairllalen.reclamosapi.service.EntityTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EntityTypeController.class)
@Import(GlobalExceptionHandler.class)
class EntityTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EntityTypeService entityTypeService;

    @Test
    void getAllEntityTypes_shouldReturnStatus200() throws Exception {
        EntityTypeResponseDTO responseDTO = EntityTypeResponseDTO.builder()
                .idEntityType(1)
                .nameTypeEntity("PyME")
                .entitySize("Pequeña")
                .sector("Tecnología")
                .idUserCreate(101)
                .idUserUpdate(101)
                .dateCreate(LocalDate.of(2024, 5, 15))
                .dateUpdate(LocalDate.of(2025, 11, 14))
                .build();

        when(entityTypeService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/entity-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEntityType").value(1))
                .andExpect(jsonPath("$[0].nameTypeEntity").value("PyME"))
                .andExpect(jsonPath("$[0].entitySize").value("Pequeña"))
                .andExpect(jsonPath("$[0].sector").value("Tecnología"));

        verify(entityTypeService).findAll();
    }

    @Test
    void getEntityTypeById_whenExists_shouldReturnStatus200() throws Exception {
        EntityTypeResponseDTO responseDTO = EntityTypeResponseDTO.builder()
                .idEntityType(2)
                .nameTypeEntity("ONG")
                .entitySize("Pequeña")
                .sector("Salud")
                .idUserCreate(103)
                .dateCreate(LocalDate.of(2024, 5, 20))
                .build();

        when(entityTypeService.findById(2)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/entity-types/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEntityType").value(2))
                .andExpect(jsonPath("$.nameTypeEntity").value("ONG"))
                .andExpect(jsonPath("$.entitySize").value("Pequeña"))
                .andExpect(jsonPath("$.sector").value("Salud"));

        verify(entityTypeService).findById(2);
    }

    @Test
    void getEntityTypeById_whenDoesNotExist_shouldReturnStatus404() throws Exception {
        when(entityTypeService.findById(9999))
                .thenThrow(new EntityTypeNotFoundException(9999));

        mockMvc.perform(get("/api/entity-types/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No existe un tipo de entidad con ID: 9999"));

        verify(entityTypeService).findById(9999);
    }

    @Test
    void createEntityType_whenRequestIsValid_shouldReturnStatus201() throws Exception {
        String requestBody = """
                {
                  "nameTypeEntity": "Comercio",
                  "entitySize": "Mediana",
                  "sector": "Comercio",
                  "idUserCreate": 1
                }
                """;

        EntityTypeResponseDTO responseDTO = EntityTypeResponseDTO.builder()
                .idEntityType(20)
                .nameTypeEntity("Comercio")
                .entitySize("Mediana")
                .sector("Comercio")
                .idUserCreate(1)
                .dateCreate(LocalDate.of(2026, 5, 3))
                .build();

        when(entityTypeService.create(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/entity-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEntityType").value(20))
                .andExpect(jsonPath("$.nameTypeEntity").value("Comercio"))
                .andExpect(jsonPath("$.entitySize").value("Mediana"))
                .andExpect(jsonPath("$.sector").value("Comercio"))
                .andExpect(jsonPath("$.idUserCreate").value(1));

        verify(entityTypeService).create(any());
    }

    @Test
    void createEntityType_whenFieldsAreEmpty_shouldReturnStatus400() throws Exception {
        String requestBody = """
                {
                  "nameTypeEntity": "",
                  "entitySize": "",
                  "sector": "",
                  "idUserCreate": 1
                }
                """;

        mockMvc.perform(post("/api/entity-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Error de validación"))
                .andExpect(jsonPath("$.errors.nameTypeEntity").value("El nombre del tipo de entidad es obligatorio"))
                .andExpect(jsonPath("$.errors.entitySize").value("El tamaño de la entidad es obligatorio"))
                .andExpect(jsonPath("$.errors.sector").value("El sector es obligatorio"));

        verifyNoInteractions(entityTypeService);
    }

    @Test
    void createEntityType_whenIdUserCreateIsMissing_shouldReturnStatus400() throws Exception {
        String requestBody = """
                {
                  "nameTypeEntity": "Comercio",
                  "entitySize": "Mediana",
                  "sector": "Comercio"
                }
                """;

        mockMvc.perform(post("/api/entity-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.idUserCreate").value("El ID del usuario creador es obligatorio"));

        verifyNoInteractions(entityTypeService);
    }

    @Test
    void updateEntityType_whenRequestIsValid_shouldReturnStatus200() throws Exception {
        String requestBody = """
                {
                  "nameTypeEntity": "Institución educativa",
                  "entitySize": "Grande",
                  "sector": "Educación",
                  "idUserUpdate": 1
                }
                """;

        EntityTypeResponseDTO responseDTO = EntityTypeResponseDTO.builder()
                .idEntityType(2)
                .nameTypeEntity("Institución educativa")
                .entitySize("Grande")
                .sector("Educación")
                .idUserCreate(103)
                .idUserUpdate(1)
                .dateCreate(LocalDate.of(2024, 5, 20))
                .dateUpdate(LocalDate.of(2026, 5, 3))
                .build();

        when(entityTypeService.update(eq(2), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/entity-types/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEntityType").value(2))
                .andExpect(jsonPath("$.nameTypeEntity").value("Institución educativa"))
                .andExpect(jsonPath("$.entitySize").value("Grande"))
                .andExpect(jsonPath("$.sector").value("Educación"))
                .andExpect(jsonPath("$.idUserUpdate").value(1));

        verify(entityTypeService).update(eq(2), any());
    }

    @Test
    void updateEntityType_whenDoesNotExist_shouldReturnStatus404() throws Exception {
        String requestBody = """
                {
                  "nameTypeEntity": "Institución educativa",
                  "entitySize": "Grande",
                  "sector": "Educación",
                  "idUserUpdate": 1
                }
                """;

        when(entityTypeService.update(eq(9999), any()))
                .thenThrow(new EntityTypeNotFoundException(9999));

        mockMvc.perform(put("/api/entity-types/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No existe un tipo de entidad con ID: 9999"));

        verify(entityTypeService).update(eq(9999), any());
    }

    @Test
    void updateEntityType_whenIdUserUpdateIsMissing_shouldReturnStatus400() throws Exception {
        String requestBody = """
                {
                  "nameTypeEntity": "Comercio actualizado",
                  "entitySize": "Grande",
                  "sector": "Tecnología"
                }
                """;

        mockMvc.perform(put("/api/entity-types/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.idUserUpdate").value("El ID del usuario modificador es obligatorio"));

        verifyNoInteractions(entityTypeService);
    }

    @Test
    void deleteEntityType_whenExists_shouldReturnStatus204() throws Exception {
        doNothing().when(entityTypeService).delete(2);

        mockMvc.perform(delete("/api/entity-types/2"))
                .andExpect(status().isNoContent());

        verify(entityTypeService).delete(2);
    }

    @Test
    void deleteEntityType_whenDoesNotExist_shouldReturnStatus404() throws Exception {
        doThrow(new EntityTypeNotFoundException(9999))
                .when(entityTypeService)
                .delete(9999);

        mockMvc.perform(delete("/api/entity-types/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No existe un tipo de entidad con ID: 9999"));

        verify(entityTypeService).delete(9999);
    }
}