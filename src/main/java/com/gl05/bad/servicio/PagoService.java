package com.gl05.bad.servicio;

import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Venta;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface PagoService {
    
    public List<Pago> listaPagos();
    
    public DataTablesOutput<Pago> listarPagos(DataTablesInput input, Long idProyecto, Date fechaInicio, Date fechaFin, String comprobante, Boolean estado, Integer tipoPago, Venta venta);
   
    public void agregar(Pago pago);
        
    public void eliminar(Pago pago);
    
    public Pago encontrar(Long idPago);

    public List<Pago> encontrarPago(String tipo,Venta venta);
    
    public void actualizar(Pago pago);
}
