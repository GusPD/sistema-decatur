package com.gl05.bad.servicio;

import com.gl05.bad.dao.VistaTerrenoDao;
import com.gl05.bad.domain.VistaTerreno;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VistaTerrenoServiceImp implements VistaTerrenoService{
    
    @Autowired
    private VistaTerrenoDao terrenoDao;

    @Override
    @Transactional(readOnly = true)
    public List<VistaTerreno> listaTerrenos() {
        return (List<VistaTerreno>) terrenoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<VistaTerreno> listarTerrenos(DataTablesInput input, Long idProyecto) {
        Specification<VistaTerreno> specification = (root, query, builder) -> {
            Predicate condition = builder.equal(root.get("idProyecto"), idProyecto);
            return condition;
        };
        return terrenoDao.findAll(input, specification);
    }
}
