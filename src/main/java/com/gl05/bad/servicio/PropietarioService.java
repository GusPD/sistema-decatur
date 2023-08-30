package com.gl05.bad.servicio;

import com.gl05.bad.domain.Propietario;
import com.gl05.bad.domain.Persona;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface PropietarioService {
    
    public List<Propietario> listaPropietarios();
    
    public DataTablesOutput<Persona> listarPropietarios(DataTablesInput input);
   
    public void agregarPropietario(Propietario propietario);
        
    public void eliminarPropietario(Propietario propietario);
    
    public Propietario encontrarPropietario(Long idPropietario);
    
    public Propietario encontrarPropietarioPersona(Persona persona);
    
    public void actualizarPropietario(Propietario propietario);
}
