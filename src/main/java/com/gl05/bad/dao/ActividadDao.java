package com.gl05.bad.dao;

import com.gl05.bad.domain.Actividad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActividadDao extends JpaRepository<Actividad, Long>{

    @Query("SELECT a FROM Actividad a WHERE idListAe.idListAe = :listProgramaAsignaturaId")
    List<Actividad> findActividadesByLisId(@Param("listProgramaAsignaturaId") Long listProgramaAsignaturaId);

}
