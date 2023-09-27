package com.gl05.bad.servicio;

import com.gl05.bad.domain.VistaTerreno;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface VistaTerrenoService {
    
    public List<VistaTerreno> listaTerrenos();
    
    public DataTablesOutput<VistaTerreno> listarTerrenos(DataTablesInput input, Long idProyecto);
}
