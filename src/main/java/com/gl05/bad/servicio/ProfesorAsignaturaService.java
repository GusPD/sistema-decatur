package com.gl05.bad.servicio;

import com.gl05.bad.domain.Actividad;
import com.gl05.bad.domain.ProfesorAsignatura;
import java.util.Collection;
import java.util.List;

public interface ProfesorAsignaturaService {

    public void agregarA(ProfesorAsignatura profesorAsignatura);

    public List<ProfesorAsignatura> listarAsignaturas(Long idProfesor);
}
