package com.gl05.bad.servicio;

import com.gl05.bad.domain.Venta;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface VentaService {
    
    public List<Venta> listaVentas();
    
    public DataTablesOutput<Venta> listarVentas(DataTablesInput input, Long idProyecto);
   
    public void agregarVenta(Venta venta);
        
    public void eliminarVenta(Venta venta);
    
    public Venta encontrarVenta(Long idVenta);
    
    public void actualizarVenta(Venta venta);
}
