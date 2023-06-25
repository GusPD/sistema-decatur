package com.gl05.bad.servicio;

import com.gl05.bad.dao.MaestriaDao;
import com.gl05.bad.domain.Maestria;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaestriaServiceImp implements MaestriaService{
    
    @Autowired
    private MaestriaDao maestriaDao;
    
    @Override
    @Transactional(readOnly=true)
    public Collection<Maestria> listarMaestrias() {
        return (Collection<Maestria>)maestriaDao.findAll();
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<Maestria> listarMaestrias(DataTablesInput input) {
        return (DataTablesOutput<Maestria>)maestriaDao.findAll(input);
    }

    @Override
    @Transactional
    public void agregar(Maestria maestria) {
        maestriaDao.save(maestria);
    }
    
    @Override
    public void proAgregar(String nombre, String posgrado) {
      maestriaDao.sp_insert_maestria(nombre, posgrado);
    }

    @Override
    @Transactional
    public void eliminar(Maestria maestria) {
        maestriaDao.delete(maestria);
    }
    
    @Override
    @Transactional
    public void actualizar(Maestria maestria) {
         
        if (maestriaDao.existsById(maestria.getIdMaestria())) {
            maestriaDao.save(maestria);
        } else {
            throw new IllegalArgumentException("La maestria especificada no existe.");
        }
    }
    
    @Override
    @Transactional(readOnly=true)
    public Maestria encontrarMaestria(Maestria maestria) {
        return maestriaDao.findById(maestria.getIdMaestria()).orElse(null);
    }
    
    @Override
    @Transactional(readOnly=true)
    public Collection<Maestria> encontrarMaestrias(Integer idUsuario) {
        return maestriaDao.findMaestriasByUsuarioId(idUsuario);
    }
    
}
