package com.gl05.bad.dao;

import com.gl05.bad.domain.ProgramaAsignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProgramaAsignaturaDao extends JpaRepository<ProgramaAsignatura, Long>{
    
    @Query("SELECT pa.idListAe.idListAe FROM ProgramaAsignatura pa WHERE idProgramAsignatura = :idProgramaAsignatura")
    Long findProgramaByAsignaturaId(@Param("idProgramaAsignatura") Long idProgramaAsignatura);
}
