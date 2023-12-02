package com.gl05.bad.servicio;

import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.Propietario;
import com.gl05.bad.domain.Venta;

import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface AsignacionPropietarioService {
    
    public List<AsignacionPropietario> listaAsignacion();

    public List<AsignacionPropietario> listaAsignacion(Venta venta);

    public List<AsignacionPropietario> listaAsignacionPropietarioSeleccionado(Venta venta);
   
    public void agregar(AsignacionPropietario asignacionPropietario);
        
    public void eliminar(AsignacionPropietario asignacionPropietario);
    
    public AsignacionPropietario encontrar(Long idAsignacionPropietario);
    
    public AsignacionPropietario encontrarPropietarioVenta(Propietario propietario, Venta venta);
    
    public void actualizar(AsignacionPropietario asignacionPropietario);
    
    public DataTablesOutput<AsignacionPropietario> listarPropietarios(DataTablesInput input, Long idProyecto);
    
    public DataTablesOutput<AsignacionPropietario> listarPropietariosVenta(DataTablesInput input, Long idVenta);

    public DataTablesOutput<AsignacionPropietario> listarTerrenosPropietario(DataTablesInput input, Long idPropietario);
}
