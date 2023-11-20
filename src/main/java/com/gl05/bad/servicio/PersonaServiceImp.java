package com.gl05.bad.servicio;

import com.gl05.bad.dao.PersonaDao;
import com.gl05.bad.domain.Persona;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonaServiceImp implements PersonaService{
    
    @Autowired
    private PersonaDao personaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> listaPersonas() {
        return (List<Persona>) personaDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Persona persona) {
        personaDao.save(persona);
    }

    @Override
    @Transactional
    public void eliminar(Persona persona) {
        personaDao.delete(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona encontrar(Long idPersona) {
        return personaDao.findById(idPersona).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Persona encontrarNumero(String numero) {
        return personaDao.findByNumero(numero);
    }

    @Override
    @Transactional
    public void actualizar(Persona persona) {
        if (personaDao.existsById(persona.getIdPersona())) {
            personaDao.save(persona);
        } else {
            throw new IllegalArgumentException("La persona no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Persona> listarPersonas(DataTablesInput input) {
        return (DataTablesOutput<Persona>) personaDao.findAll(input);
    }
}
