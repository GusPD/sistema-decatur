package com.gl05.bad.servicio;

import com.gl05.bad.dao.AreaConocimientoDao;
import com.gl05.bad.domain.AreaConocimiento;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AreaConocimientoImp implements AreaConocimientoService{

    @Autowired
    private AreaConocimientoDao areaConocimientoDao;
    
    @Override
    @Transactional(readOnly=true)
    public Collection<AreaConocimiento> listarAreaConocimientos() {
        return (Collection<AreaConocimiento>)areaConocimientoDao.findAll();
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<AreaConocimiento> listarAreaConocimientos(DataTablesInput input) {
       return (DataTablesOutput<AreaConocimiento>)areaConocimientoDao.findAll(input);
    }

    @Override
    public void agregarAC(AreaConocimiento areaConocimiento) {
        areaConocimientoDao.save(areaConocimiento);
    }

    @Override
    public void eliminarAC(AreaConocimiento areaConocimiento) {
        areaConocimientoDao.delete(areaConocimiento);
    }

    @Override
    public AreaConocimiento encontrarAC(Long areaConocimiento) {
        return areaConocimientoDao.findById(areaConocimiento).orElse(null);
    }
    @Override
    public void actualizarAC(AreaConocimiento areaConocimiento) {
        // Verifica si el área de conocimiento existe en la base de datos
        if (areaConocimientoDao.existsById(areaConocimiento.getIdAreaConocimiento())) {
            // Actualiza el área de conocimiento en la base de datos
            areaConocimientoDao.save(areaConocimiento);
        } else {
            // El área de conocimiento no existe, puedes lanzar una excepción o manejar el caso según tus necesidades
            throw new IllegalArgumentException("El área de conocimiento especificada no existe.");
        }
    }
    
}
