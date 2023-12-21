package com.gl05.bad.servicio;

import com.gl05.bad.domain.Departamento;
import com.gl05.bad.domain.Municipio;

import java.util.List;

public interface MunicipioService {
  
  public List<Municipio> listarMunicipios();
  
  public void agregar(Municipio municipio);
  
  public void actualizar(Municipio municipio);
  
  public void eliminar(Municipio municipio);
  
  public Municipio encontrar(Municipio municipio);

  public List<Municipio> encontrarDepartamento(Departamento departamento);
}
