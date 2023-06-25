package com.gl05.bad.servicio;

import com.gl05.bad.dao.CorreoDao;
import com.gl05.bad.domain.Correo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CorreoServiceImp implements CorreoService{
  
    @Autowired
    private CorreoDao correoDao;

    @Override
    public List<Correo> listarCorreos() {
        return (List<Correo>) correoDao.findAll();
    }

    @Override
    @Transactional
    public void agregarC(Correo correo) {
        correoDao.save(correo);
    }
    
    @Override
    @Transactional
    public void actualizarC(Correo correo) {
        Correo correoExistente = correoDao.findById(correo.getIdCorreo()).orElse(null);
        correoDao.save(correoExistente);
    }

    @Override
    public void eliminarC(Correo correo) {
        correoDao.delete(correo);
    }

    @Override
    public Correo encontrarC(Correo correo) {
        return correoDao.findById(correo.getIdCorreo()).orElse(null);
    }
}
