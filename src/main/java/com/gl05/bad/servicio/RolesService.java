package com.gl05.bad.servicio;

import com.gl05.bad.domain.Rol;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface RolesService {
    
    public List<Rol> listaRoles();
    
    public DataTablesOutput<Rol> listarRoles(DataTablesInput input);
   
    public void AgregarRol(Rol rol);
    
    public Rol encontrarRol(Long rol);
    
    public void eliminarRol(Rol rol);
    
    public void actualizarRol(Rol rol);
}
