package com.jairllalen.reclamosapi.service;

import com.jairllalen.reclamosapi.dto.EntityTypeCreateRequestDTO;
import com.jairllalen.reclamosapi.dto.EntityTypeResponseDTO;
import com.jairllalen.reclamosapi.dto.EntityTypeUpdateRequestDTO;
import com.jairllalen.reclamosapi.entity.EntityType;
import com.jairllalen.reclamosapi.exception.EntityTypeNotFoundException;
import com.jairllalen.reclamosapi.repository.EntityTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityTypeService {

    private final EntityTypeRepository entityTypeRepository;

    @Transactional(readOnly = true)
    public List<EntityTypeResponseDTO> findAll() {
        return entityTypeRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public EntityTypeResponseDTO findById(Integer id) {
        EntityType entityType = getEntityTypeOrThrow(id);

        return toResponseDTO(entityType);
    }

    @Transactional
    public EntityTypeResponseDTO create(EntityTypeCreateRequestDTO requestDTO) {
        EntityType entityType = EntityType.builder()
                .nameTypeEntity(requestDTO.getNameTypeEntity())
                .entitySize(requestDTO.getEntitySize())
                .sector(requestDTO.getSector())
                .idUserCreate(requestDTO.getIdUserCreate())
                .build();

        EntityType savedEntityType = entityTypeRepository.save(entityType);

        return toResponseDTO(savedEntityType);
    }

    @Transactional
    public EntityTypeResponseDTO update(Integer id, EntityTypeUpdateRequestDTO requestDTO) {
        EntityType entityType = getEntityTypeOrThrow(id);

        entityType.setNameTypeEntity(requestDTO.getNameTypeEntity());
        entityType.setEntitySize(requestDTO.getEntitySize());
        entityType.setSector(requestDTO.getSector());
        entityType.setIdUserUpdate(requestDTO.getIdUserUpdate());

        /*
         * La entidad ya está administrada por JPA dentro de la transacción.
         * No es necesario llamar a save().
         *
         * Se llama a flush() para forzar el UPDATE antes de construir el ResponseDTO.
         * Eso permite que @PreUpdate actualice dateUpdate antes de responder.
         */
        entityTypeRepository.flush();

        return toResponseDTO(entityType);
    }

    @Transactional
    public void delete(Integer id) {
        EntityType entityType = getEntityTypeOrThrow(id);

        entityTypeRepository.delete(entityType);
    }

    private EntityType getEntityTypeOrThrow(Integer id) {
        return entityTypeRepository.findById(id)
                .orElseThrow(() -> new EntityTypeNotFoundException(id));
    }

    private EntityTypeResponseDTO toResponseDTO(EntityType entityType) {
        return EntityTypeResponseDTO.builder()
                .idEntityType(entityType.getIdEntityType())
                .nameTypeEntity(entityType.getNameTypeEntity())
                .entitySize(entityType.getEntitySize())
                .sector(entityType.getSector())
                .idUserCreate(entityType.getIdUserCreate())
                .idUserUpdate(entityType.getIdUserUpdate())
                .dateCreate(entityType.getDateCreate())
                .dateUpdate(entityType.getDateUpdate())
                .build();
    }
}