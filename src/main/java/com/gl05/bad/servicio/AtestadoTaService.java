package com.gl05.bad.servicio;

import com.gl05.bad.domain.AtestadoTa;
import java.util.List;

public interface AtestadoTaService {
  public List<AtestadoTa> listarAtestados();
  
  public void agregarAtestado(AtestadoTa atestado);
  
  public void actualizarAtestado(AtestadoTa atestado);
  
  public void eliminarAtestado(AtestadoTa atestado);
  
  public AtestadoTa encontrarAtestado(AtestadoTa atestado);  
}
