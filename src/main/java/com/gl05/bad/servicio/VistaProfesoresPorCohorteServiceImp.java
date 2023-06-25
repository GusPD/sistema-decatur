package com.gl05.bad.servicio;

import com.gl05.bad.dao.VistaProfesoresPorCohorteDao;
import com.gl05.bad.domain.VistaProfesoresPorCohorte;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VistaProfesoresPorCohorteServiceImp implements VistaProfesoresPorCohorteService{
    private final VistaProfesoresPorCohorteDao vistaProfesoresPorCohorteDao;

    public VistaProfesoresPorCohorteServiceImp(VistaProfesoresPorCohorteDao vistaProfesoresPorCohorteDao) {
        this.vistaProfesoresPorCohorteDao = vistaProfesoresPorCohorteDao;
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<VistaProfesoresPorCohorte> obtenerProfesoresContratados(DataTablesInput input) {
       return vistaProfesoresPorCohorteDao.findAll(input);
    }
}
