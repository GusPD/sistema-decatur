package com.gl05.bad.dao;

import com.gl05.bad.domain.Pago;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface PagoDao extends DataTablesRepository<Pago, Long> {
}