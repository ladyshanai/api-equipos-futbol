package com.gestion.equiposfutbol.config;

import com.gestion.equiposfutbol.exception.AccesoNoAutorizadoException;
import com.gestion.equiposfutbol.exception.SolicitudInvalidaException;
import com.gestion.equiposfutbol.exception.EquipoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EquipoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarEquipoNoEncontrado(EquipoNoEncontradoException e) {
        return construirRespuesta(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(SolicitudInvalidaException.class)
    public ResponseEntity<Map<String, Object>> manejarEquipoDuplicado(SolicitudInvalidaException e) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AccesoNoAutorizadoException.class)
    public ResponseEntity<Map<String, Object>> manejarAccesoNoAutorizado(AccesoNoAutorizadoException e) {
        return construirRespuesta(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErroresGenerales(Exception e) {
        return construirRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private ResponseEntity<Map<String, Object>> construirRespuesta(HttpStatus status, String mensaje) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(status).body(respuesta);
    }
}
