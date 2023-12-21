package com.gl05.bad.servicio;

import com.gl05.bad.dao.MunicipioDao;
import com.gl05.bad.domain.Departamento;
import com.gl05.bad.domain.Municipio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MunicipioServiceImp implements MunicipioService{
  
    @Autowired
    private MunicipioDao municipioDao;

    @Override
    public List<Municipio> listarMunicipios() {
        return (List<Municipio>) municipioDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Municipio municipio) {
        municipioDao.save(municipio);
    }
    
    @Override
    @Transactional
    public void actualizar(Municipio municipio) {
        Municipio municipioExistente = municipioDao.findById(municipio.getIdMunicipio()).orElse(null);
        municipioDao.save(municipioExistente);
    }

    @Override
    public void eliminar(Municipio municipio) {
        municipioDao.delete(municipio);
    }

    @Override
    public Municipio encontrar(Municipio municipio) {
        return municipioDao.findById(municipio.getIdMunicipio()).orElse(null);
    }

    @Override
    public List<Municipio> encontrarDepartamento(Departamento  departamento) {
        return municipioDao.findByDepartamento(departamento);
    }
}
