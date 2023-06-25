package com.gl05.bad.dao;

import com.gl05.bad.domain.Facultad;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface FacultadDao extends DataTablesRepository<Facultad, Long>{
    
}
