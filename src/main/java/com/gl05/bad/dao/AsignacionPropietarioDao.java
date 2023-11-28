package com.gl05.bad.dao;

import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.Propietario;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface AsignacionPropietarioDao extends DataTablesRepository<AsignacionPropietario, Long> {
    AsignacionPropietario findByPropietario (Propietario propietario);
}