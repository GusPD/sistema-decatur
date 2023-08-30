package com.gl05.bad.servicio;

import com.gl05.bad.domain.Telefono;
import java.util.List;

public interface TelefonoService {
  
  public List<Telefono> listarTelefonos();
  
  public void agregarT(Telefono telefono);
  
  public void actualizarT(Telefono telefono);
  
  public void eliminarT(Telefono telefono);
  
  public Telefono encontrarT(Telefono telefono);

  public Telefono encontrarTelefono(String telefono);
}
