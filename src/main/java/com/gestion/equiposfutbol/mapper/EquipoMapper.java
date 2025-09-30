package com.gestion.equiposfutbol.mapper;

import com.gestion.equiposfutbol.dto.request.CreateEquipoDTORequest;
import com.gestion.equiposfutbol.dto.response.EquipoDTOResponse;
import com.gestion.equiposfutbol.entity.EquipoEntity;

public class EquipoMapper {

    public static EquipoDTOResponse entityToResponse(EquipoEntity equipoEntity) {
        return new EquipoDTOResponse(
                equipoEntity.getId(),
                equipoEntity.getNombre(),
                equipoEntity.getLiga(),
                equipoEntity.getPais());
    }

    public static EquipoEntity dtoToEntity(CreateEquipoDTORequest request) {
        EquipoEntity equipoEntity = new EquipoEntity();
        equipoEntity.setNombre(request.nombre());
        equipoEntity.setLiga(request.liga());
        equipoEntity.setPais(request.pais());
        return equipoEntity;
    }
}
