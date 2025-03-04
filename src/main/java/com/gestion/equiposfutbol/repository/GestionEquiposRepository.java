package com.gestion.equiposfutbol.repository;

import com.gestion.equiposfutbol.dto.request.CreateEquipoDTORequest;
import com.gestion.equiposfutbol.entity.EquipoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GestionEquiposRepository extends JpaRepository<EquipoEntity, Long> {

    @Query("SELECT e FROM EquipoEntity e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(e.liga) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(e.pais) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<EquipoEntity> findByNombreContaining(@Param("nombre") String nombre);

    EquipoEntity findByNombre(String nombre);

}
