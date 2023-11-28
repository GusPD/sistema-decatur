package com.gl05.bad.servicio;

import com.gl05.bad.domain.Propietario;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Persona;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface PropietarioService {
    
    public List<Propietario> listaPropietarios();
    
    public DataTablesOutput<Propietario> listarPropietarios(DataTablesInput input);

    public List<Propietario> listaPropietariosProyecto(Proyecto proyecto);
   
    public DataTablesOutput<Propietario> listarPropietariosVenta(DataTablesInput input, Long idVenta);

    public void agregar(Propietario propietario);
        
    public void eliminar(Propietario propietario);
    
    public Propietario encontrar(Long idPropietario);
    
    public Propietario encontrarPersona(Persona persona);
    
    public void actualizar(Propietario propietario);
}
