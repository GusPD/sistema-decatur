package com.gl05.bad.servicio;

import com.gl05.bad.domain.Facturacion;
import com.gl05.bad.domain.Venta;
import java.util.List;

public interface FacturacionService {
    
    public List<Facturacion> listaFacturaciones();
   
    public void agregar(Facturacion facturacion);
        
    public void eliminar(Facturacion facturacion);
    
    public Facturacion encontrar(Long idFacturacion);
    
    public Facturacion encontrarVenta(Venta venta);
    
    public void actualizar(Facturacion facturacion);
}
