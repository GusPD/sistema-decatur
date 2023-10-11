package com.gl05.bad.dao;

import com.gl05.bad.domain.InformacionFinanciamiento;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface InformacionFinanciamientoDao extends DataTablesRepository<InformacionFinanciamiento, Long> {
}