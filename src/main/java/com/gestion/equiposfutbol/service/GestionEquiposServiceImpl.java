package com.gestion.equiposfutbol.service;

import com.gestion.equiposfutbol.dto.request.CreateEquipoDTORequest;
import com.gestion.equiposfutbol.dto.response.EquipoDTOResponse;
import com.gestion.equiposfutbol.entity.EquipoEntity;
import com.gestion.equiposfutbol.exception.EquipoDuplicadoException;
import com.gestion.equiposfutbol.exception.EquipoNoEncontradoException;
import com.gestion.equiposfutbol.mapper.EquipoMapper;
import com.gestion.equiposfutbol.repository.GestionEquiposRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestionEquiposServiceImpl implements GestionEquiposService{
    private static final Logger log = LoggerFactory.getLogger(GestionEquiposServiceImpl.class);
    private final GestionEquiposRepository gestionEquiposRepository;

    public GestionEquiposServiceImpl(GestionEquiposRepository gestionEquiposRepository) {
        this.gestionEquiposRepository = gestionEquiposRepository;
    }

    @Override
    public List<EquipoDTOResponse> findEquipos() {
        List<EquipoEntity> equiposEntities = gestionEquiposRepository.findAll();
        if (equiposEntities.isEmpty())
            throw new EquipoNoEncontradoException("No se encontraron equipos");
        return equiposEntities.stream().map(EquipoMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public EquipoDTOResponse findEquipoById(Long id) {
        EquipoEntity equipoEntity = gestionEquiposRepository.findById(id)
                .orElseThrow(() -> new EquipoNoEncontradoException("Error al encontrar equipo con id: " + id));

        return EquipoMapper.entityToResponse(equipoEntity);
    }

    @Override
    public List<EquipoDTOResponse> findEquipoByNombre(String nombre) {

        List<EquipoEntity> equiposEntities = gestionEquiposRepository.findByNombreContaining(nombre);
        if (equiposEntities.isEmpty())
            throw new EquipoNoEncontradoException("No se encontraron equipos");
        return equiposEntities.stream().map(EquipoMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public EquipoDTOResponse createEquipos(CreateEquipoDTORequest request) {
        List<EquipoEntity> equipoEntities = gestionEquiposRepository.findByNombreContaining(request.getNombre());

        if (!equipoEntities.isEmpty()) {
            throw new EquipoDuplicadoException("Ya existe un equipo con ese nombre.");
        }

        EquipoEntity equipo = gestionEquiposRepository.save(EquipoMapper.dtoToEntity(request));
        return EquipoMapper.entityToResponse(equipo);
    }


}
