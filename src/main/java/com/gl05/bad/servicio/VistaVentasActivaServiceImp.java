package com.gl05.bad.servicio;

import com.gl05.bad.dao.VistaVentasActivaDao;
import com.gl05.bad.domain.VistaVentasActiva;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VistaVentasActivaServiceImp implements VistaVentasActivaService{
    
    @Autowired
    private VistaVentasActivaDao terrenoDao;

    @Override
    @Transactional(readOnly = true)
    public List<VistaVentasActiva> listaTerrenos() {
        return (List<VistaVentasActiva>) terrenoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<VistaVentasActiva> listarTerrenos(DataTablesInput input, Long idProyecto) {
        Specification<VistaVentasActiva> specification = (root, query, builder) -> {
            Predicate condition = builder.equal(root.get("idProyecto"), idProyecto);
            return condition;
        };
        return terrenoDao.findAll(input, specification);
    }
}
