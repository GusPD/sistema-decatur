package com.gl05.bad.servicio;

import com.gl05.bad.dao.PropietarioDao;
import com.gl05.bad.dao.PersonaDao;
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
    
    @Autowired
    private PersonaDao personaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Propietario> listaPropietarios() {
        return (List<Propietario>) propietarioDao.findAll();
    }

    @Override
    @Transactional
    public void agregarPropietario(Propietario propietario) {
        propietarioDao.save(propietario);
    }

    @Override
    @Transactional
    public void eliminarPropietario(Propietario propietario) {
        propietarioDao.delete(propietario);
    }

    @Override
    @Transactional(readOnly = true)
    public Propietario encontrarPropietario(Long idPropietario) {
        return propietarioDao.findById(idPropietario).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Propietario encontrarPropietarioPersona(Persona persona) {
        return propietarioDao.findByPersona(persona);
    }

    @Override
    @Transactional
    public void actualizarPropietario(Propietario propietario) {
        if (propietarioDao.existsById(propietario.getIdPropietario())) {
            propietarioDao.save(propietario);
        } else {
            throw new IllegalArgumentException("El propietario no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Persona> listarPropietarios(DataTablesInput input) {
        return personaDao.findAll(input);
    }
}
