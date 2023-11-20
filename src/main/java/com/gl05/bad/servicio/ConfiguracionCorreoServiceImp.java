package com.gl05.bad.servicio;

import com.gl05.bad.dao.ConfiguracionCorreoDao;
import com.gl05.bad.domain.ConfiguracionCorreo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return (List<ConfiguracionCorreo>) correoDao.findAllByServidorNot(true);
    }

    @Override
    public ConfiguracionCorreo obtenerConfiguracionCorreo() {
        return correoDao.findFirstByServidor(true);
    }
}
