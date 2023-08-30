package com.gl05.bad.dao;

import com.gl05.bad.domain.Referencia;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface ReferenciaDao extends DataTablesRepository<Referencia, Long> {
}