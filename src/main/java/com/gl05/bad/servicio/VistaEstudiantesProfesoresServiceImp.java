package com.gl05.bad.servicio;

import com.gl05.bad.dao.VistaEstudiantesProfesoresDao;
import com.gl05.bad.domain.VistaEstudiantesProfesores;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VistaEstudiantesProfesoresServiceImp implements VistaEstudiantesProfesoresService{
    private final VistaEstudiantesProfesoresDao vistaEstudiantesProfesoresDao;

    public VistaEstudiantesProfesoresServiceImp(VistaEstudiantesProfesoresDao vistaEstudiantesProfesoresDao) {
        this.vistaEstudiantesProfesoresDao = vistaEstudiantesProfesoresDao;
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<VistaEstudiantesProfesores> obtenerEstudiantesProfesores(DataTablesInput input) {
        return vistaEstudiantesProfesoresDao.findAll(input);
    }
}
