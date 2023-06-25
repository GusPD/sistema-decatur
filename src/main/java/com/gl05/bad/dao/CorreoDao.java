package com.gl05.bad.dao;

import com.gl05.bad.domain.Correo;
import org.springframework.data.jpa.repository.JpaRepository;
/*import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;*/

public interface CorreoDao extends JpaRepository<Correo, Long>{
}
