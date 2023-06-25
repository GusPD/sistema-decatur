package com.gl05.bad.dao;

import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.Estudiante;
import com.gl05.bad.domain.EstudianteCohorte;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstudianteCohorteDao extends DataTablesRepository<EstudianteCohorte, Long>{
   
    EstudianteCohorte findByIdCohorteAndIdEstudiante(Cohorte idCohorte, Estudiante idEstudiante);
    
    List<EstudianteCohorte> findByIdCohorte(Cohorte idCohorte);
}
