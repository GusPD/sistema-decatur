package com.gl05.bad.servicio;

import com.gl05.bad.domain.Permiso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gl05.bad.dao.PermisoDao;

@Service
public class PermisosServiceImp implements PermisosService {

    @Autowired
    private PermisoDao permisoDao;

    @Override
    public List<Permiso> listaPermisos() {
       return (List<Permiso>) permisoDao.findAll();
    }

    @Override
    public void AgregarPermiso(Permiso permiso) {
        permisoDao.save(permiso);
    }
    
    
}
