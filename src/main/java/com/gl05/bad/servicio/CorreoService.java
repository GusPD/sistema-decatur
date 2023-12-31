package com.gl05.bad.servicio;

import com.gl05.bad.domain.Correo;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface CorreoService {
  
  public List<Correo> listarCorreos();
  
  public void agregar(Correo correo);
  
  public void actualizar(Correo correo);
  
  public void eliminar(Correo correo);
  
  public Correo encontrar(Correo correo);
  
  public Correo encontrarCorreo(String correo);
  
  public DataTablesOutput<Correo> listarCorreos(DataTablesInput input, Long idPropietario);
  
  public boolean enviarCorreo(String destinatario, String asunto, String mensaje);

}
