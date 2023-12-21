package com.gl05.bad.servicio;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Terreno;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface TerrenoService {
    
    public List<Terreno> listaTerrenos();
    
    public DataTablesOutput<Terreno> listarTerrenos(DataTablesInput input, Long idProyecto);
   
    public void agregar(Terreno terreno);
        
    public void eliminar(Terreno terreno);
    
    public Terreno encontrar(Long idTerreno);

    public Terreno encontrarLote(String poligono, Long numero, String seccion, Proyecto proyecto);

    public Terreno encontrarMatricula(String matricula);
    
    public void actualizar(Terreno terreno);
}
