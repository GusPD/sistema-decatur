package com.gl05.bad.servicio;

import com.gl05.bad.domain.InformacionFinanciamiento;
import java.util.List;

public interface InformacionFinanciamientoService {
    
    public List<InformacionFinanciamiento> listaInformacionFinanciamientos();
   
    public void agregar(InformacionFinanciamiento informacionFinanciamiento);
        
    public void eliminar(InformacionFinanciamiento informacionFinanciamiento);
    
    public InformacionFinanciamiento encontrar(Long idInformacionFinanciamiento);
    
    public void actualizar(InformacionFinanciamiento informacionFinanciamiento);
}
