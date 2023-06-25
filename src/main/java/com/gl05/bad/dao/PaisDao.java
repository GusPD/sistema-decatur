package com.gl05.bad.dao;

import com.gl05.bad.domain.Pais;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface PaisDao extends DataTablesRepository<Pais, Long>{
}
