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
    public void agregarT(Telefono telefono) {
        telefonoDao.save(telefono);
    }
    
    @Override
    @Transactional
    public void actualizarT(Telefono telefono) {
        Telefono telefonoExistente = telefonoDao.findById(telefono.getIdTelefono()).orElse(null);
        telefonoDao.save(telefonoExistente);
    }

    @Override
    public void eliminarT(Telefono telefono) {
        telefonoDao.delete(telefono);
    }

    @Override
    public Telefono encontrarT(Telefono telefono) {
        return telefonoDao.findById(telefono.getIdTelefono()).orElse(null);
    }
}
