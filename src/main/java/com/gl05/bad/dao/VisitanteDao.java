package com.gl05.bad.dao;

import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Visitante;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface VisitanteDao extends DataTablesRepository<Visitante, Long> {
    Visitante findByPersona (Persona persona);
}