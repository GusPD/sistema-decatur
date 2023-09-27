package com.gl05.bad.servicio;

import com.gl05.bad.domain.VistaPropietariosProyecto;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface VistaPropietariosProyectoService {
    
    public List<VistaPropietariosProyecto> listaPropietarios();
    
    public DataTablesOutput<VistaPropietariosProyecto> listarPropietarios(DataTablesInput input, Long idProyecto);
}
