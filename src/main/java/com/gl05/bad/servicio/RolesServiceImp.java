package com.gl05.bad.servicio;

import com.gl05.bad.domain.Rol;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gl05.bad.dao.RolDao;

@Service
public class RolesServiceImp implements RolesService {

    @Autowired
    private RolDao rolDao;

    @Override
    @Transactional(readOnly=true)
    public List<Rol> listaRoles() {
        return (List<Rol>) rolDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Rol rol) {
        rolDao.save(rol);
    }

    @Override
    @Transactional
    public void eliminar(Rol rol) {
        rolDao.delete(rol);
    }

    @Override
    @Transactional(readOnly=true)
    public Rol encontrar(Long rol) {
        return rolDao.findById(rol).orElse(null);
    }

    @Override
    @Transactional
    public void actualizar(Rol rol) {
        if (rolDao.existsById(rol.getIdRol())) {
            rolDao.save(rol);
        } else {
            throw new IllegalArgumentException("El rol no existe.");
        }
    }

    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<Rol> listarRoles(DataTablesInput input) {
        return (DataTablesOutput<Rol>)rolDao.findAll(input);
    }
}
