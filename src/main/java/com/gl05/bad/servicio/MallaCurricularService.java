package com.gl05.bad.servicio;

import com.gl05.bad.domain.MallaCurricular;
import com.gl05.bad.domain.PlanEstudio;
import java.util.List;

public interface MallaCurricularService {
    public MallaCurricular obtenerMallaCurricularPlan(PlanEstudio idPlanEstudio);
}
