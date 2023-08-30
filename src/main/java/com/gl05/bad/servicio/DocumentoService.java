package com.gl05.bad.servicio;

import com.gl05.bad.domain.Documento;
import java.util.List;

public interface DocumentoService {
  
  public List<Documento> listarDocumentos();
  
  public Documento encontrarDoc(Documento doc);
  
  public void agregarDocumento(Documento doc);
  
  public void eliminarDocumento(Documento doc);
  
}
