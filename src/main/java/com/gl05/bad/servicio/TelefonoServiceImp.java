package com.gl05.bad.servicio;

import com.gl05.bad.dao.TelefonoDao;
import com.gl05.bad.domain.Telefono;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TelefonoServiceImp implements TelefonoService{
  
    @Autowired
    private TelefonoDao telefonoDao;

    @Override
    public List<Telefono> listarTelefonos() {
        return (List<Telefono>) telefonoDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Telefono telefono) {
        telefonoDao.save(telefono);
    }
    
    @Override
    @Transactional
    public void actualizar(Telefono telefono) {
        Telefono telefonoExistente = telefonoDao.findById(telefono.getIdTelefono()).orElse(null);
        telefonoDao.save(telefonoExistente);
    }

    @Override
    public void eliminar(Telefono telefono) {
        telefonoDao.delete(telefono);
    }

    @Override
    public Telefono encontrar(Telefono telefono) {
        return telefonoDao.findById(telefono.getIdTelefono()).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Telefono encontrarTelefono(String telefono) {
        return telefonoDao.findByTelefono(telefono);
    }
}
