package com.gl05.bad.servicio;

import com.gl05.bad.dao.DepartamentoDao;
import com.gl05.bad.domain.Departamento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartamentoServiceImp implements DepartamentoService{
  
    @Autowired
    private DepartamentoDao departamentoDao;

    @Override
    public List<Departamento> listarDepartamentos() {
        return (List<Departamento>) departamentoDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Departamento departamento) {
        departamentoDao.save(departamento);
    }
    
    @Override
    @Transactional
    public void actualizar(Departamento departamento) {
        Departamento departamentoExistente = departamentoDao.findById(departamento.getIdDepartamento()).orElse(null);
        departamentoDao.save(departamentoExistente);
    }

    @Override
    public void eliminar(Departamento departamento) {
        departamentoDao.delete(departamento);
    }

    @Override
    public Departamento encontrar(Departamento departamento) {
        return departamentoDao.findById(departamento.getIdDepartamento()).orElse(null);
    }
}
