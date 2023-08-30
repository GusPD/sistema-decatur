package com.gl05.bad.servicio;

import com.gl05.bad.domain.Referencia;
import java.util.List;

public interface ReferenciaService {
  
  public List<Referencia> listarReferencias();
  
  public void agregar(Referencia referencia);
  
  public void actualizar(Referencia referencia);
  
  public void eliminar(Referencia referencia);
  
  public Referencia encontrar(Referencia referencia);

}
