package com.gl05.bad.servicio;

import com.gl05.bad.domain.Referencia;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface ReferenciaService {
  
    public List<Referencia> listaReferencias();

    public void agregar(Referencia referencia);

    public void actualizar(Referencia referencia);

    public void eliminar(Referencia referencia);

    public Referencia encontrar(Referencia referencia);

    public DataTablesOutput<Referencia> listarReferencias(DataTablesInput input, Long idPropietario);

}
