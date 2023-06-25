package com.gl05.bad.servicio;

import com.gl05.bad.dao.ExperienciaLaboralDao;
import com.gl05.bad.domain.ExperienciaLaboral;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExperienciaLaboralServiceImp implements ExperienciaLaboralService{
  
    @Autowired
    private ExperienciaLaboralDao experienciaLaboralDao;

    @Override
    public List<ExperienciaLaboral> listarExperienciasLaboral() {
        return (List<ExperienciaLaboral>) experienciaLaboralDao.findAll();
    }

    @Override
    @Transactional
    public void agregarEL(ExperienciaLaboral experienciaLaboral) {
        experienciaLaboralDao.save(experienciaLaboral);
    }
    
    @Override
    @Transactional
    public void actualizarEL(ExperienciaLaboral experienciaLaboral) {
        ExperienciaLaboral experienciaLaboralExistente = experienciaLaboralDao.findById(experienciaLaboral.getIdEl()).orElse(null);
        experienciaLaboralDao.save(experienciaLaboralExistente);
    }

    @Override
    public void eliminarEL(ExperienciaLaboral experienciaLaboral) {
        experienciaLaboralDao.delete(experienciaLaboral);
    }

    @Override
    public ExperienciaLaboral encontrarEL(ExperienciaLaboral experienciaLaboral) {
        return experienciaLaboralDao.findById(experienciaLaboral.getIdEl()).orElse(null);
    }
}
