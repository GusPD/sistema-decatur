package com.gl05.bad.servicio;

import com.gl05.bad.domain.CuotaMantenimiento;
import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Venta;

import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface CuotaMantenimientoService {
    
    public List<CuotaMantenimiento> listaCuotaMantenimientos();
    
    public DataTablesOutput<CuotaMantenimiento> listarCuotaMantenimientos(DataTablesInput input, Long idPago);
   
    public void agregar(CuotaMantenimiento cuotaMantenimiento);
        
    public void eliminar(CuotaMantenimiento cuotaMantenimiento);
    
    public CuotaMantenimiento encontrar(Long idCuotaMantenimiento);

    public CuotaMantenimiento encontrarUltimaCuota(Venta venta);

    public List<CuotaMantenimiento> encontrarPago(Pago pago);
    
    public void actualizar(CuotaMantenimiento cuotaMantenimiento);
}
