package com.gl05.bad.servicio;

import com.gl05.bad.domain.AspiranteProfesor;
import com.gl05.bad.domain.PostulacionCohorte;
import java.util.List;

public interface PostuladoCohorteService {
    
    public List<AspiranteProfesor> listarAspiranteProfesor(Long idCohorte);
    
    public void agregarP(PostulacionCohorte postulado);
    
    public List<PostulacionCohorte> listarPostulaciones(Long idAspiranteProfesor);
  
}
