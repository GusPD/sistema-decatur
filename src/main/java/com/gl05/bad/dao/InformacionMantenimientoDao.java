package com.gl05.bad.dao;

import com.gl05.bad.domain.InformacionMantenimiento;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

public interface InformacionMantenimientoDao extends DataTablesRepository<InformacionMantenimiento, Long> {
    @Query("SELECT m FROM InformacionMantenimiento m WHERE m.venta = :venta ORDER BY m.fechaAplicacion DESC")
    List<InformacionMantenimiento> findByVenta(Venta venta);
    List<InformacionMantenimiento> findByFechaAplicacion(Date fechaAplicacion);
    List<InformacionMantenimiento> findByVentaTerrenoProyecto(Proyecto proyecto);
}