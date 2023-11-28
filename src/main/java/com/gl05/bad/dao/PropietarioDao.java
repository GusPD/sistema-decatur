package com.gl05.bad.dao;

import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Propietario;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

public interface PropietarioDao extends DataTablesRepository<Propietario, Long> {
    Propietario findByPersona (Persona persona);
    @Query("SELECT DISTINCT ap.propietario FROM AsignacionPropietario ap " +
       "JOIN ap.venta v " +
       "JOIN v.terreno t " +
       "WHERE t.proyecto.idProyecto = :idProyecto")
    List<Propietario> findPropietariosByProyecto(Long idProyecto);
}