package com.gl05.bad.servicio;

import com.gl05.bad.domain.Actividad;
import com.gl05.bad.domain.ListadoActividadEvaluada;
import java.util.List;

public interface ActividadService {

    public List<Actividad> listaActividades(Long listProgramaAsignaturaId);

    public Actividad encontrarA(Long actividad);

    public void actualizarA(Actividad actividad);

    public void eliminarA(Actividad actividad);

    public Actividad encontrarActividad(Actividad actividad);

    public void agregarA(Actividad actividad);

    public ListadoActividadEvaluada encontrarList(Long id);
}
