package com.gl05.bad.servicio;

import com.gl05.bad.domain.ProgramaAsignatura;

public interface ProgramaAsignaturaService {

    public long encontrarPrograma(Long idProgramaAsignatura);

    public ProgramaAsignatura encontrarP(Long idProgramaAsignatura);

    public void actualizarP(ProgramaAsignatura programa);

    public ProgramaAsignatura encontrarPrograma(ProgramaAsignatura programa);

}
