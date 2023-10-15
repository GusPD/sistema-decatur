package com.gl05.bad.servicio;

import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.Propietario;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface AsigPropietarioVentaService {
    
    public List<AsignacionPropietario> listaAsignacion();
   
    public void agregar(AsignacionPropietario asignacionPropietario);
        
    public void eliminar(AsignacionPropietario asignacionPropietario);
    
    public AsignacionPropietario encontrar(Long idAsignacionPropietario);
    
    public AsignacionPropietario encontrarPropietario(Propietario propietario);
    
    public void actualizar(AsignacionPropietario asignacionPropietario);
    
    public DataTablesOutput<AsignacionPropietario> listarPropietarios(DataTablesInput input, Long idProyecto);
    
    public DataTablesOutput<AsignacionPropietario> listarPropietariosVenta(DataTablesInput input, Long idVenta);
}
