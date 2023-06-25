
package com.gl05.bad.servicio;

import com.gl05.bad.dao.EscuelaPostgradoDao;
import com.gl05.bad.domain.EscuelaPostgrado;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EscuelaPostgradoServiceImp implements EscuelaPostgradoService{

    @Autowired
    private EscuelaPostgradoDao escuelaPostgradoDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<EscuelaPostgrado> listarEscuelaPostgrado() {
        return(List<EscuelaPostgrado>) escuelaPostgradoDao.findAll();
    }

    @Override
    @Transactional
    public void agregarEscuela(EscuelaPostgrado escuela) {
        escuelaPostgradoDao.save(escuela);
    }

    @Override
    @Transactional
    public void eliminarEscuela(EscuelaPostgrado escuela) {
       escuelaPostgradoDao.delete(escuela);
    }

    @Override
    @Transactional(readOnly=true)
    public EscuelaPostgrado encontrarEscuela(Long escuela) {
        return escuelaPostgradoDao.findById(escuela).orElse(null);
    }

    @Override
    @Transactional
    public void actualizarEscuela(EscuelaPostgrado escuela) {
         
        if (escuelaPostgradoDao.existsById(escuela.getIdPostgrado())) {
            escuelaPostgradoDao.save(escuela);
        } else {
            throw new IllegalArgumentException("La escuela de posgrado especificada no existe.");
        }
    }

    @Override
    public DataTablesOutput<EscuelaPostgrado> listarEscuelasDePosgrados(DataTablesInput input) {
        return (DataTablesOutput<EscuelaPostgrado>)escuelaPostgradoDao.findAll(input);
    }
    
}
