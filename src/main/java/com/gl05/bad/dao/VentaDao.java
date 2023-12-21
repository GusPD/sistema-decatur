package com.gl05.bad.dao;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Terreno;
import com.gl05.bad.domain.Venta;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

public interface VentaDao extends DataTablesRepository<Venta, Long> {
    @Query("SELECT v FROM Venta v WHERE v.idListDocumento = :idListDocumento")
    Venta findByIdListDocumento (Integer idListDocumento);
    List<Venta> findByEstadoAndTerrenoProyecto(String estado, Proyecto proyecto);
    List<Venta> findByTerreno(Terreno terreno);
    List<Venta> findByEstadoAndTerrenoProyectoAndIdVentaNotIn(String estado, Proyecto proyecto, List<Long> ventas);
    List<Venta> findByEstadoAndTerrenoProyectoAndIdVentaIn(String estado, Proyecto proyecto, List<Long> ventas);
}