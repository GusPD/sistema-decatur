package com.gl05.bad.dao;

import com.gl05.bad.domain.MallaCurricular;
import com.gl05.bad.domain.PlanEstudio;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface MallaCurricularDao extends DataTablesRepository<MallaCurricular, Long>{
    MallaCurricular findByIdPlanEstudio(PlanEstudio idPlanEstudio);
}
