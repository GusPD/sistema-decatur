package com.gl05.bad.servicio;

import com.gl05.bad.domain.VistaVentasActiva;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface VistaVentasActivaService {
    
    public List<VistaVentasActiva> listaTerrenos();
    
    public DataTablesOutput<VistaVentasActiva> listarTerrenos(DataTablesInput input, Long idProyecto);
}
