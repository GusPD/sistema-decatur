package com.gl05.bad.dao;

import com.gl05.bad.domain.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitacoraDao extends JpaRepository<Bitacora, Long>{
  
}