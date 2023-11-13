package com.gl05.bad.servicio;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface VentaService {
    
    public List<Venta> listaVentas();
    
    public DataTablesOutput<Venta> listarVentas(DataTablesInput input, Long idTerreno);
   
    public void agregar(Venta venta);
        
    public void eliminar(Venta venta);
    
    public Venta encontrar(Long idVenta);
    
    public Venta encontrarDocumento(Integer idDocumento);

    public List<Venta> encontrarActivas(Proyecto proyecto);
    
    public List<Venta> encontrarProyectoPrima(Proyecto proyecto);
    
    public List<Venta> encontrarProyectoFinanciamiento(Proyecto proyecto);
    
    public List<Venta> encontrarProyectoMantenimiento(Proyecto proyecto);
    
    public void actualizar(Venta venta);
}
