package com.gl05.bad.servicio;

import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Visitante;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface VisitanteService {
    
    public List<Visitante> listaVisitantes();
    
    public DataTablesOutput<Visitante> listarVisitantes(DataTablesInput input);
    
    public DataTablesOutput<Visitante> listarTrabajadores(DataTablesInput input);
   
    public void agregar(Visitante visitante);
        
    public void eliminar(Visitante visitante);
    
    public Visitante encontrar(Long idVisitante);
    
    public Visitante encontrarPersona(Persona persona);
    
    public void actualizar(Visitante visitante);
}
