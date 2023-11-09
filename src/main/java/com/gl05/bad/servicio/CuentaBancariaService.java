package com.gl05.bad.servicio;

import com.gl05.bad.domain.CuentaBancaria;
import com.gl05.bad.domain.Empresa;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface CuentaBancariaService {
    
    public List<CuentaBancaria> listaCuentas();
    
    public DataTablesOutput<CuentaBancaria> listarCuentas(DataTablesInput input, Long idEmpresa);
   
    public void agregar(CuentaBancaria cuenta);
        
    public void eliminar(CuentaBancaria cuenta);
    
    public CuentaBancaria encontrar(Long idCuenta);
    
    public List<CuentaBancaria> encontrarEmpresa(Empresa empresa);
    
    public void actualizar(CuentaBancaria cuenta);
}
