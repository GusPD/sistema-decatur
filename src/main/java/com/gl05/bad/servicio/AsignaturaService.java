package com.gl05.bad.servicio;

import com.gl05.bad.domain.Asignatura;
import com.gl05.bad.domain.MallaCurricular;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface AsignaturaService {

    public DataTablesOutput<Asignatura> listarAsignatura(DataTablesInput input);

    public Collection<Asignatura> listarAsignatura();

    public void agregarA(Asignatura asignatura);

    public void AgregarAsig(String codigoAsignatura, String nombreAsignatura, int uv, int numeroCorrelativo, int ciclo, long idAreaC, long idMalla, int duracion, int horasT, int horasP, int horaCiclo, String introduccion, String descipcionPrograma, String objetivo, String metodologia, String sistemaEvaluacion, String bibliografia, String actividad, String ponderacion);

    public void eliminarA(Asignatura asignatura);

    public Asignatura encontrarA(Asignatura asignatura);

    public long encontrarMalla(Long idPlanEstudio);

    public void actualizarA(Asignatura asignatura);

    public DataTablesOutput<Asignatura> listarAsignaturaFiltrado(DataTablesInput input, Long idMallaCurricular);
    
    public List<Asignatura> encontrarAsignaturasPorMalla(MallaCurricular idMallaCurricular);
   
    public Asignatura encontrarAsig(Long asignatura);

}
