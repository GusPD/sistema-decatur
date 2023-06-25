package com.gl05.bad.servicio;

import com.gl05.bad.domain.ExperienciaLaboral;
import java.util.List;

public interface ExperienciaLaboralService {
  
  public List<ExperienciaLaboral> listarExperienciasLaboral();
  
  public void agregarEL(ExperienciaLaboral experienciaLaboral);
  
  public void actualizarEL(ExperienciaLaboral experienciaLaboral);
  
  public void eliminarEL(ExperienciaLaboral experienciaLaboral);
  
  public ExperienciaLaboral encontrarEL(ExperienciaLaboral experienciaLaboral);

}
