package com.gl05.bad.dao;

import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.Propietario;
import com.gl05.bad.domain.Venta;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface AsignacionPropietarioDao extends DataTablesRepository<AsignacionPropietario, Long> {
    AsignacionPropietario findByPropietarioAndVenta (Propietario propietario, Venta venta);
    List<AsignacionPropietario> findByVenta (Venta venta);
    List<AsignacionPropietario> findByVentaAndEstado (Venta venta, String estado);
}