package com.gl05.bad.servicio;

import com.gl05.bad.domain.Terreno;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface TerrenoService {
    
    public List<Terreno> listaTerrenos();
    
    public DataTablesOutput<Terreno> listarTerrenos(DataTablesInput input, Long idProyecto);
   
    public void agregarTerreno(Terreno terreno);
        
    public void eliminarTerreno(Terreno terreno);
    
    public Terreno encontrarTerreno(Long idTerreno);
    
    public void actualizarTerreno(Terreno terreno);
}
