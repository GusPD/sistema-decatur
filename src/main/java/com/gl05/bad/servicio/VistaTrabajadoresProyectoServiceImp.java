package com.gl05.bad.servicio;

import com.gl05.bad.dao.VistaTrabajadoresProyectoDao;
import com.gl05.bad.domain.VistaTrabajadoresProyecto;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VistaTrabajadoresProyectoServiceImp implements VistaTrabajadoresProyectoService{
    
    @Autowired
    private VistaTrabajadoresProyectoDao trabajadorDao;

    @Override
    @Transactional(readOnly = true)
    public List<VistaTrabajadoresProyecto> listaTrabajadores() {
        return (List<VistaTrabajadoresProyecto>) trabajadorDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<VistaTrabajadoresProyecto> listarTrabajadores(DataTablesInput input, Long idProyecto) {
        Specification<VistaTrabajadoresProyecto> specification = (root, query, builder) -> {
            Predicate condition = builder.equal(root.get("idProyecto"), idProyecto);
            return condition;
        };
        return trabajadorDao.findAll(input, specification);
    }
}
