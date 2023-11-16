package com.gl05.bad.servicio;

import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface PagoService {
    
    public List<Pago> listaPagos();
    
    public DataTablesOutput<Pago> listarPagos(DataTablesInput input, Long idProyecto, Date fechaInicio, Date fechaFin, String comprobante, Boolean estado, Integer tipoPago, Venta venta);
   
    public DataTablesOutput<Pago> listarPagosVenta(DataTablesInput input, Long idVenta);

    public DataTablesOutput<Pago> listarPrimaVenta(DataTablesInput input, Long idVenta);
    
    public void agregar(Pago pago);
        
    public void eliminar(Pago pago);
    
    public Pago encontrar(Long idPago);

    public List<Pago> encontrarPago(Boolean estado, String tipo,Venta venta);

    public List<Pago> encontrarMayores(String tipo,Venta venta, LocalDateTime fechaRegistro);

    public Pago encontrarRecibo(String tipo,Integer recibo, Proyecto proyecto, String comprobante);
    
    public void actualizar(Pago pago);
}
