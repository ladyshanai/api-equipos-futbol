package com.gestion.equiposfutbol.controller;

import com.gestion.equiposfutbol.dto.request.CreateEquipoDTORequest;
import com.gestion.equiposfutbol.dto.response.EquipoDTOResponse;
import com.gestion.equiposfutbol.service.GestionEquiposService;
import com.gestion.equiposfutbol.service.user.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionEquiposControllerTest {

    @Mock
    private GestionEquiposService gestionEquiposService;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private GestionEquiposController gestionEquiposController;

    private final String validAuthHeader = "Bearer mock-token";
    private EquipoDTOResponse equipoDTOResponse;
    private CreateEquipoDTORequest createEquipoDTORequest;

    @BeforeEach
    void setUp() {
        EquipoDTOResponse equipoDTOResponse = new EquipoDTOResponse(1L,"Equipo Test","","");

        CreateEquipoDTORequest equipo = new CreateEquipoDTORequest("Equipo Nuevo","","");
    }

    @Test
    void findTeamsOk() throws Exception {
        doNothing().when(usersService).validateToken(anyString());
        when(gestionEquiposService.findEquipos()).thenReturn(Collections.singletonList(equipoDTOResponse));

        ResponseEntity<List<EquipoDTOResponse>> response = gestionEquiposController.findEquipos(validAuthHeader);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertFalse(response.getBody().isEmpty());
        verify(usersService, times(1)).validateToken(anyString());
        verify(gestionEquiposService, times(1)).findEquipos();
    }

    @Test
    void findTeamsByIdOk() throws Exception {
        doNothing().when(usersService).validateToken(anyString());
        when(gestionEquiposService.findEquipoById(anyLong())).thenReturn(equipoDTOResponse);

        ResponseEntity<EquipoDTOResponse> response = gestionEquiposController.findEquipoById(1L, validAuthHeader);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1L, response.getBody().id());
        verify(usersService, times(1)).validateToken(anyString());
        verify(gestionEquiposService, times(1)).findEquipoById(anyLong());
    }

    @Test
    void createTeamsOk() throws Exception {
        doNothing().when(usersService).validateToken(anyString());
        when(gestionEquiposService.createEquipos(any(CreateEquipoDTORequest.class))).thenReturn(equipoDTOResponse);

        ResponseEntity<EquipoDTOResponse> response = gestionEquiposController.createEquipos(createEquipoDTORequest, validAuthHeader);

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        assertEquals("Equipo Test", response.getBody().nombre());
        verify(usersService, times(1)).validateToken(anyString());
        verify(gestionEquiposService, times(1)).createEquipos(any(CreateEquipoDTORequest.class));
    }

    @Test
    void deleteTeamsOk() throws Exception {
        doNothing().when(usersService).validateToken(anyString());
        doNothing().when(gestionEquiposService).deleteEquipo(anyLong());

        ResponseEntity<?> response = gestionEquiposController.deleteEquipo(1L, validAuthHeader);

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());
        verify(usersService, times(1)).validateToken(anyString());
        verify(gestionEquiposService, times(1)).deleteEquipo(anyLong());
    }
}
