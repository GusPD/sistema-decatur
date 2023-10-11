package com.gl05.bad.dao;

import com.gl05.bad.domain.AsignacionVisitante;
import com.gl05.bad.domain.Visitante;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface AsignacionVisitanteDao extends DataTablesRepository<AsignacionVisitante, Long> {
    AsignacionVisitante findByVisitante (Visitante visitante);
}