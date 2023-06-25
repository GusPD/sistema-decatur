package com.gl05.bad.dao;

import com.gl05.bad.domain.Asignatura;
import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.EstudianteAsignatura;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstudianteAsignaturaDao extends DataTablesRepository<EstudianteAsignatura, Long>{
    
    @Query("SELECT CASE WHEN COUNT(ea) > 0 THEN true ELSE false END FROM EstudianteAsignatura ea WHERE ea.idEstudCo.idCohorte = :cohorteId AND ea.idAsignatura = :asignatura")
    boolean verificarEstudianteAsignatura(@Param("cohorteId") Cohorte cohorte, @Param("asignatura") Asignatura asignatura);

    @Query("SELECT ea FROM EstudianteAsignatura ea WHERE ea.idEstudCo.idCohorte.idCohorte = :cohorteId AND ea.idAsignatura.idAsignatura = :asignatura")
    List<EstudianteAsignatura> findByCohorteAndAsignatura(@Param("cohorteId") Long idCohorte, @Param("asignatura") Long idCohorteAsignatura);
}
