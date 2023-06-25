package com.gl05.bad.servicio;

import com.gl05.bad.domain.Estudiante;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.repository.query.Param;

public interface EstudianteService {
    
    public List<Estudiante> listaEstudiantes();
    
    public DataTablesOutput<Estudiante> listaEstudiantes(DataTablesInput input);
   
    public void agregarEstudiante(Estudiante estudiante);
    
    public Estudiante encontrarEstudiante(Long estudiante);
    
    public void eliminarEstudiante(Estudiante estudiante);
    
    public void actualizarEstudiante(Estudiante estudiante);
    
    public List<Estudiante> obtenerEstudiantesSinCohorte(@Param("idCohorte") Long idCohorte);
    
    public List<String> obtenerCorreosEstudiantes();   
}
