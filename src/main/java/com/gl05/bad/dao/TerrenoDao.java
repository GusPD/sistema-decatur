package com.gl05.bad.dao;

import com.gl05.bad.domain.Terreno;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface TerrenoDao extends DataTablesRepository<Terreno, Long> {
}