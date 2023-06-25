package com.gl05.bad.servicio;

import com.gl05.bad.domain.Maestria;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface MaestriaService {
    
    public Collection<Maestria> listarMaestrias();
    
    public DataTablesOutput<Maestria> listarMaestrias(DataTablesInput input);
    
    public void agregar(Maestria maestria);
    
    public void proAgregar(String nombre, String posgrado);
    
    public void eliminar(Maestria maestria);
    
    public Maestria encontrarMaestria(Maestria maestria);
    
    public Collection<Maestria> encontrarMaestrias(Integer idUsuario);
    
    public void actualizar(Maestria maestria);
}
