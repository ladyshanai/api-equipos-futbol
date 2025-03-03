package com.gestion.equiposfutbol.mapper;

import com.gestion.equiposfutbol.dto.request.CreateEquipoDTORequest;
import com.gestion.equiposfutbol.dto.response.EquipoDTOResponse;
import com.gestion.equiposfutbol.entity.EquipoEntity;

public class EquipoMapper {

    public static EquipoDTOResponse entityToResponse(EquipoEntity equipoEntity) {
        EquipoDTOResponse equipo = new EquipoDTOResponse();
        equipo.setId(equipoEntity.getId());
        equipo.setNombre(equipoEntity.getNombre());
        equipo.setLiga(equipoEntity.getLiga());
        equipo.setPais(equipoEntity.getPais());
        return equipo;
    }

    public static EquipoEntity dtoToEntity(CreateEquipoDTORequest request) {
        EquipoEntity equipoEntity = new EquipoEntity();
        equipoEntity.setNombre(request.getNombre());
        equipoEntity.setLiga(request.getLiga());
        equipoEntity.setPais(request.getPais());
        return equipoEntity;
    }
}
