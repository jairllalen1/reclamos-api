package com.jairllalen.reclamosapi.exception;

public class EntityTypeNotFoundException extends RuntimeException {

    public EntityTypeNotFoundException(Integer id) {
        super("No existe un tipo de entidad con ID: " + id);
    }
}