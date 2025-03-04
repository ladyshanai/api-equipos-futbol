package com.gestion.equiposfutbol.service;


import com.gestion.equiposfutbol.dto.request.CreateEquipoDTORequest;
import com.gestion.equiposfutbol.dto.response.EquipoDTOResponse;

import java.util.List;

public interface GestionEquiposService {
    List<EquipoDTOResponse> findEquipos();
    EquipoDTOResponse findEquipoById(Long id);
    List<EquipoDTOResponse> findEquipoByNombre(String nombre);

    EquipoDTOResponse createEquipos(CreateEquipoDTORequest request);

    EquipoDTOResponse modifyEquipo(Long id, CreateEquipoDTORequest request);

    void deleteEquipo(Long id);
}



