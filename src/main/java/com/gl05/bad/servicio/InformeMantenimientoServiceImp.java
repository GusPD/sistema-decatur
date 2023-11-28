package com.gl05.bad.servicio;

import com.gl05.bad.dao.InformeMantenimientoDao;
import com.gl05.bad.domain.InformeMantenimiento;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InformeMantenimientoServiceImp implements InformeMantenimientoService{
    
    @Autowired
    private InformeMantenimientoDao informeMantenimientoDao;

    @Override
    @Transactional(readOnly = true)
    public List<InformeMantenimiento> listaInformeMantenimientos() {
        return (List<InformeMantenimiento>) informeMantenimientoDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InformeMantenimiento> listaInformeMantenimientosProyecto(Proyecto proyecto) {
        return (List<InformeMantenimiento>) informeMantenimientoDao.findByVentaTerrenoProyecto(proyecto);
    }

    @Override
    @Transactional
    public void agregar(InformeMantenimiento informeMantenimiento) {
        informeMantenimientoDao.save(informeMantenimiento);
    }

    @Override
    @Transactional
    public void eliminar(InformeMantenimiento informeMantenimiento) {
        informeMantenimientoDao.delete(informeMantenimiento);
    }

    @Override
    @Transactional
    public void eliminarProyecto(Proyecto proyecto) {
        informeMantenimientoDao.deleteByVentaTerrenoProyecto(proyecto);
    }

    @Override
    @Transactional(readOnly = true)
    public InformeMantenimiento encontrar(Long idInformeMantenimiento) {
        return informeMantenimientoDao.findById(idInformeMantenimiento).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public InformeMantenimiento encontrarVenta(Venta venta) {
        return informeMantenimientoDao.findByVenta(venta);
    }

    @Override
    @Transactional
    public void actualizar(InformeMantenimiento informeMantenimiento) {
        if (informeMantenimientoDao.existsById(informeMantenimiento.getIdInforme())) {
            informeMantenimientoDao.save(informeMantenimiento);
        } else {
            throw new IllegalArgumentException("El informe del mantenimiento no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<InformeMantenimiento> listarInforme(DataTablesInput input, Long idProyecto) {
        Specification<InformeMantenimiento> specification = (root, query, builder) -> {
            return builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
        };
        return informeMantenimientoDao.findAll(input, specification);
    } 
}
