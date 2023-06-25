package com.gl05.bad.servicio;

import com.gl05.bad.domain.Permisos;
import java.util.List;

public interface PermisosService {
    public List<Permisos> listaPermisos();

    public void AgregarPermiso(Permisos permiso);
}
