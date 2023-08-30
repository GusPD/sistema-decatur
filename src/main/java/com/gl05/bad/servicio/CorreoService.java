package com.gl05.bad.servicio;

import com.gl05.bad.domain.Correo;
import java.util.List;

public interface CorreoService {
  
  public List<Correo> listarCorreos();
  
  public void agregarC(Correo correo);
  
  public void actualizarC(Correo correo);
  
  public void eliminarC(Correo correo);
  
  public Correo encontrarC(Correo correo);
  
  public Correo encontrarCorreo(String correo);

}
