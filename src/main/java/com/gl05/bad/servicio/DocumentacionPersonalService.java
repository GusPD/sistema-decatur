package com.gl05.bad.servicio;

import com.gl05.bad.domain.ListadoDocumentacionPersonal;
import java.util.List;

public interface DocumentacionPersonalService {
  
  public List<ListadoDocumentacionPersonal> listarDocumentosPersonales();
  
  public ListadoDocumentacionPersonal encontrarDocPersonal(ListadoDocumentacionPersonal doc);
  
}
