package com.gl05.bad.servicio;

import com.gl05.bad.dao.ActividadDao;
import com.gl05.bad.dao.ListadoActividadEvaluadaDao;
import com.gl05.bad.domain.Actividad;
import com.gl05.bad.domain.ListadoActividadEvaluada;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActividadServiceImp implements ActividadService {

    @Autowired
    private ActividadDao actividadDao;

    @Autowired
    private ListadoActividadEvaluadaDao listadoActividadEvaluadaDao;
    
    @Override
    public List<Actividad> listaActividades(Long listProgramaAsignaturaId) {
        return (List<Actividad>) actividadDao.findActividadesByLisId(listProgramaAsignaturaId);
    }

    @Override
    public Actividad encontrarA(Long actividad) {
        return actividadDao.findById(actividad).orElse(null);
    }

    @Override
    public void actualizarA(Actividad actividad) {
        // Verifica si el área de conocimiento existe en la base de datos
        if (actividadDao.existsById(actividad.getIdActividad())) {
            actividadDao.save(actividad);

        } else {
            // El área de conocimiento no existe, puedes lanzar una excepción o manejar el caso según tus necesidades
            throw new IllegalArgumentException("La actividad especificada no existe.");
        }
    }

    @Override
    public void eliminarA(Actividad actividad) {
        actividadDao.delete(actividad);
    }

    @Override
    public Actividad encontrarActividad(Actividad actividad) {
        return actividadDao.findById(actividad.getIdActividad()).orElse(null);
    }

    @Override
    public void agregarA(Actividad actividad) {
        actividadDao.save(actividad);
    }

    @Override
    public ListadoActividadEvaluada encontrarList(Long id) {
        return listadoActividadEvaluadaDao.findById(id).orElse(null);
    }

}
