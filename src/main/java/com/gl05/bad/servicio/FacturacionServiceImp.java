package com.gl05.bad.servicio;

import com.gl05.bad.dao.FacturacionDao;
import com.gl05.bad.domain.Facturacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturacionServiceImp implements FacturacionService{
    
    @Autowired
    private FacturacionDao facturacionDao;

    @Override
    @Transactional(readOnly = true)
    public List<Facturacion> listaFacturaciones() {
        return (List<Facturacion>) facturacionDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Facturacion facturacion) {
        facturacionDao.save(facturacion);
    }

    @Override
    @Transactional
    public void eliminar(Facturacion facturacion) {
        facturacionDao.delete(facturacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Facturacion encontrar(Long idFacturacion) {
        return facturacionDao.findById(idFacturacion).orElse(null);
    }

    @Override
    @Transactional
    public void actualizar(Facturacion facturacion) {
        if (facturacionDao.existsById(facturacion.getIdFacturacion())) {
            facturacionDao.save(facturacion);
        } else {
            throw new IllegalArgumentException("La facturacion no existe.");
        }
    }
}
