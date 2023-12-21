package com.gl05.bad.servicio;

import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gl05.bad.dao.CuotaMantenimientoDao;
import com.gl05.bad.dao.PagoDao;

@Service
public class PagoServiceImp implements PagoService{
    
    @Autowired
    private PagoDao pagoDao;

    @Autowired
    private CuotaMantenimientoDao cuotaMantenimientoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Pago> listaPagos() {
        return (List<Pago>) pagoDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Pago pago) {
        pagoDao.save(pago);
    }

    @Override
    @Transactional
    public void eliminar(Pago pago) {
        if(pago.getTipo().equals("Prima")){
            pagoDao.delete(pago);
        }else if(pago.getTipo().equals("Mantenimiento")){
            cuotaMantenimientoDao.deleteByPagoVentaAndFechaRegistroGreaterThanEqual(pago.getVenta(), pago.getFechaRegistro());
            pagoDao.deleteByTipoAndVentaAndFechaRegistroGreaterThanEqual("Mantenimiento",pago.getVenta(), pago.getFechaRegistro());
        }
    }

    @Override
    @Transactional
    public void eliminarVenta(String tipo, Venta venta) {
        if(tipo.equals("Prima")){
            pagoDao.deleteByTipoAndVenta(tipo, venta);
        }else if(tipo.equals("Mantenimiento")){
            cuotaMantenimientoDao.deleteByPagoVenta(venta);
            pagoDao.deleteByTipoAndVenta(tipo, venta);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Pago encontrar(Long idPago) {
        return pagoDao.findById(idPago).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pago> encontrarPago(Boolean estado, String tipo, String comprobante, Venta venta) {
        return pagoDao.findByEstadoAndTipoAndVentaAndComprobanteNotEqual(estado, tipo, venta, comprobante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pago> encontrarPago(Boolean estado, String tipo, Venta venta) {
        return pagoDao.findByEstadoAndTipoAndVenta(estado, tipo, venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Pago encontrarUlitmoPago(Boolean estado, String tipo, Venta venta) {
        List<Pago> listaPagos = pagoDao.findByEstadoAndTipoAndVenta(estado, tipo, venta);
        Pago pago = listaPagos.get(listaPagos.size()-1);
        return pago;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pago> encontrarMayores(String tipo,Venta venta, LocalDateTime fechaRegistro){
        return pagoDao.findByTipoAndVentaAndFechaRegistroAfter(tipo, venta, fechaRegistro);
    }

    @Override
    @Transactional(readOnly = true)
    public Pago encontrarRecibo(String tipo,Integer recibo, Proyecto proyecto, String comprobante) {
        return pagoDao.findByTipoAndReciboAndVentaTerrenoProyectoAndComprobante(tipo, recibo, proyecto, comprobante);
    }

    @Override
    @Transactional
    public void actualizar(Pago pago) {
        if (pagoDao.existsById(pago.getIdPago())) {
            pagoDao.save(pago);
        } else {
            throw new IllegalArgumentException("El pago no existe.");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Pago> listarPagos(DataTablesInput input, Long idProyecto, Date fechaInicio, Date fechaFin, String tipo, Boolean estado, Integer cuenta, String comprobante) {
        Specification<Pago> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto));
            if (fechaInicio != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("fecha"), fechaInicio));
            }
            if (fechaFin != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("fecha"), fechaFin));
            }
            if (!tipo.isEmpty()) {
                predicates.add(builder.equal(root.get("tipo"), tipo));
            }
            if (estado != null) {
                predicates.add(builder.equal(root.get("estado"), estado));
            }
            if (cuenta > 0) {
                predicates.add(builder.equal(root.get("cuentaBancaria").get("idCuenta"), cuenta));
            }
            if (!comprobante.isEmpty()) {
                predicates.add(builder.equal(root.get("comprobante"), comprobante));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return pagoDao.findAll(input, specification);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Pago> listarPagosVenta(DataTablesInput input, Long idVenta) {
        Specification<Pago> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("venta").get("idVenta"), idVenta));
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return pagoDao.findAll(input, specification);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Pago> listarPrimaVenta(DataTablesInput input, Long idVenta) {
        Specification<Pago> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("venta").get("idVenta"), idVenta));
            predicates.add(builder.equal(root.get("tipo"), "Prima"));
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return pagoDao.findAll(input, specification);
    }
}
