package com.gl05.bad.servicio;

import com.gl05.bad.domain.InformacionFinanciamiento;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import java.util.List;

public interface InformacionFinanciamientoService {
    
    public List<InformacionFinanciamiento> listaInformacionFinanciamientos();
    
    public List<InformacionFinanciamiento> listaInformacionFinanciamientosProyecto(Proyecto proyecto);
   
    public void agregar(InformacionFinanciamiento informacionFinanciamiento);
        
    public void eliminar(InformacionFinanciamiento informacionFinanciamiento);
    
    public InformacionFinanciamiento encontrar(Long idInformacionFinanciamiento);
    
    public List<InformacionFinanciamiento> encontrarVenta(Venta venta);
    
    public void actualizar(InformacionFinanciamiento informacionFinanciamiento);
}
