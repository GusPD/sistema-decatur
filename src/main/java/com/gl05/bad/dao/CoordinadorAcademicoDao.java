package com.gl05.bad.dao;

import com.gl05.bad.domain.CoordinadorAcademico;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface CoordinadorAcademicoDao extends DataTablesRepository<CoordinadorAcademico, Long>{
      
  @Procedure(name = "sp_insert_coordinador")
    void sp_insert_coordinador(
        @Param("p_cod") String cod,
        @Param("p_nombre") String nombre,
        @Param("p_apellido") String apellido,
        @Param("p_email") String email,
        @Param("p_password") String password
    );
    
    CoordinadorAcademico findByIdusuario(Integer idUsuario);
    
    @Query("SELECT c.correo FROM Correo c WHERE c.idListCorreo IN :idListCorreos")
    List<String> findCorreosByIdListCorreos(@Param("idListCorreos") List<Integer> idListCorreos);
}
