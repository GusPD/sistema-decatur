package com.gl05.bad.servicio;

import com.gl05.bad.domain.ConfiguracionCorreo;
import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface ConfiguracionCorreoService {
  
  public List<ConfiguracionCorreo> listarConfiguracionesCorreo();

  public DataTablesOutput<ConfiguracionCorreo> listarConfiguracionesCorreo(DataTablesInput input);

  public List<ConfiguracionCorreo> listarCorreosEnvio();
  
  public ConfiguracionCorreo obtenerConfiguracionCorreo();

  public ConfiguracionCorreo encontrar(Long idConfiguracion);

  public void agregar(ConfiguracionCorreo configuracionCorreo);

  public void eliminar(ConfiguracionCorreo configuracionCorreo);

  public void actualizar(ConfiguracionCorreo configuracionCorreo);
}
