package com.gl05.bad.servicio;

import com.gl05.bad.domain.Bitacora;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface BitacoraService {
    public List<Bitacora> listaBitacora();
    
    public DataTablesOutput<Bitacora> listarBitacora(DataTablesInput input, LocalDateTime fechaInicio, LocalDateTime fechaFin, String usuario);
  
    public void registrarInicioSesion(String usuario);

    public void registrarAccion(String accion);

    public void registrarCerrarSesion(String usuario);
}


