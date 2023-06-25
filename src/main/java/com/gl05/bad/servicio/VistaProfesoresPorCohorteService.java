
package com.gl05.bad.servicio;

import com.gl05.bad.domain.VistaProfesoresPorCohorte;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface VistaProfesoresPorCohorteService {
    public DataTablesOutput<VistaProfesoresPorCohorte> obtenerProfesoresContratados(DataTablesInput input);
}
