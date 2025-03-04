package com.gestion.equiposfutbol.service;

import com.gestion.equiposfutbol.dto.request.CreateEquipoDTORequest;
import com.gestion.equiposfutbol.dto.response.EquipoDTOResponse;
import com.gestion.equiposfutbol.entity.EquipoEntity;
import com.gestion.equiposfutbol.exception.SolicitudInvalidaException;
import com.gestion.equiposfutbol.exception.EquipoNoEncontradoException;
import com.gestion.equiposfutbol.mapper.EquipoMapper;
import com.gestion.equiposfutbol.repository.GestionEquiposRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestionEquiposServiceImpl implements GestionEquiposService {
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
        EquipoEntity equipoEntities = gestionEquiposRepository.findByNombre(request.getNombre());

        if (equipoEntities != null) {
            throw new SolicitudInvalidaException("La solicitud es inválida");
        }

        EquipoEntity equipo = gestionEquiposRepository.save(EquipoMapper.dtoToEntity(request));
        return EquipoMapper.entityToResponse(equipo);
    }

    @Override
    public EquipoDTOResponse modifyEquipo(Long id, CreateEquipoDTORequest request) {
        EquipoEntity equipoEntities = gestionEquiposRepository.findById(id)
                .orElseThrow(() -> new EquipoNoEncontradoException("Equipo no encontrado"));
        equipoEntities.setNombre(request.getNombre());
        equipoEntities.setLiga(request.getLiga());
        equipoEntities.setPais(request.getPais());
        EquipoEntity equipoModificado = gestionEquiposRepository.save(equipoEntities);
        return EquipoMapper.entityToResponse(equipoModificado);
    }

    @Override
    public void deleteEquipo(Long id) {
        try {
            gestionEquiposRepository.deleteById(id);
        } catch (Exception e) {
            log.error("delete error: {}", e.getMessage());
            throw new EquipoNoEncontradoException("Equipo no encontrado");
        }
    }

}
