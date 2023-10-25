package com.gl05.bad.dao;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface VentaDao extends DataTablesRepository<Venta, Long> {
    Venta findByIdListDocumento (Integer idDocumento);
    List<Venta> findByEstadoAndTerrenoProyectoAndIdVentaNotIn(String estado, Proyecto proyecto, List<Long> ventas);
    List<Venta> findByEstadoAndTerrenoProyectoAndIdVentaIn(String estado, Proyecto proyecto, List<Long> ventas);
}