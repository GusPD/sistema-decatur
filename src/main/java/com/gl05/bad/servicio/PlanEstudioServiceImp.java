package com.gl05.bad.servicio;

import com.gl05.bad.dao.PlanEstudioDao;
import com.gl05.bad.domain.Maestria;
import com.gl05.bad.domain.PlanEstudio;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanEstudioServiceImp implements PlanEstudioService{
   
    @Autowired
    private PlanEstudioDao planEstudioDao;
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<PlanEstudio> listarPlanEstudio(DataTablesInput input){
        return (DataTablesOutput<PlanEstudio>)planEstudioDao.findAll(input);
    }
    
    @Override
    @Transactional
    public void proAgregar(Long idMaestria, String codPlan, String modalidad, BigDecimal cumMinimo, BigDecimal notaMinimaAprobacion, short totalAsignaturas, short totalUv, short duracion_carrera, String tituloOtorgar, short anio) {
      planEstudioDao.sp_insert_planestudio(idMaestria, codPlan, modalidad, cumMinimo, notaMinimaAprobacion, totalAsignaturas, totalUv, duracion_carrera, tituloOtorgar, anio);
    }
    
    @Override
    @Transactional
    public void eliminar(PlanEstudio planEstudio) {
        planEstudioDao.delete(planEstudio);
    }
    
    @Override
    @Transactional
    public void actualizar(PlanEstudio planEstudio) {
         
        if (planEstudioDao.existsById(planEstudio.getIdPlanEstudio())) {
            planEstudioDao.save(planEstudio);
        } else {
            throw new IllegalArgumentException("El plan de estudio especificado no existe.");
        }
    }
    
    @Override
    @Transactional(readOnly=true)
    public PlanEstudio encontrarPlanEstudio(PlanEstudio planEstudio){
        return planEstudioDao.findById(planEstudio.getIdPlanEstudio()).orElse(null);
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<PlanEstudio> listarPlanEstudioFiltrado(DataTablesInput input, Long idMaestria) {
        Specification<PlanEstudio> additionalSpecification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("idMaestria"), idMaestria);
        return planEstudioDao.findAll(input, additionalSpecification);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PlanEstudio encontrarPlanEstudioPorIdMaestria(Maestria maestria, short planEstado) {
        return planEstudioDao.findByPlanEstadoAndIdMaestria(planEstado, maestria);
    }
}
