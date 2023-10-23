package com.gl05.bad.servicio;

import com.gl05.bad.dao.InformacionMantenimientoDao;
import com.gl05.bad.domain.InformacionMantenimiento;
import com.gl05.bad.domain.Venta;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InformacionMantenimientoServiceImp implements InformacionMantenimientoService{
    
    @Autowired
    private InformacionMantenimientoDao informacionMantenimientoDao;

    @Override
    @Transactional(readOnly = true)
    public List<InformacionMantenimiento> listaInformacionMantenimientos() {
        return (List<InformacionMantenimiento>) informacionMantenimientoDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(InformacionMantenimiento informacionMantenimiento) {
        informacionMantenimientoDao.save(informacionMantenimiento);
    }

    @Override
    @Transactional
    public void eliminar(InformacionMantenimiento informacionMantenimiento) {
        informacionMantenimientoDao.delete(informacionMantenimiento);
    }

    @Override
    @Transactional(readOnly = true)
    public InformacionMantenimiento encontrar(Long idInformacionMantenimiento) {
        return informacionMantenimientoDao.findById(idInformacionMantenimiento).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InformacionMantenimiento> encontrarVenta(Venta venta) {
        return informacionMantenimientoDao.findByVenta(venta);
    }

    @Override
    @Transactional
    public void actualizar(InformacionMantenimiento informacionMantenimiento) {
        if (informacionMantenimientoDao.existsById(informacionMantenimiento.getIdAsignacion())) {
            informacionMantenimientoDao.save(informacionMantenimiento);
        } else {
            throw new IllegalArgumentException("La información del mantenimiento no existe.");
        }
    }
}
