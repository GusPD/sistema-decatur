
package com.gl05.bad.dao;

import com.gl05.bad.domain.AspiranteProfesor;
import com.gl05.bad.domain.PostulacionCohorte;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PostulacionCohorteDao extends JpaRepository<PostulacionCohorte, Long>{
    
    //findPostulaciones
   @Query("SELECT pc FROM PostulacionCohorte pc WHERE pc.idAspiranteProfesor.idAspiranteProfesor = :idAspiranteProfesor")
   List<PostulacionCohorte> findPostulaciones(@Param("idAspiranteProfesor") Long idAspiranteProfesor);


}
