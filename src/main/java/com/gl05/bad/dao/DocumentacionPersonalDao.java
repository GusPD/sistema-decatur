package com.gl05.bad.dao;

import com.gl05.bad.domain.ListadoDocumentacionPersonal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentacionPersonalDao extends JpaRepository<ListadoDocumentacionPersonal, Long>{
  
}

