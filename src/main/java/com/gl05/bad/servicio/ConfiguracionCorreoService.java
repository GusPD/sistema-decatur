package com.gl05.bad.servicio;

import com.gl05.bad.domain.ConfiguracionCorreo;
import java.util.List;

public interface ConfiguracionCorreoService {
  
  public List<ConfiguracionCorreo> listarConfiguracionesCorreo();
  
  public ConfiguracionCorreo obtenerConfiguracionCorreo();

}
