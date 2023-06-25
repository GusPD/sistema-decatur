
package com.gl05.bad.dao;

import com.gl05.bad.domain.ProfesorAsignatura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfesorAsignaturaDao extends JpaRepository<ProfesorAsignatura, Long>{
    
    @Query("SELECT pa FROM ProfesorAsignatura pa WHERE pa.idProfesor.idProfesor = :idProfesor")
    List<ProfesorAsignatura> findProfesAsignaturaId(@Param("idProfesor") Long idProfesor);
}
