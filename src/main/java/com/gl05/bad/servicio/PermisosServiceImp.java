package com.gl05.bad.servicio;

import com.gl05.bad.dao.PermisosDao;
import com.gl05.bad.domain.Permisos;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermisosServiceImp implements PermisosService {

    @Autowired
    private PermisosDao permisoDao;

    @Override
    public List<Permisos> listaPermisos() {
       return (List<Permisos>) permisoDao.findAll();
    }

    @Override
    public void AgregarPermiso(Permisos permiso) {
        permisoDao.save(permiso);
    }
    
    
}
