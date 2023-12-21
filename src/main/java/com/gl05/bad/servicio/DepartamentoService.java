package com.gl05.bad.servicio;

import com.gl05.bad.domain.Departamento;

import java.util.List;

public interface DepartamentoService {
  
  public List<Departamento> listarDepartamentos();
  
  public void agregar(Departamento departamento);
  
  public void actualizar(Departamento departamento);
  
  public void eliminar(Departamento departamento);
  
  public Departamento encontrar(Departamento departamento);
}
