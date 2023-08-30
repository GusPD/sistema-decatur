package com.gl05.bad.dao;

import com.gl05.bad.domain.Correo;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface CorreoDao extends DataTablesRepository<Correo, Long> {
    Correo findByCorreo (String correo);
}