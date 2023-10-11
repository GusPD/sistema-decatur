package com.gl05.bad.servicio;

import com.gl05.bad.domain.InformacionMantenimiento;
import java.util.List;

public interface InformacionMantenimientoService {
    
    public List<InformacionMantenimiento> listaInformacionMantenimientos();
   
    public void agregar(InformacionMantenimiento informacionMantenimiento);
        
    public void eliminar(InformacionMantenimiento informacionMantenimiento);
    
    public InformacionMantenimiento encontrar(Long idInformacionMantenimiento);
    
    public void actualizar(InformacionMantenimiento informacionMantenimiento);
}
