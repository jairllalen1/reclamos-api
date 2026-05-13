package com.jairllalen.reclamosapi.service;

import com.jairllalen.reclamosapi.dto.EntityTypeCreateRequestDTO;
import com.jairllalen.reclamosapi.dto.EntityTypeResponseDTO;
import com.jairllalen.reclamosapi.dto.EntityTypeUpdateRequestDTO;
import com.jairllalen.reclamosapi.entity.EntityType;
import com.jairllalen.reclamosapi.exception.EntityTypeNotFoundException;
import com.jairllalen.reclamosapi.repository.EntityTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntityTypeServiceTest {

    @Mock
    private EntityTypeRepository entityTypeRepository;

    @InjectMocks
    private EntityTypeService entityTypeService;

    @Test
    void findAll_shouldReturnEntityTypes() {
        EntityType entityType = EntityType.builder()
                .idEntityType(1)
                .nameTypeEntity("PyME")
                .entitySize("Pequeña")
                .sector("Tecnología")
                .idUserCreate(101)
                .idUserUpdate(101)
                .dateCreate(LocalDate.of(2024, 5, 15))
                .dateUpdate(LocalDate.of(2025, 11, 14))
                .build();

        when(entityTypeRepository.findAll()).thenReturn(List.of(entityType));

        List<EntityTypeResponseDTO> result = entityTypeService.findAll();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getIdEntityType());
        assertEquals("PyME", result.get(0).getNameTypeEntity());
        assertEquals("Pequeña", result.get(0).getEntitySize());
        assertEquals("Tecnología", result.get(0).getSector());

        verify(entityTypeRepository).findAll();
    }

    @Test
    void findById_whenEntityTypeExists_shouldReturnEntityType() {
        EntityType entityType = EntityType.builder()
                .idEntityType(1)
                .nameTypeEntity("ONG")
                .entitySize("Pequeña")
                .sector("Salud")
                .idUserCreate(103)
                .dateCreate(LocalDate.of(2024, 5, 20))
                .build();

        when(entityTypeRepository.findById(1)).thenReturn(Optional.of(entityType));

        EntityTypeResponseDTO result = entityTypeService.findById(1);

        assertEquals(1, result.getIdEntityType());
        assertEquals("ONG", result.getNameTypeEntity());
        assertEquals("Pequeña", result.getEntitySize());
        assertEquals("Salud", result.getSector());

        verify(entityTypeRepository).findById(1);
    }

    @Test
    void findById_whenEntityTypeDoesNotExist_shouldThrowException() {
        when(entityTypeRepository.findById(9999)).thenReturn(Optional.empty());

        EntityTypeNotFoundException exception = assertThrows(
                EntityTypeNotFoundException.class,
                () -> entityTypeService.findById(9999)
        );

        assertEquals("No existe un tipo de entidad con ID: 9999", exception.getMessage());

        verify(entityTypeRepository).findById(9999);
    }

    @Test
    void create_shouldSaveAndReturnEntityType() {
        EntityTypeCreateRequestDTO requestDTO = EntityTypeCreateRequestDTO.builder()
                .nameTypeEntity("Comercio")
                .entitySize("Mediana")
                .sector("Comercio")
                .idUserCreate(1)
                .build();

        EntityType savedEntityType = EntityType.builder()
                .idEntityType(20)
                .nameTypeEntity("Comercio")
                .entitySize("Mediana")
                .sector("Comercio")
                .idUserCreate(1)
                .dateCreate(LocalDate.now())
                .build();

        when(entityTypeRepository.save(any(EntityType.class))).thenReturn(savedEntityType);

        EntityTypeResponseDTO result = entityTypeService.create(requestDTO);

        assertEquals(20, result.getIdEntityType());
        assertEquals("Comercio", result.getNameTypeEntity());
        assertEquals("Mediana", result.getEntitySize());
        assertEquals("Comercio", result.getSector());
        assertEquals(1, result.getIdUserCreate());

        verify(entityTypeRepository).save(any(EntityType.class));
    }

    @Test
    void update_whenEntityTypeExists_shouldUpdateAndReturnEntityType() {
        EntityType existingEntityType = EntityType.builder()
                .idEntityType(2)
                .nameTypeEntity("ONG")
                .entitySize("Pequeña")
                .sector("Salud")
                .idUserCreate(103)
                .dateCreate(LocalDate.of(2024, 5, 20))
                .build();

        EntityTypeUpdateRequestDTO requestDTO = EntityTypeUpdateRequestDTO.builder()
                .nameTypeEntity("Institución educativa")
                .entitySize("Grande")
                .sector("Educación")
                .idUserUpdate(1)
                .build();

        when(entityTypeRepository.findById(2)).thenReturn(Optional.of(existingEntityType));

        EntityTypeResponseDTO result = entityTypeService.update(2, requestDTO);

        assertEquals(2, result.getIdEntityType());
        assertEquals("Institución educativa", result.getNameTypeEntity());
        assertEquals("Grande", result.getEntitySize());
        assertEquals("Educación", result.getSector());
        assertEquals(1, result.getIdUserUpdate());

        verify(entityTypeRepository).findById(2);
        verify(entityTypeRepository).flush();
        verify(entityTypeRepository, never()).save(any(EntityType.class));
    }

    @Test
    void update_whenEntityTypeDoesNotExist_shouldThrowException() {
        EntityTypeUpdateRequestDTO requestDTO = EntityTypeUpdateRequestDTO.builder()
                .nameTypeEntity("Institución educativa")
                .entitySize("Grande")
                .sector("Educación")
                .idUserUpdate(1)
                .build();

        when(entityTypeRepository.findById(9999)).thenReturn(Optional.empty());

        EntityTypeNotFoundException exception = assertThrows(
                EntityTypeNotFoundException.class,
                () -> entityTypeService.update(9999, requestDTO)
        );

        assertEquals("No existe un tipo de entidad con ID: 9999", exception.getMessage());

        verify(entityTypeRepository).findById(9999);
        verify(entityTypeRepository, never()).flush();
        verify(entityTypeRepository, never()).save(any(EntityType.class));
    }

    @Test
    void delete_whenEntityTypeExists_shouldDeleteEntityType() {
        EntityType existingEntityType = EntityType.builder()
                .idEntityType(2)
                .nameTypeEntity("ONG")
                .entitySize("Pequeña")
                .sector("Salud")
                .build();

        when(entityTypeRepository.findById(2)).thenReturn(Optional.of(existingEntityType));

        entityTypeService.delete(2);

        verify(entityTypeRepository).findById(2);
        verify(entityTypeRepository).delete(existingEntityType);
    }

    @Test
    void delete_whenEntityTypeDoesNotExist_shouldThrowException() {
        when(entityTypeRepository.findById(9999)).thenReturn(Optional.empty());

        EntityTypeNotFoundException exception = assertThrows(
                EntityTypeNotFoundException.class,
                () -> entityTypeService.delete(9999)
        );

        assertEquals("No existe un tipo de entidad con ID: 9999", exception.getMessage());

        verify(entityTypeRepository).findById(9999);
        verify(entityTypeRepository, never()).delete(any(EntityType.class));
    }
}