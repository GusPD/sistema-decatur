package com.gl05.bad.dao;

import com.gl05.bad.domain.Venta;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface VentaDao extends DataTablesRepository<Venta, Long> {
}