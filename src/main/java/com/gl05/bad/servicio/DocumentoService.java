package com.gl05.bad.servicio;

import com.gl05.bad.domain.Documento;
import java.util.List;

public interface DocumentoService {
  
  public List<Documento> listarDocumentos();
  
  public Documento encontrar(Documento doc);
  
  public void agregar(Documento doc);
  
  public void eliminar(Documento doc);
  
}
