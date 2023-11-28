package com.gl05.bad.dao;

import com.gl05.bad.domain.InformeMantenimiento;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface InformeMantenimientoDao extends DataTablesRepository<InformeMantenimiento, Long> {
    InformeMantenimiento findByVenta(Venta venta);
    List<InformeMantenimiento> findByVentaTerrenoProyecto(Proyecto proyecto);
    void deleteByVentaTerrenoProyecto(Proyecto proyecto);
}