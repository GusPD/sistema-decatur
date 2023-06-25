package com.gl05.bad.servicio;

import com.gl05.bad.dao.ProfesorAsignaturaDao;
import com.gl05.bad.domain.ProfesorAsignatura;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfesorAsignaturaServiceImp implements ProfesorAsignaturaService {

    @Autowired
    private ProfesorAsignaturaDao profesorAsignaturaDao;
    
    @Override
    public void agregarA(ProfesorAsignatura profesorAsignatura) {
        profesorAsignaturaDao.save(profesorAsignatura);
    }
    @Override
    public List<ProfesorAsignatura> listarAsignaturas(Long idProfesor) {
        return (List<ProfesorAsignatura>)profesorAsignaturaDao.findProfesAsignaturaId(idProfesor);
    }

}
