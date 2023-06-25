package com.gl05.bad.dao;

import com.gl05.bad.domain.Estudiante;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstudianteDao extends DataTablesRepository<Estudiante, Long>{
    @Query("SELECT e FROM Estudiante e WHERE NOT EXISTS " +
           "(SELECT ec FROM EstudianteCohorte ec WHERE ec.idEstudiante = e AND ec.idCohorte.idCohorte = :idCohorte)")
    List<Estudiante> findEstudiantesSinCohorte(@Param("idCohorte") Long idCohorte);
}
