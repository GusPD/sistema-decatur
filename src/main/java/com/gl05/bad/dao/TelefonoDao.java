package com.gl05.bad.dao;

import com.gl05.bad.domain.Telefono;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface TelefonoDao extends DataTablesRepository<Telefono, Long> {
    Telefono findByTelefono (String telefono);
}