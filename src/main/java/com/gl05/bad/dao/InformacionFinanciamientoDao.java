package com.gl05.bad.dao;

import com.gl05.bad.domain.InformacionFinanciamiento;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

public interface InformacionFinanciamientoDao extends DataTablesRepository<InformacionFinanciamiento, Long> {
    @Query("SELECT f FROM InformacionFinanciamiento f WHERE f.venta = :venta ORDER BY f.fechaAplicacion DESC")
    List<InformacionFinanciamiento> findByVenta(Venta venta);
    List<InformacionFinanciamiento> findByVentaTerrenoProyecto(Proyecto proyecto);
}