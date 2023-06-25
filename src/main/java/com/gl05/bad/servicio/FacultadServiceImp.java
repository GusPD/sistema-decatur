
package com.gl05.bad.servicio;

import com.gl05.bad.dao.FacultadDao;
import com.gl05.bad.domain.Facultad;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacultadServiceImp implements FacultadService{

    @Autowired
    private FacultadDao facultadDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<Facultad> listarFacultad() {
        return (List<Facultad>) facultadDao.findAll();
    }

    @Override
    @Transactional
    public void agregarFacultad(Facultad facultad) {
        facultadDao.save(facultad);
    }

    @Override
    @Transactional
    public void eliminarFacultad(Facultad facultad) {
       facultadDao.delete(facultad);
    }

    @Override
    @Transactional(readOnly=true)
    public Facultad encontrarFacultad(Long facultad) {
      return facultadDao.findById(facultad).orElse(null);
    }

    @Override
    @Transactional
    public void actualizarFacultad(Facultad facultad) {
        
        if (facultadDao.existsById(facultad.getIdFacultad())) {
            facultadDao.save(facultad);
        } else {
            throw new IllegalArgumentException("La facultad especificada no existe.");
        }
    }

    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<Facultad> listarFacultades(DataTablesInput input) {
        return (DataTablesOutput<Facultad>)facultadDao.findAll(input); 
    }
    
}
