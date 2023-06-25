package com.gl05.bad.servicio;

import com.gl05.bad.domain.Roles;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface RolesService {
    
    public List<Roles> listaRoles();
    
    public DataTablesOutput<Roles> listarRoles(DataTablesInput input);
   
    public void AgregarRol(Roles rol);
    
    public Roles encontrarRol(Long rol);
    
    public void eliminarRol(Roles rol);
    
    public void actualizarRol(Roles rol);
}
