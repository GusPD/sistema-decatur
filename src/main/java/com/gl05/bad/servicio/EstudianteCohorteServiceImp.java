package com.gl05.bad.servicio;

import com.gl05.bad.dao.EstudianteCohorteDao;
import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.Estudiante;
import com.gl05.bad.domain.EstudianteCohorte;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstudianteCohorteServiceImp implements EstudianteCohorteService{
    @Autowired
    private EstudianteCohorteDao estudianteCohorteDao;
    
    @Autowired
    public EstudianteCohorteServiceImp(EstudianteCohorteDao estudianteCohorteDao) {
        this.estudianteCohorteDao = estudianteCohorteDao;
    }

    @Override
    @Transactional
    public void guardarEstudianteCohorte(EstudianteCohorte estudianteCohorte) {
        estudianteCohorteDao.save(estudianteCohorte);
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<EstudianteCohorte> econtrarPorCohorte(DataTablesInput input, Cohorte idCohorte){
        Specification<EstudianteCohorte> additionalSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("idCohorte"), idCohorte);
        return estudianteCohorteDao.findAll(input, additionalSpecification);
    }
    
    @Override
    @Transactional
    public void eliminar(EstudianteCohorte estudianteCohorte) {
        estudianteCohorteDao.delete(estudianteCohorte);
    }
    
    @Override
    @Transactional(readOnly=true)
    public EstudianteCohorte encontrarEstudianteCohorte(Cohorte idCohorte, Estudiante idEstudiante){
     return estudianteCohorteDao.findByIdCohorteAndIdEstudiante(idCohorte, idEstudiante);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<EstudianteCohorte> encontrarEstudianteIdCohorte(Cohorte idCohorte){
        return estudianteCohorteDao.findByIdCohorte(idCohorte);
    }
}
