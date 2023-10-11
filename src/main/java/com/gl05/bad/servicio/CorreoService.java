package com.gl05.bad.servicio;

import com.gl05.bad.domain.Correo;
import java.util.List;

public interface CorreoService {
  
  public List<Correo> listarCorreos();
  
  public void agregar(Correo correo);
  
  public void actualizar(Correo correo);
  
  public void eliminar(Correo correo);
  
  public Correo encontrar(Correo correo);
  
  public Correo encontrarCorreo(String correo);
  
  public boolean enviarCorreo(String destinatario, String asunto, String mensaje);

}
