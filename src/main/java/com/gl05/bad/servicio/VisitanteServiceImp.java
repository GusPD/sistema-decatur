package com.gl05.bad.servicio;

import com.gl05.bad.dao.VisitanteDao;
import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Visitante;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitanteServiceImp implements VisitanteService{
    
    @Autowired
    private VisitanteDao visitanteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Visitante> listaVisitantes() {
        return (List<Visitante>) visitanteDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Visitante visitante) {
        visitanteDao.save(visitante);
    }

    @Override
    @Transactional
    public void eliminar(Visitante visitante) {
        visitanteDao.delete(visitante);
    }

    @Override
    @Transactional(readOnly = true)
    public Visitante encontrar(Long idVisitante) {
        return visitanteDao.findById(idVisitante).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Visitante encontrarPersona(Persona persona) {
        return visitanteDao.findByPersona(persona);
    }

    @Override
    @Transactional
    public void actualizar(Visitante visitante) {
        if (visitanteDao.existsById(visitante.getIdVisitante())) {
            visitanteDao.save(visitante);
        } else {
            throw new IllegalArgumentException("El visitante no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Visitante> listarVisitantes(DataTablesInput input) {
        return (DataTablesOutput<Visitante>) visitanteDao.findAll(input);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Visitante> listarTrabajadores(DataTablesInput input) {
        Specification<Visitante> specification = (root, query, builder) -> {
            return builder.equal(root.get("rol"),"TRABAJADOR" );
        };
        return (DataTablesOutput<Visitante>) visitanteDao.findAll(input,specification);
    }
}
