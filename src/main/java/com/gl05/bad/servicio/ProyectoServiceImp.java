package com.gl05.bad.servicio;

import com.gl05.bad.dao.ProyectoDao;
import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.Proyecto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProyectoServiceImp implements ProyectoService{
    
    @Autowired
    private ProyectoDao proyectoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Proyecto> listaProyectos() {
        return (List<Proyecto>) proyectoDao.findAll();
    }

    @Override
    @Transactional
    public void agregarProyecto(Proyecto proyecto) {
        proyectoDao.save(proyecto);
    }

    @Override
    @Transactional
    public void eliminarProyecto(Proyecto proyecto) {
        proyectoDao.delete(proyecto);
    }

    @Override
    @Transactional(readOnly = true)
    public Proyecto encontrarProyecto(Long idProyecto) {
        return proyectoDao.findById(idProyecto).orElse(null);
    }

    @Override
    @Transactional
    public void actualizarProyecto(Proyecto proyecto) {
        if (proyectoDao.existsById(proyecto.getIdProyecto())) {
            proyectoDao.save(proyecto);
        } else {
            throw new IllegalArgumentException("El proyecto no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Proyecto> listarProyectos(DataTablesInput input) {
        return (DataTablesOutput<Proyecto>) proyectoDao.findAll(input);
    }
}
