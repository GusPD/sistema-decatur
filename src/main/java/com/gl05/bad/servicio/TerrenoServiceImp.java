package com.gl05.bad.servicio;

import com.gl05.bad.domain.Terreno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gl05.bad.dao.TerrenoDao;

@Service
public class TerrenoServiceImp implements TerrenoService{
    
    @Autowired
    private TerrenoDao terrenoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Terreno> listaTerrenos() {
        return (List<Terreno>) terrenoDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Terreno terreno) {
        terrenoDao.save(terreno);
    }

    @Override
    @Transactional
    public void eliminar(Terreno terreno) {
        terrenoDao.delete(terreno);
    }

    @Override
    @Transactional(readOnly = true)
    public Terreno encontrar(Long idTerreno) {
        return terrenoDao.findById(idTerreno).orElse(null);
    }

    @Override
    @Transactional
    public void actualizar(Terreno terreno) {
        if (terrenoDao.existsById(terreno.getIdTerreno())) {
            terrenoDao.save(terreno);
        } else {
            throw new IllegalArgumentException("El terreno no existe.");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Terreno> listarTerrenos(DataTablesInput input, Long idProyecto) {
        Specification<Terreno> specification = (root, query, builder) -> {
            return builder.equal(root.get("proyecto").get("idProyecto"), idProyecto);
        };
        return terrenoDao.findAll(input, specification);
    }    
}
