package com.gl05.bad.servicio;

import com.gl05.bad.domain.AsignacionVisitante;
import com.gl05.bad.domain.Visitante;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface AsignacionVisitanteService {
    
    public List<AsignacionVisitante> listaAsignacionVisitantes();
    
    public DataTablesOutput<AsignacionVisitante> listarAsignacionVisitantes(DataTablesInput input, Long idVenta);
   
    public void agregar(AsignacionVisitante asignacion);
        
    public void eliminar(AsignacionVisitante asignacion);
    
    public AsignacionVisitante encontrar(Long idAsignacion);
    
    public AsignacionVisitante encontrarVisitante(Visitante visitante);
    
    public void actualizar(AsignacionVisitante asignacion);
}
