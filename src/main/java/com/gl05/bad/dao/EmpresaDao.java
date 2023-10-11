package com.gl05.bad.dao;

import com.gl05.bad.domain.Empresa;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface EmpresaDao extends DataTablesRepository<Empresa, Long>{
}
