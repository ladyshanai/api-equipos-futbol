package com.gestion.equiposfutbol.controller;


import com.gestion.equiposfutbol.dto.request.CreateEquipoDTORequest;
import com.gestion.equiposfutbol.dto.response.EquipoDTOResponse;
import com.gestion.equiposfutbol.exception.AccesoNoAutorizadoException;
import com.gestion.equiposfutbol.service.GestionEquiposService;
import com.gestion.equiposfutbol.service.user.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/equipos")
public class GestionEquiposController {
    private final GestionEquiposService gestionEquiposService;
    private final UsersService usersService;

    public GestionEquiposController(GestionEquiposService gestionEquiposService, UsersService usersService) {
        this.gestionEquiposService = gestionEquiposService;
        this.usersService = usersService;
    }

    @GetMapping()
    public ResponseEntity<List<EquipoDTOResponse>> findEquipos(@RequestHeader("Authorization") String authHeader) throws Exception {
        validateToken(authHeader);
        List<EquipoDTOResponse> response = gestionEquiposService.findEquipos();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EquipoDTOResponse> findEquipoById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) throws Exception {
        validateToken(authHeader);
        EquipoDTOResponse response = gestionEquiposService.findEquipoById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/buscar")
    public ResponseEntity<List<EquipoDTOResponse>> findEquipoByNombre(@RequestParam("nombre") String nombre, @RequestHeader("Authorization") String authHeader) throws Exception {
        validateToken(authHeader);
        List<EquipoDTOResponse> response = gestionEquiposService.findEquipoByNombre(nombre);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<EquipoDTOResponse> createEquipos(@RequestBody CreateEquipoDTORequest request, @RequestHeader("Authorization") String authHeader) throws Exception {
        validateToken(authHeader);
        EquipoDTOResponse response = gestionEquiposService.createEquipos(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EquipoDTOResponse> modifyEquipo(@PathVariable Long id, @RequestBody CreateEquipoDTORequest request, @RequestHeader("Authorization") String authHeader) throws Exception {
        validateToken(authHeader);
        EquipoDTOResponse response = gestionEquiposService.modifyEquipo(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteEquipo(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) throws Exception {
        validateToken(authHeader);
        gestionEquiposService.deleteEquipo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void validateToken(String authHeader) throws Exception {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            usersService.validateToken(token);
        } else {
            throw new AccesoNoAutorizadoException("Token invalido");
        }
    }

}
