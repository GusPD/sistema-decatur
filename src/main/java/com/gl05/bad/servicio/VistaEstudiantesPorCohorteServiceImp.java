package com.gl05.bad.servicio;

import com.gl05.bad.dao.VistaEstudiantesPorCohorteDao;
import com.gl05.bad.domain.VistaEstudiantesPorCohorte;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VistaEstudiantesPorCohorteServiceImp implements VistaEstudiantesPorCohorteService{
    private final VistaEstudiantesPorCohorteDao vistaEstudiantesPorCohorteDao;

    public VistaEstudiantesPorCohorteServiceImp(VistaEstudiantesPorCohorteDao vistaEstudiantesPorCohorteDao) {
        this.vistaEstudiantesPorCohorteDao = vistaEstudiantesPorCohorteDao;
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<VistaEstudiantesPorCohorte> obtenerEstudiantesPorCohorte(DataTablesInput input) {
        return vistaEstudiantesPorCohorteDao.findAll(input);
    }
}
