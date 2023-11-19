package com.gl05.bad.servicio;

import com.gl05.bad.domain.Persona;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface PersonaService {
    
    public List<Persona> listaPersonas();
    
    public DataTablesOutput<Persona> listarPersonas(DataTablesInput input);
   
    public void agregar(Persona persona);
        
    public void eliminar(Persona persona);
    
    public Persona encontrar(Long idPersona);
    
    public Persona encontrarNumero(String numero);
    
    public void actualizar(Persona persona);
}
