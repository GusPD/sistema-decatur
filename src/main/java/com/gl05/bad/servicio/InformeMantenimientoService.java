package com.gl05.bad.servicio;

import com.gl05.bad.domain.InformeMantenimiento;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface InformeMantenimientoService {

    public DataTablesOutput<InformeMantenimiento> listarInforme(DataTablesInput input, Long idProyecto);
    
    public List<InformeMantenimiento> listaInformeMantenimientos();
    
    public List<InformeMantenimiento> listaInformeMantenimientosProyecto(Proyecto proyecto);
   
    public void agregar(InformeMantenimiento informeMantenimiento);
        
    public void eliminar(InformeMantenimiento informeMantenimiento);

    public void eliminarProyecto (Proyecto proyecto);
    
    public InformeMantenimiento encontrar(Long idInformeMantenimiento);
    
    public InformeMantenimiento encontrarVenta(Venta venta);
    
    public void actualizar(InformeMantenimiento informeMantenimiento);
}
