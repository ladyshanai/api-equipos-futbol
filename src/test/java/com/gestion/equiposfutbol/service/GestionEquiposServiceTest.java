package com.gestion.equiposfutbol.service;

import com.gestion.equiposfutbol.dto.request.CreateEquipoDTORequest;
import com.gestion.equiposfutbol.dto.response.EquipoDTOResponse;
import com.gestion.equiposfutbol.entity.EquipoEntity;
import com.gestion.equiposfutbol.exception.EquipoNoEncontradoException;
import com.gestion.equiposfutbol.exception.SolicitudInvalidaException;
import com.gestion.equiposfutbol.repository.GestionEquiposRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionEquiposServiceTest {

    @Mock
    private GestionEquiposRepository gestionEquiposRepository;

    @InjectMocks
    private GestionEquiposServiceImpl gestionEquiposService;

    private EquipoEntity equipoEntity;
    private CreateEquipoDTORequest equipoRequest;

    @BeforeEach
    void setUp() {
        equipoEntity = new EquipoEntity();
        equipoEntity.setId(1L);
        equipoEntity.setNombre("Equipo Test");
        equipoEntity.setLiga("Liga Test");
        equipoEntity.setPais("Pais Test");

        equipoRequest = new CreateEquipoDTORequest();
        equipoRequest.setNombre("Equipo Test");
        equipoRequest.setLiga("Liga Test");
        equipoRequest.setPais("Pais Test");
    }

    @Test
    void shouldReturnTeams() {
        when(gestionEquiposRepository.findAll()).thenReturn(List.of(equipoEntity));

        List<EquipoDTOResponse> result = gestionEquiposService.findEquipos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Equipo Test", result.get(0).getNombre());
        verify(gestionEquiposRepository, times(1)).findAll();
    }

    @Test
    void noTeamsException() {
        when(gestionEquiposRepository.findAll()).thenReturn(List.of());

        assertThrows(EquipoNoEncontradoException.class, () -> gestionEquiposService.findEquipos());
    }

    @Test
    void shouldReturnTeamsById() {
        when(gestionEquiposRepository.findById(1L)).thenReturn(Optional.of(equipoEntity));

        EquipoDTOResponse result = gestionEquiposService.findEquipoById(1L);

        assertNotNull(result);
        assertEquals("Equipo Test", result.getNombre());
        verify(gestionEquiposRepository, times(1)).findById(1L);
    }

    @Test
    void noTeamsByIdException() {
        when(gestionEquiposRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EquipoNoEncontradoException.class, () -> gestionEquiposService.findEquipoById(1L));
    }

    @Test
    void shouldReturnTeamsByName() {
        when(gestionEquiposRepository.findByNombreContaining("Equipo")).thenReturn(List.of(equipoEntity));

        List<EquipoDTOResponse> result = gestionEquiposService.findEquipoByNombre("Equipo");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Equipo Test", result.get(0).getNombre());
        verify(gestionEquiposRepository, times(1)).findByNombreContaining("Equipo");
    }

    @Test
    void noTeamsByNameException() {
        when(gestionEquiposRepository.findByNombreContaining("Equipo")).thenReturn(List.of());

        assertThrows(EquipoNoEncontradoException.class, () -> gestionEquiposService.findEquipoByNombre("Equipo"));
    }

    @Test
    void createEquipos_DeberiaCrearEquipo() {
        when(gestionEquiposRepository.findByNombre("Equipo Test")).thenReturn(null);
        when(gestionEquiposRepository.save(any(EquipoEntity.class))).thenReturn(equipoEntity);

        EquipoDTOResponse result = gestionEquiposService.createEquipos(equipoRequest);

        assertNotNull(result);
        assertEquals("Equipo Test", result.getNombre());
        verify(gestionEquiposRepository, times(1)).save(any(EquipoEntity.class));
    }

    @Test
    void createEquipos_DeberiaLanzarExcepcionSiElEquipoYaExiste() {
        when(gestionEquiposRepository.findByNombre("Equipo Test")).thenReturn(equipoEntity);

        assertThrows(SolicitudInvalidaException.class, () -> gestionEquiposService.createEquipos(equipoRequest));
    }

    @Test
    void modifyEquipo_DeberiaModificarEquipo() {
        when(gestionEquiposRepository.findById(1L)).thenReturn(Optional.of(equipoEntity));
        when(gestionEquiposRepository.save(any(EquipoEntity.class))).thenReturn(equipoEntity);

        EquipoDTOResponse result = gestionEquiposService.modifyEquipo(1L, equipoRequest);

        assertNotNull(result);
        assertEquals("Equipo Test", result.getNombre());
        verify(gestionEquiposRepository, times(1)).save(any(EquipoEntity.class));
    }

    @Test
    void modifyEquipo_DeberiaLanzarExcepcionSiNoExiste() {
        when(gestionEquiposRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EquipoNoEncontradoException.class, () -> gestionEquiposService.modifyEquipo(1L, equipoRequest));
    }

    @Test
    void deleteEquipo_DeberiaEliminarEquipo() {
        doNothing().when(gestionEquiposRepository).deleteById(1L);

        assertDoesNotThrow(() -> gestionEquiposService.deleteEquipo(1L));

        verify(gestionEquiposRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteEquipo_DeberiaLanzarExcepcionSiFalla() {
        doThrow(new RuntimeException("Error")).when(gestionEquiposRepository).deleteById(1L);

        assertThrows(EquipoNoEncontradoException.class, () -> gestionEquiposService.deleteEquipo(1L));
    }
}
