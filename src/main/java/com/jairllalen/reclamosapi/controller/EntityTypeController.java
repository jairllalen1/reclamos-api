package com.jairllalen.reclamosapi.controller;

import com.jairllalen.reclamosapi.dto.EntityTypeCreateRequestDTO;
import com.jairllalen.reclamosapi.dto.EntityTypeResponseDTO;
import com.jairllalen.reclamosapi.dto.EntityTypeUpdateRequestDTO;
import com.jairllalen.reclamosapi.service.EntityTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/entity-types")
@RequiredArgsConstructor
public class EntityTypeController {

    private final EntityTypeService entityTypeService;

    @GetMapping
    public List<EntityTypeResponseDTO> getAllEntityTypes() {
        return entityTypeService.findAll();
    }

    @GetMapping("/{id}")
    public EntityTypeResponseDTO getEntityTypeById(@PathVariable Integer id) {
        return entityTypeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityTypeResponseDTO createEntityType(
            @Valid @RequestBody EntityTypeCreateRequestDTO requestDTO
    ) {
        return entityTypeService.create(requestDTO);
    }

    @PutMapping("/{id}")
    public EntityTypeResponseDTO updateEntityType(
            @PathVariable Integer id,
            @Valid @RequestBody EntityTypeUpdateRequestDTO requestDTO
    ) {
        return entityTypeService.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntityType(@PathVariable Integer id) {
        entityTypeService.delete(id);
    }
}