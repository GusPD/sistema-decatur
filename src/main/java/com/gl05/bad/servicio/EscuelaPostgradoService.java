package com.gl05.bad.servicio;

import com.gl05.bad.domain.EscuelaPostgrado;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface EscuelaPostgradoService {
   
  public List<EscuelaPostgrado> listarEscuelaPostgrado();
  
  public DataTablesOutput<EscuelaPostgrado> listarEscuelasDePosgrados(DataTablesInput input);
  
  public void agregarEscuela(EscuelaPostgrado escuela);
  
  public void eliminarEscuela(EscuelaPostgrado escuela);
  
  public EscuelaPostgrado encontrarEscuela(Long escuela);
  
  public void actualizarEscuela(EscuelaPostgrado escuela);
    
}

