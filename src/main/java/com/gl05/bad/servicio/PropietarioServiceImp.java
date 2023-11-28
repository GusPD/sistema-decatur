package com.gl05.bad.servicio;

import com.gl05.bad.dao.PropietarioDao;
import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Propietario;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropietarioServiceImp implements PropietarioService{
    
    @Autowired
    private PropietarioDao propietarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Propietario> listaPropietarios() {
        return (List<Propietario>) propietarioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Propietario> listaPropietariosProyecto(Proyecto proyecto) {
        return (List<Propietario>) propietarioDao.findPropietariosByProyecto(proyecto.getIdProyecto());
    }

    @Override
    @Transactional
    public void agregar(Propietario propietario) {
        propietarioDao.save(propietario);
    }

    @Override
    @Transactional
    public void eliminar(Propietario propietario) {
        propietarioDao.delete(propietario);
    }

    @Override
    @Transactional(readOnly = true)
    public Propietario encontrar(Long idPropietario) {
        return propietarioDao.findById(idPropietario).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Propietario encontrarPersona(Persona persona) {
        return propietarioDao.findByPersona(persona);
    }

    @Override
    @Transactional
    public void actualizar(Propietario propietario) {
        if (propietarioDao.existsById(propietario.getIdPropietario())) {
            propietarioDao.save(propietario);
        } else {
            throw new IllegalArgumentException("El propietario no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Propietario> listarPropietarios(DataTablesInput input) {
        return propietarioDao.findAll(input);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Propietario> listarPropietariosVenta(DataTablesInput input, Long idVenta) {
        Specification<Propietario> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Propietario, Venta> joinVenta = root.join("ventas", JoinType.INNER);
            predicates.add(builder.equal(joinVenta.get("idVenta"), idVenta));
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return propietarioDao.findAll(input, specification);
    }
}
