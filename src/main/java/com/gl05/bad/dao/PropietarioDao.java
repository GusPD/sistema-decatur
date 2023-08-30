package com.gl05.bad.dao;

import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Propietario;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface PropietarioDao extends DataTablesRepository<Propietario, Long> {
    Propietario findByPersona (Persona persona);
}