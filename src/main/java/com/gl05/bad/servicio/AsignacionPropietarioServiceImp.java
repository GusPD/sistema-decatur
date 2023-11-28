package com.gl05.bad.servicio;

import com.gl05.bad.dao.AsignacionPropietarioDao;
import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.Propietario;
import java.util.List;

import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsignacionPropietarioServiceImp implements AsignacionPropietarioService{
    
    @Autowired
    private AsignacionPropietarioDao asignacionDao;

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionPropietario> listaAsignacion() {
        return (List<AsignacionPropietario>) asignacionDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(AsignacionPropietario asignacion) {
        asignacionDao.save(asignacion);
    }

    @Override
    @Transactional
    public void eliminar(AsignacionPropietario asignacion) {
        asignacionDao.delete(asignacion);
    }

    @Override
    @Transactional(readOnly = true)
    public AsignacionPropietario encontrar(Long idAsignacion) {
        return asignacionDao.findById(idAsignacion).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public AsignacionPropietario encontrarPropietario(Propietario propietario) {
        return asignacionDao.findByPropietario(propietario);
    }

    @Override
    @Transactional
    public void actualizar(AsignacionPropietario asignacion) {
        if (asignacionDao.existsById(asignacion.getIdAsignacion())) {
            asignacionDao.save(asignacion);
        } else {
            throw new IllegalArgumentException("La asignación no existe.");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<AsignacionPropietario> listarPropietarios(DataTablesInput input, Long idProyecto) {
        Specification<AsignacionPropietario> specification = (root, query, builder) -> {
            return builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
        };
        return asignacionDao.findAll(input, specification);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<AsignacionPropietario> listarPropietariosVenta(DataTablesInput input, Long idVenta) {
        Specification<AsignacionPropietario> specification = (root, query, builder) -> {
            root.fetch("venta", JoinType.INNER);
            root.fetch("propietario", JoinType.INNER);
            query.distinct(true);
            return builder.equal(root.get("venta").get("idVenta"), idVenta);
        };
        return asignacionDao.findAll(input, specification);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<AsignacionPropietario> listarTerrenosPropietario(DataTablesInput input, Long idPropietario) {
        Specification<AsignacionPropietario> specification = (root, query, builder) -> {
            root.fetch("propietario", JoinType.INNER);
            root.fetch("venta", JoinType.INNER);
            return builder.equal(root.get("propietario").get("idPropietario"), idPropietario);
        };
        return asignacionDao.findAll(input, specification);
    }
}
