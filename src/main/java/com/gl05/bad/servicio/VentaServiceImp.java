package com.gl05.bad.servicio;

import com.gl05.bad.dao.VentaDao;
import com.gl05.bad.domain.InformacionFinanciamiento;
import com.gl05.bad.domain.InformacionMantenimiento;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaServiceImp implements VentaService{
    
    @Autowired
    private VentaDao ventaDao;
    
    @Autowired
    private InformacionFinanciamientoServiceImp financiamientoService;
    
    @Autowired
    private InformacionMantenimientoServiceImp mantenimientoService;

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listaVentas() {
        return (List<Venta>) ventaDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Venta venta) {
        ventaDao.save(venta);
    }

    @Override
    @Transactional
    public void eliminar(Venta venta) {
        ventaDao.delete(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Venta encontrar(Long idVenta) {
        return ventaDao.findById(idVenta).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Venta encontrarDocumento(Integer idDocumento) {
        return ventaDao.findByIdListDocumento(idDocumento);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Venta> encontrarProyectoPrima(Proyecto proyecto) {        
        List<Long> ventasConFinanciamiento = new ArrayList<>();
        List<Venta> ventasFiltradas;
        List<InformacionFinanciamiento> infoFinanciamientoList = financiamientoService.listaInformacionFinanciamientosProyecto(proyecto);
        if (infoFinanciamientoList.isEmpty()) {
            ventasFiltradas = ventaDao.findByEstadoAndTerrenoProyecto("Activo", proyecto);
        } else {
            for (InformacionFinanciamiento infoFinanciamiento : infoFinanciamientoList) {
                ventasConFinanciamiento.add(infoFinanciamiento.getVenta().getIdVenta());
            }
            ventasFiltradas = ventaDao.findByEstadoAndTerrenoProyectoAndIdVentaNotIn("Activo", proyecto, ventasConFinanciamiento);
        }
        return ventasFiltradas;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Venta> encontrarProyectoFinanciamiento(Proyecto proyecto) {        
        List<Long> ventasConFinanciamiento = new ArrayList<>();
        List<InformacionFinanciamiento> infoFinanciamientoList = financiamientoService.listaInformacionFinanciamientosProyecto(proyecto);
        for (InformacionFinanciamiento infoFinanciamiento : infoFinanciamientoList) {
            ventasConFinanciamiento.add(infoFinanciamiento.getVenta().getIdVenta());
        }
        List<Venta> ventasFiltradas = ventaDao.findByEstadoAndTerrenoProyectoAndIdVentaIn("Activo", proyecto, ventasConFinanciamiento);
        return ventasFiltradas;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Venta> encontrarProyectoMantenimiento(Proyecto proyecto) {        
        List<Long> ventasConMantenimiento = new ArrayList<>();
        List<InformacionMantenimiento> infoMantenimientoList = mantenimientoService.listaInformacionMantenimientosProyecto(proyecto);
        for (InformacionMantenimiento infoMantenimiento : infoMantenimientoList) {
            ventasConMantenimiento.add(infoMantenimiento.getVenta().getIdVenta());
        }
        List<Venta> ventasFiltradas = ventaDao.findByEstadoAndTerrenoProyectoAndIdVentaIn("Activo", proyecto, ventasConMantenimiento);
        return ventasFiltradas;
    }
    
    @Override
    @Transactional
    public void actualizar(Venta venta) {
        if (ventaDao.existsById(venta.getIdVenta())) {
            ventaDao.save(venta);
        } else {
            throw new IllegalArgumentException("La venta no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Venta> listarVentas(DataTablesInput input, Long idTerreno) {
        Specification<Venta> specification = (root, query, builder) -> {
            return builder.equal(root.get("terreno").get("idTerreno"), idTerreno);
        };
        return ventaDao.findAll(input, specification);
    }
}
