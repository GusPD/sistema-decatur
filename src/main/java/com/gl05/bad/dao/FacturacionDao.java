package com.gl05.bad.dao;

import com.gl05.bad.domain.Facturacion;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface FacturacionDao extends DataTablesRepository<Facturacion, Long> {
}