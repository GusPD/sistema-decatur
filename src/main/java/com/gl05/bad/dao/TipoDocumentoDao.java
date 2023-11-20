package com.gl05.bad.dao;

import com.gl05.bad.domain.TipoDocumento;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface TipoDocumentoDao extends DataTablesRepository<TipoDocumento, Long> {
}