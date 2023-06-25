package com.gl05.bad.servicio;

import com.gl05.bad.domain.Pais;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import java.util.Collection;

public interface PaisService {
  
  public List<Pais> listarPaises();
  
  public Collection<Pais> listarPais();
    
  public DataTablesOutput<Pais> listarPais(DataTablesInput input);
  
  public void agregarP(Pais pais);
  
  public void eliminarP(Pais pais);
  
  public Pais encontrarP(Pais pais);
  
  public void actualizarP(Pais pais);

}
