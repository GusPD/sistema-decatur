package com.gl05.bad.dao;

import com.gl05.bad.domain.Departamento;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface DepartamentoDao extends DataTablesRepository<Departamento, Long> {
}