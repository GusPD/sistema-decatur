package com.gl05.bad.dao;

import com.gl05.bad.domain.Bitacora;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitacoraDao extends DataTablesRepository<Bitacora, Integer>{
  
}