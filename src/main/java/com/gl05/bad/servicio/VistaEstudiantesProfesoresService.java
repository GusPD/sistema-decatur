
package com.gl05.bad.servicio;

import com.gl05.bad.domain.VistaEstudiantesProfesores;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface VistaEstudiantesProfesoresService {
    public DataTablesOutput<VistaEstudiantesProfesores> obtenerEstudiantesProfesores(DataTablesInput input);
}
