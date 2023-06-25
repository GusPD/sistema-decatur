package com.gl05.bad.servicio;

import com.gl05.bad.dao.MallaCurricularDao;
import com.gl05.bad.domain.MallaCurricular;
import com.gl05.bad.domain.PlanEstudio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MallaCurricularServiceImp implements MallaCurricularService{
    
    @Autowired
    private MallaCurricularDao mallaCurricularDao;
    
    @Override
    @Transactional(readOnly=true)
    public MallaCurricular obtenerMallaCurricularPlan(PlanEstudio idPlanEstudio){
        return mallaCurricularDao.findByIdPlanEstudio(idPlanEstudio);
    }
}
