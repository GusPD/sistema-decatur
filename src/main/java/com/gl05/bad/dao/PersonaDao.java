package com.gl05.bad.dao;

import com.gl05.bad.domain.Persona;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface PersonaDao extends DataTablesRepository<Persona, Long> {
    Persona findByDui(String dui);
}