package com.gl05.bad.servicio;

import com.gl05.bad.domain.Maestria;
import com.gl05.bad.domain.PlanEstudio;
import java.math.BigDecimal;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface PlanEstudioService {
    
    public DataTablesOutput<PlanEstudio> listarPlanEstudio(DataTablesInput input);
    
    public void proAgregar(Long idMaestria, String codPlan, String modalidad, BigDecimal cumMinimo, BigDecimal notaMinimaAprobacion, short totalAsignaturas, short totalUv, short duracion_carrera, String tituloOtorgar, short anio);
    
    public void eliminar(PlanEstudio planEstudio);
    
    public void actualizar(PlanEstudio planEstudio);
    
    public PlanEstudio encontrarPlanEstudio(PlanEstudio planEstudio);
    
    public DataTablesOutput<PlanEstudio> listarPlanEstudioFiltrado(DataTablesInput input, Long idMaestria);
    
    public PlanEstudio encontrarPlanEstudioPorIdMaestria(Maestria maestria, short planEstado);
    
}
