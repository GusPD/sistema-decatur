package com.gl05.bad.servicio;

import com.gl05.bad.domain.Proyecto;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface ProyectoService {
    
    public List<Proyecto> listaProyectos();
    
    public DataTablesOutput<Proyecto> listarProyectos(DataTablesInput input);
   
    public void agregarProyecto(Proyecto proyecto);
        
    public void eliminarProyecto(Proyecto proyecto);
    
    public Proyecto encontrarProyecto(Long idProyecto);
    
    public void actualizarProyecto(Proyecto proyecto);
}
