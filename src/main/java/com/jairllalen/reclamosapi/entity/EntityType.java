package com.jairllalen.reclamosapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "entity_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entity_type")
    private Integer idEntityType;

    @Column(name = "name_type_entity", length = 45, nullable = false)
    private String nameTypeEntity;

    @Column(name = "entity_size", length = 45, nullable = false)
    private String entitySize;

    @Column(name = "sector", length = 45, nullable = false)
    private String sector;

    @Column(name = "id_user_create", nullable = false)
    private Integer idUserCreate;

    @Column(name = "id_user_update")
    private Integer idUserUpdate;

    @Column(name = "date_update")
    private LocalDate dateUpdate;

    @Column(name = "date_create")
    private LocalDate dateCreate;

    @PrePersist
    public void prePersist() {
        if (this.dateCreate == null) {
            this.dateCreate = LocalDate.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.dateUpdate = LocalDate.now();
    }
}