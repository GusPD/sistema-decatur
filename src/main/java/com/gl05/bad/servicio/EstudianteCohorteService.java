package com.gl05.bad.servicio;

import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.Estudiante;
import com.gl05.bad.domain.EstudianteCohorte;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface EstudianteCohorteService {
    
    void guardarEstudianteCohorte(EstudianteCohorte estudianteCohorte);
    
    public DataTablesOutput<EstudianteCohorte>econtrarPorCohorte(DataTablesInput input,Cohorte idCohorte);
    
    public void eliminar(EstudianteCohorte estudianteCohorte);
    
    public EstudianteCohorte encontrarEstudianteCohorte(Cohorte idCohorte, Estudiante idEstudiante);
    
    public List<EstudianteCohorte> encontrarEstudianteIdCohorte(Cohorte idCohorte);
}
