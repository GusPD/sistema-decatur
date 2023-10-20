package com.gl05.bad.dao;

import com.gl05.bad.domain.Facturacion;
import com.gl05.bad.domain.Venta;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface FacturacionDao extends DataTablesRepository<Facturacion, Long> {
    Facturacion findByVenta(Venta venta);
}