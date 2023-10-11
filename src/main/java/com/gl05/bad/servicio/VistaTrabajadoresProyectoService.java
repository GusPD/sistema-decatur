package com.gl05.bad.servicio;

import com.gl05.bad.domain.VistaTrabajadoresProyecto;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface VistaTrabajadoresProyectoService {
    
    public List<VistaTrabajadoresProyecto> listaTrabajadores();
    
    public DataTablesOutput<VistaTrabajadoresProyecto> listarTrabajadores(DataTablesInput input, Long idProyecto);
}
