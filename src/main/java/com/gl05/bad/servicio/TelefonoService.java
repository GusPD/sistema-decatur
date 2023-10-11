package com.gl05.bad.servicio;

import com.gl05.bad.domain.Telefono;
import java.util.List;

public interface TelefonoService {
  
  public List<Telefono> listarTelefonos();
  
  public void agregar(Telefono telefono);
  
  public void actualizar(Telefono telefono);
  
  public void eliminar(Telefono telefono);
  
  public Telefono encontrar(Telefono telefono);

  public Telefono encontrarTelefono(String telefono);
}
