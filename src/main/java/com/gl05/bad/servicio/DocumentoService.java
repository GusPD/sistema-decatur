package com.gl05.bad.servicio;

import com.gl05.bad.domain.Asignatura;
import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.Documento;
import com.gl05.bad.domain.ListadoDocumentacionPersonal;
import java.util.List;

public interface DocumentoService {
  
  public List<Documento> listarDocumentos();
  
  public Documento encontrarDoc(Documento doc);
  
  public void agregarDocumento(Documento doc);
  
  public void eliminarDocumento(Documento doc);
  
  public List<Documento> listarDocumentoPorListado(ListadoDocumentacionPersonal ldp);
  
  public List<Documento> listarTipoFile(List<Documento> listDoc);
  
}
