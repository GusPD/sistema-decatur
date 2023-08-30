package com.gl05.bad.servicio;

import com.gl05.bad.domain.Persona;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface PersonaService {
    
    public List<Persona> listaPersonas();
    
    public DataTablesOutput<Persona> listarPersonas(DataTablesInput input);
   
    public void agregarPersona(Persona persona);
        
    public void eliminarPersona(Persona persona);
    
    public Persona encontrarPersona(Long idPersona);
    
    public Persona encontrarPersona(String dui);
    
    public void actualizarPersona(Persona persona);
}
