package com.gl05.bad.dao;

import com.gl05.bad.domain.Correo;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

public interface CorreoDao extends DataTablesRepository<Correo, Long> {
    Correo findByCorreo (String correo);
    @Query("SELECT c FROM Correo c " +
       "JOIN AsignacionPropietario ap ON c.propietario = ap.propietario " +
       "JOIN Venta v ON ap.venta.idVenta = v.idVenta " +
       "JOIN Terreno t ON v.terreno.idTerreno = t.idTerreno " +
       "WHERE v.estado = 'Activo' AND t.proyecto.idProyecto = :idProyecto")
   List<Correo> findByCorreoByVentaActiva(Long idProyecto);

   @Query("SELECT c FROM Correo c " +
       "JOIN AsignacionPropietario ap ON c.propietario = ap.propietario " +
       "JOIN Venta v ON ap.venta.idVenta = v.idVenta " +
       "JOIN Terreno t ON v.terreno.idTerreno = t.idTerreno " +
       "WHERE t.proyecto.idProyecto = :idProyecto")
   List<Correo> findByCorreoByProyecto(Long idProyecto);
}