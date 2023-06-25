package com.gl05.bad.servicio;

import com.gl05.bad.dao.ProgramaAsignaturaDao;
import com.gl05.bad.domain.ProgramaAsignatura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgramaAsignaturaServiceImp implements ProgramaAsignaturaService {

    @Autowired
    private ProgramaAsignaturaDao programaAsignaturaDao;

    @Override
    public long encontrarPrograma(Long listProgramaAsignaturaId) {
        return programaAsignaturaDao.findProgramaByAsignaturaId(listProgramaAsignaturaId);
    }

    @Override
    public ProgramaAsignatura encontrarP(Long idProgramaAsignatura) {
        return programaAsignaturaDao.findById(idProgramaAsignatura).orElse(null);
    }

    @Override
    public void actualizarP(ProgramaAsignatura programa) {
        // Verifica si el área de conocimiento existe en la base de datos
        if (programaAsignaturaDao.existsById(programa.getIdProgramAsignatura())) {
            programaAsignaturaDao.save(programa);

        } else {
            // El área de conocimiento no existe, puedes lanzar una excepción o manejar el caso según tus necesidades
            throw new IllegalArgumentException("El programa especificada no existe.");
        }
    }

    @Override
    public ProgramaAsignatura encontrarPrograma(ProgramaAsignatura programa) {
        return programaAsignaturaDao.findById(programa.getIdProgramAsignatura()).orElse(null);
    }

}
