package com.gl05.bad.dao;

import com.gl05.bad.domain.Rol;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface RolDao extends DataTablesRepository<Rol, Long>{
    
}
