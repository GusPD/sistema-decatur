package com.gl05.bad.servicio;

import com.gl05.bad.dao.EstudianteDao;
import com.gl05.bad.domain.Estudiante;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstudianteServiceImp implements EstudianteService{

    @Autowired
    private EstudianteDao estudianteDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<Estudiante> listaEstudiantes() {
         return (List<Estudiante>) estudianteDao.findAll();
    }

    @Override
    @Transactional
    public void agregarEstudiante(Estudiante estudiante) {
       estudianteDao.save(estudiante);
    }

    @Override
    @Transactional(readOnly=true)
    public Estudiante encontrarEstudiante(Long estudiante) {
         return estudianteDao.findById(estudiante).orElse(null);
    }

    @Override
    @Transactional
    public void eliminarEstudiante(Estudiante estudiante) {
        estudianteDao.delete(estudiante);
    }

    @Override
    @Transactional
    public void actualizarEstudiante(Estudiante estudiante) {
        if (estudianteDao.existsById(estudiante.getIdEstudiante())) {
            // Actualiza el estudiante en la base de datos
            estudianteDao.save(estudiante);
        } else {
            throw new IllegalArgumentException("El estudiante no existe.");
        }
    }

    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<Estudiante> listaEstudiantes(DataTablesInput input) {
        return (DataTablesOutput<Estudiante>)estudianteDao.findAll(input);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Estudiante> obtenerEstudiantesSinCohorte(@Param("idCohorte") Long idCohorte){
        return estudianteDao.findEstudiantesSinCohorte(idCohorte);
    }
        
    @Override
    public List<String> obtenerCorreosEstudiantes() {
        List<Estudiante> estudiantes = listaEstudiantes();
        List<String> correos = new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {
            correos.add(estudiante.getCorreoE());
        }

        return correos;
    }
}
