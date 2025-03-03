package com.gestion.equiposfutbol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EquipoDuplicadoException extends RuntimeException {
    public EquipoDuplicadoException(String message) {
        super(message);
    }

}
