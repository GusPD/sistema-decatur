package com.gl05.bad.dao;

import com.gl05.bad.domain.InformacionMantenimiento;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface InformacionMantenimientoDao extends DataTablesRepository<InformacionMantenimiento, Long> {
}