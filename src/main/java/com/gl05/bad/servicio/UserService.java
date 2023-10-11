package com.gl05.bad.servicio;

import com.gl05.bad.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface UserService {
    
    public List<Usuario> listaUsuarios();
    
    public DataTablesOutput<Usuario> listarUsuarios(DataTablesInput input);
   
    public void agregar(Usuario usuario);
        
    public void eliminar(Usuario usuario);
    
    public Usuario encontrar(Long usuario);
    
    public Usuario encontrarUsername(String username);
    
    public Usuario encontrarEmail(String email);
    
    public void actualizar(Usuario usuario);
}
