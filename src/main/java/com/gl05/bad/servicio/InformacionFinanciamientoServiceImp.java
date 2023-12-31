package com.gl05.bad.servicio;

import com.gl05.bad.dao.InformacionFinanciamientoDao;
import com.gl05.bad.domain.InformacionFinanciamiento;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InformacionFinanciamientoServiceImp implements InformacionFinanciamientoService{
    
    @Autowired
    private InformacionFinanciamientoDao informacionFinanciamientoDao;

    @Override
    @Transactional(readOnly = true)
    public List<InformacionFinanciamiento> listaInformacionFinanciamientos() {
        return (List<InformacionFinanciamiento>) informacionFinanciamientoDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InformacionFinanciamiento> listaInformacionFinanciamientosProyecto(Proyecto proyecto) {
        return (List<InformacionFinanciamiento>) informacionFinanciamientoDao.findByVentaTerrenoProyecto(proyecto);
    }

    @Override
    @Transactional
    public void agregar(InformacionFinanciamiento informacionFinanciamiento) {
        informacionFinanciamientoDao.save(informacionFinanciamiento);
    }

    @Override
    @Transactional
    public void eliminar(InformacionFinanciamiento informacionFinanciamiento) {
        informacionFinanciamientoDao.delete(informacionFinanciamiento);
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionFinanciamiento encontrar(Long idInformacionFinanciamiento) {
        return informacionFinanciamientoDao.findById(idInformacionFinanciamiento).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InformacionFinanciamiento> encontrarVenta(Venta venta) {
        return informacionFinanciamientoDao.findByVenta(venta);
    }

    @Override
    @Transactional
    public void actualizar(InformacionFinanciamiento InformacionFinanciamiento) {
        if (informacionFinanciamientoDao.existsById(InformacionFinanciamiento.getIdAsignacion())) {
            informacionFinanciamientoDao.save(InformacionFinanciamiento);
        } else {
            throw new IllegalArgumentException("La información financiamiento no existe.");
        }
    }
}
