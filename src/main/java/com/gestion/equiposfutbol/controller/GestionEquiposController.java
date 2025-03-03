package com.gestion.equiposfutbol.controller;


import com.gestion.equiposfutbol.dto.request.CreateEquipoDTORequest;
import com.gestion.equiposfutbol.dto.response.EquipoDTOResponse;
import com.gestion.equiposfutbol.service.GestionEquiposService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/equipos")
public class GestionEquiposController {
    private final GestionEquiposService gestionEquiposService;

    public GestionEquiposController(GestionEquiposService gestionEquiposService) {
        this.gestionEquiposService = gestionEquiposService;
    }

    @GetMapping()
    public ResponseEntity<List<EquipoDTOResponse>> findEquipos() throws Exception {
        List<EquipoDTOResponse> response = gestionEquiposService.findEquipos();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EquipoDTOResponse> findEquipoById(@PathVariable Long id) throws Exception {
        EquipoDTOResponse response = gestionEquiposService.findEquipoById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/buscar")
    public ResponseEntity<List<EquipoDTOResponse>> findEquipoByNombre(@RequestParam("nombre") String nombre) throws Exception {
        List<EquipoDTOResponse> response = gestionEquiposService.findEquipoByNombre(nombre);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<EquipoDTOResponse> createEquipos(@RequestBody CreateEquipoDTORequest request) throws Exception {
        EquipoDTOResponse response = gestionEquiposService.createEquipos(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
