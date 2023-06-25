package com.gl05.bad.servicio;

import java.util.List;
import com.gl05.bad.dao.PaisDao;
import com.gl05.bad.domain.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import java.util.Collection;

@Service
public class PaisServiceImp implements PaisService{
  
    @Autowired
    private PaisDao paisDao;
    
    @Override
    @Transactional(readOnly=true)
    public Collection<Pais> listarPais() {
        return (Collection<Pais>)paisDao.findAll();
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<Pais> listarPais(DataTablesInput input) {
        return (DataTablesOutput<Pais>)paisDao.findAll(input);
    }

    @Override
    public List<Pais> listarPaises() {
        return (List<Pais>) paisDao.findAll();
    }

    @Override
    @Transactional
    public void agregarP(Pais pais) {
        paisDao.save(pais);
    }
    
    @Override
    @Transactional
    public void actualizarP(Pais pais) {
         
        if (paisDao.existsById(pais.getIdPais())) {
            paisDao.save(pais);
        } else {
            throw new IllegalArgumentException("El pais especificado no existe.");
        }
    }

    @Override
    public void eliminarP(Pais pais) {
        paisDao.delete(pais);
    }

    @Override
    public Pais encontrarP(Pais pais) {
        return paisDao.findById(pais.getIdPais()).orElse(null);
    }
}
