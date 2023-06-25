package com.gl05.bad.dao;

import com.gl05.bad.domain.AspiranteProfesor;
import com.gl05.bad.domain.ProfesorCohorte;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfesorCohorteDao extends JpaRepository<ProfesorCohorte, Long>{
    
    @Query("SELECT pc FROM ProfesorCohorte pc WHERE idCohorte.idCohorte = :idCohorte")
    List<ProfesorCohorte> findProfesByCohorteId(@Param("idCohorte") Long idCohorte);
    
    @Query("SELECT pc.idAspiranteProfesor FROM ProfesorCohorte pc")
    List<AspiranteProfesor> findIdsProfesorCohorte();
    
}
