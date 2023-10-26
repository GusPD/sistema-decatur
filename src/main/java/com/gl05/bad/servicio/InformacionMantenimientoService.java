package com.gl05.bad.servicio;

import com.gl05.bad.domain.InformacionMantenimiento;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import java.util.List;

public interface InformacionMantenimientoService {
    
    public List<InformacionMantenimiento> listaInformacionMantenimientos();
    
    public List<InformacionMantenimiento> listaInformacionMantenimientosProyecto(Proyecto proyecto);
   
    public void agregar(InformacionMantenimiento informacionMantenimiento);
        
    public void eliminar(InformacionMantenimiento informacionMantenimiento);
    
    public InformacionMantenimiento encontrar(Long idInformacionMantenimiento);
    
    public List<InformacionMantenimiento> encontrarVenta(Venta venta);
    
    public void actualizar(InformacionMantenimiento informacionMantenimiento);
}
