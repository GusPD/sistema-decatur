package com.gl05.bad.servicio;

import com.gl05.bad.domain.Permiso;
import java.util.List;

public interface PermisosService {
    public List<Permiso> listaPermisos();

    public void AgregarPermiso(Permiso permiso);
}
