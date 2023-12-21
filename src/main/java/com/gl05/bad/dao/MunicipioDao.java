package com.gl05.bad.dao;

import com.gl05.bad.domain.Departamento;
import com.gl05.bad.domain.Municipio;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import java.util.List;


public interface MunicipioDao extends DataTablesRepository<Municipio, Long> {
    List<Municipio> findByDepartamento(Departamento departamento);
}