package com.gl05.bad.servicio;

import com.gl05.bad.domain.Documento;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface DocumentoService {
  
  public List<Documento> listarDocumentos();
  
  public DataTablesOutput<Documento> listarDocumentos(DataTablesInput input, Integer idListDocumento);
  
  public Documento encontrar(Documento doc);
  
  public void agregar(Documento doc);
  
  public void eliminar(Documento doc);
  
}
