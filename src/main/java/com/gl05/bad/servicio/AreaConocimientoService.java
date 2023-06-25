package com.gl05.bad.servicio;

import com.gl05.bad.domain.AreaConocimiento;
import java.util.Collection;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface AreaConocimientoService {
    
  public DataTablesOutput<AreaConocimiento> listarAreaConocimientos(DataTablesInput input);
  
  public Collection<AreaConocimiento> listarAreaConocimientos();
  
  public void agregarAC(AreaConocimiento area);
  
  public void eliminarAC(AreaConocimiento area);
  
  public AreaConocimiento encontrarAC(Long area);
  
  public void actualizarAC(AreaConocimiento areaConocimiento);
  
}
