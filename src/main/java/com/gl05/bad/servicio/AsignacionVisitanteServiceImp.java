package com.gl05.bad.servicio;

import com.gl05.bad.dao.AsignacionVisitanteDao;
import com.gl05.bad.domain.AsignacionVisitante;
import com.gl05.bad.domain.Visitante;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsignacionVisitanteServiceImp implements AsignacionVisitanteService{
    
    @Autowired
    private AsignacionVisitanteDao asignacionDao;

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionVisitante> listaAsignacionVisitantes() {
        return (List<AsignacionVisitante>) asignacionDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(AsignacionVisitante asignacion) {
        asignacionDao.save(asignacion);
    }

    @Override
    @Transactional
    public void eliminar(AsignacionVisitante asignacion) {
        asignacionDao.delete(asignacion);
    }

    @Override
    @Transactional(readOnly = true)
    public AsignacionVisitante encontrar(Long idAsignacion) {
        return asignacionDao.findById(idAsignacion).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public AsignacionVisitante encontrarVisitante(Visitante visitante) {
        return asignacionDao.findByVisitante(visitante);
    }


    @Override
    @Transactional
    public void actualizar(AsignacionVisitante asignacion) {
        if (asignacionDao.existsById(asignacion.getIdAsignacion())) {
            asignacionDao.save(asignacion);
        } else {
            throw new IllegalArgumentException("La asignación del visitante no existe.");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<AsignacionVisitante> listarAsignacionVisitantes(DataTablesInput input, Long idVenta) {
        Specification<AsignacionVisitante> specification = (root, query, builder) -> {
            return builder.equal(root.get("venta").get("idVenta"), idVenta);
        };
        return asignacionDao.findAll(input, specification);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<AsignacionVisitante> listarTrabajadoresVenta (DataTablesInput input, Long idVenta) {
        Specification<AsignacionVisitante> specification = (root, query, builder) -> {
            Predicate ventaIdEqual = builder.equal(root.get("venta").get("idVenta"), idVenta);
            Predicate rolTrabajador = builder.equal(root.get("visitante").get("rol"), "TRABAJADOR");
            return builder.or(ventaIdEqual, rolTrabajador);
        };
        return asignacionDao.findAll(input, specification);
    }
}
