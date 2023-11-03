package com.gl05.bad.servicio;

import com.gl05.bad.dao.PropietarioDao;
import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Propietario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropietarioServiceImp implements PropietarioService{
    
    @Autowired
    private PropietarioDao propietarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Propietario> listaPropietarios() {
        return (List<Propietario>) propietarioDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Propietario propietario) {
        propietarioDao.save(propietario);
    }

    @Override
    @Transactional
    public void eliminar(Propietario propietario) {
        propietarioDao.delete(propietario);
    }

    @Override
    @Transactional(readOnly = true)
    public Propietario encontrar(Long idPropietario) {
        return propietarioDao.findById(idPropietario).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Propietario encontrarPersona(Persona persona) {
        return propietarioDao.findByPersona(persona);
    }

    @Override
    @Transactional
    public void actualizar(Propietario propietario) {
        if (propietarioDao.existsById(propietario.getIdPropietario())) {
            propietarioDao.save(propietario);
        } else {
            throw new IllegalArgumentException("El propietario no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Propietario> listarPropietarios(DataTablesInput input) {
        return propietarioDao.findAll(input);
    }
}
