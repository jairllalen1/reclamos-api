package com.jairllalen.reclamosapi.repository;

import com.jairllalen.reclamosapi.entity.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityTypeRepository extends JpaRepository<EntityType, Integer> {
}