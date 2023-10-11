package com.gl05.bad.servicio;

import com.gl05.bad.domain.Empresa;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface EmpresaService {
    
    public List<Empresa> listaEmpresas();
    
    public DataTablesOutput<Empresa> listarEmpresas(DataTablesInput input);
   
    public void agregar(Empresa empresa);
        
    public void eliminar(Empresa empresa);
    
    public Empresa encontrar(Long idEmpresa);
    
    public void actualizar(Empresa empresa);
}
