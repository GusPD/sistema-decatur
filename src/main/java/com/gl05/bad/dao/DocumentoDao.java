package com.gl05.bad.dao;

import com.gl05.bad.domain.Documento;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface DocumentoDao extends DataTablesRepository<Documento, Long> {
}