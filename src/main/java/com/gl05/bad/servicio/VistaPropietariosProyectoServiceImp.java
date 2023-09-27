package com.gl05.bad.servicio;

import com.gl05.bad.dao.VistaPropietariosProyectoDao;
import com.gl05.bad.domain.VistaPropietariosProyecto;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VistaPropietariosProyectoServiceImp implements VistaPropietariosProyectoService{
    
    @Autowired
    private VistaPropietariosProyectoDao propietarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<VistaPropietariosProyecto> listaPropietarios() {
        return (List<VistaPropietariosProyecto>) propietarioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<VistaPropietariosProyecto> listarPropietarios(DataTablesInput input, Long idProyecto) {
        Specification<VistaPropietariosProyecto> specification = (root, query, builder) -> {
            Predicate condition = builder.equal(root.get("idProyecto"), idProyecto);
            return condition;
        };
        return propietarioDao.findAll(input, specification);
    }
}
