package com.gl05.bad.servicio;

import com.gl05.bad.dao.RolesDao;
import com.gl05.bad.domain.Roles;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolesServiceImp implements RolesService {

    @Autowired
    private RolesDao rolDao;

    @Override
    @Transactional(readOnly=true)
    public List<Roles> listaRoles() {
        return (List<Roles>) rolDao.findAll();
    }

    @Override
    @Transactional
    public void AgregarRol(Roles rol) {
        rolDao.save(rol);
    }

    @Override
    @Transactional
    public void eliminarRol(Roles rol) {
        rolDao.delete(rol);
    }

    @Override
    @Transactional(readOnly=true)
    public Roles encontrarRol(Long rol) {
        return rolDao.findById(rol).orElse(null);
    }

    @Override
    @Transactional
    public void actualizarRol(Roles rol) {
//        System.out.println(1);
        // Verifica si el rol existe en la base de datos
        if (rolDao.existsById(rol.getIdRol())) {
            // Actualiza el rol en la base de datos
            rolDao.save(rol);
        } else {
            throw new IllegalArgumentException("El rol no existe.");
        }
    }

    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<Roles> listarRoles(DataTablesInput input) {
        return (DataTablesOutput<Roles>)rolDao.findAll(input);
    }
    
}
