
package com.gl05.bad.servicio;

import com.gl05.bad.domain.VistaEstudiantesPorCohorte;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface VistaEstudiantesPorCohorteService {
    public DataTablesOutput<VistaEstudiantesPorCohorte> obtenerEstudiantesPorCohorte(DataTablesInput input);
}
