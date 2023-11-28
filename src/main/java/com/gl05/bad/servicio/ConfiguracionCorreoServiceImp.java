package com.gl05.bad.servicio;

import com.gl05.bad.dao.ConfiguracionCorreoDao;
import com.gl05.bad.domain.ConfiguracionCorreo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfiguracionCorreoServiceImp implements ConfiguracionCorreoService{
  
    @Autowired
    private ConfiguracionCorreoDao correoDao;

    @Override
    public List<ConfiguracionCorreo> listarConfiguracionesCorreo() {
        return (List<ConfiguracionCorreo>) correoDao.findAll();
    }

    @Override
    public List<ConfiguracionCorreo> listarCorreosEnvio() {
        return (List<ConfiguracionCorreo>) correoDao.findAllByServidorNotAndVerificado(true, true);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<ConfiguracionCorreo> listarConfiguracionesCorreo(DataTablesInput input) {
        return correoDao.findAll(input);
    }

    @Override
    public ConfiguracionCorreo obtenerConfiguracionCorreo() {
        return correoDao.findFirstByServidorAndVerificado(true, true);
    }

    @Override
    @Transactional(readOnly = true)
    public ConfiguracionCorreo encontrar(Long idConfiguracion) {
        return correoDao.findById(idConfiguracion).orElse(null);
    }

    @Override
    @Transactional
    public void agregar(ConfiguracionCorreo configuracionCorreo) {
        correoDao.save(configuracionCorreo);
    }

    @Override
    @Transactional
    public void eliminar(ConfiguracionCorreo configuracionCorreo) {
        correoDao.delete(configuracionCorreo);
    }

    @Override
    @Transactional
    public void actualizar(ConfiguracionCorreo configuracionCorreo) {
        if (correoDao.existsById(configuracionCorreo.getIdConfiguracion())) {
            correoDao.save(configuracionCorreo);
        } else {
            throw new IllegalArgumentException("La configuración de correo no existe.");
        }
    }
}
