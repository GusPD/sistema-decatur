
package com.gl05.bad.servicio;

import com.gl05.bad.dao.CohorteDao;
import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.Maestria;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CohorteServiceImp implements CohorteService {

    @Autowired
    private CohorteDao cohorteDao;
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<Cohorte> listarCohorte(DataTablesInput input) {
         return (DataTablesOutput<Cohorte>)cohorteDao.findAll(input);
    }
  
    @Override
    @Transactional
    public void proAgregar(Long idMaestria, String nombreCohorte,Date fechaApertura, short estadoCohorte) {
        cohorteDao.sp_insert_cohorte(idMaestria, nombreCohorte, fechaApertura, estadoCohorte);
    }

    @Override
    @Transactional
    public void eliminarCohorte(Cohorte cohorte) {
        cohorteDao.delete(cohorte);
    }

    @Override
    @Transactional
    public void actualizarCohorte(Cohorte cohorte) {
        if (cohorteDao.existsById(cohorte.getIdCohorte())) {
            cohorteDao.save(cohorte);
        } else {
            throw new IllegalArgumentException("La cohorte no existe.");
        }
    }

    @Override
    @Transactional(readOnly=true)
    public Cohorte encontrarCohorte(Cohorte cohorte) {
        return cohorteDao.findById(cohorte.getIdCohorte()).orElse(null);
    }

    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<Cohorte> listarCohorteFiltrado(DataTablesInput input, Long idMaestria) {
       Specification<Cohorte> additionalSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("idMaestria"), idMaestria);
        return cohorteDao.findAll(input, additionalSpecification);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Cohorte> encontrarCohortesActivasPorIdMaestria(Maestria maestria, short estadoCohorte){
        return cohorteDao.findByEstadoCohorteAndIdMaestria(estadoCohorte, maestria);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Cohorte> encontrarCohortesPorIdMaestria(Maestria maestria){
        return cohorteDao.findByIdMaestria( maestria);
    }
}
