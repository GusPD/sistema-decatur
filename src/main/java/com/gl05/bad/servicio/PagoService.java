package com.gl05.bad.servicio;

import com.gl05.bad.domain.Pago;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface PagoService {
    
    public List<Pago> listaPagos();
    
    public DataTablesOutput<Pago> listarPagos(DataTablesInput input, Long idProyecto);
   
    public void agregar(Pago pago);
        
    public void eliminar(Pago pago);
    
    public Pago encontrar(Long idPago);
    
    public void actualizar(Pago pago);
}
