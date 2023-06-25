package com.gl05.bad.servicio;

import com.gl05.bad.dao.EstudianteAsignaturaDao;
import com.gl05.bad.domain.Asignatura;
import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.EstudianteAsignatura;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstudianteAsignaturaServiceImp implements EstudianteAsignaturaService{
    
    @Autowired
    private EstudianteAsignaturaDao estudianteAsignaturaDao;
    
    @Autowired
    public EstudianteAsignaturaServiceImp(EstudianteAsignaturaDao estudianteAsignaturaDao) {
        this.estudianteAsignaturaDao = estudianteAsignaturaDao;
    }
    
    @Override
    @Transactional()
    public void guardarEstudianteAsignatura(EstudianteAsignatura estudianteAsignatura) {
        estudianteAsignaturaDao.save(estudianteAsignatura);
    }
    
     @Override
     @Transactional(readOnly=true)
    public boolean existeEstudianteAsignatura(Cohorte cohorte, Asignatura asignatura) {
        return estudianteAsignaturaDao.verificarEstudianteAsignatura(cohorte, asignatura);
    }
    
    @Override
     @Transactional(readOnly=true)
    public List<EstudianteAsignatura> encontrarPorIdCohorteIdasignatura(Long idCohorte, Long idCohorteAsignatura){
        return estudianteAsignaturaDao.findByCohorteAndAsignatura(idCohorte, idCohorteAsignatura);
    }
    
    @Override
    @Transactional()
    public void eliminarEstudianteAsignatura(EstudianteAsignatura estudianteAsignatura){
        estudianteAsignaturaDao.delete(estudianteAsignatura);
    }
}
