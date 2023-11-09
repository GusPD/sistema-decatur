package com.gl05.bad.servicio;

import com.gl05.bad.domain.AsignacionVisitante;
import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Venta;

import java.util.List;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gl05.bad.dao.PagoDao;

@Service
public class PagoServiceImp implements PagoService{
    
    @Autowired
    private PagoDao pagoDao;

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
        pagoDao.delete(pago);
    }

    @Override
    @Transactional(readOnly = true)
    public Pago encontrar(Long idPago) {
        return pagoDao.findById(idPago).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pago> encontrarPago(String tipo,Venta venta) {
        return pagoDao.findByTipoAndVenta(tipo, venta);
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
    public DataTablesOutput<Pago> listarPagos(DataTablesInput input, Long idProyecto, String fechaInicio, String fechaFin, String comprobante, String estado, String tipoPago) {
         Specification<Pago> specification = null;
        if(fechaInicio.equals("undefined") && fechaFin.equals("undefined") && comprobante.equals("undefined") && estado.equals("undefined") && tipoPago.equals("undefined"))
        {
            specification = (root, query, builder) -> {
                return builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
            }; 
        }else if(fechaInicio.equals("undefined") && fechaFin.equals("undefined") && !comprobante.equals("undefined") && estado.equals("undefined") && tipoPago.equals("undefined")){
            specification = (root, query, builder) -> {
                Predicate proyecto = builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
                Predicate p_comprobante = builder.greaterThanOrEqualTo(root.get("tipo"), comprobante);
                return builder.and(proyecto, p_comprobante);
            };
        }else if(fechaInicio.equals("undefined") && fechaFin.equals("undefined") && comprobante.equals("undefined") && !estado.equals("undefined") && tipoPago.equals("undefined")){
            specification = (root, query, builder) -> {
                Predicate proyecto = builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
                Predicate p_estado = builder.greaterThanOrEqualTo(root.get("estado"), estado);
                return builder.and(proyecto, p_estado);
            };
        }else if(fechaInicio.equals("undefined") && fechaFin.equals("undefined") && comprobante.equals("undefined") && estado.equals("undefined") && !tipoPago.equals("undefined")){
            specification = (root, query, builder) -> {
                Predicate proyecto = builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
                Predicate p_cuenta = builder.greaterThanOrEqualTo(root.get("cuentaBancaria").get("idCuenta"), tipoPago);
                return builder.and(proyecto, p_cuenta);
            };
        }else if(!fechaInicio.equals("undefined") && fechaFin.equals("undefined") && comprobante.equals("undefined") && estado.equals("undefined") && tipoPago.equals("undefined")){
            specification = (root, query, builder) -> {
                Predicate proyecto = builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
                Predicate fecha_inicio = builder.greaterThanOrEqualTo(root.get("fecha"), fechaInicio);
                return builder.and(proyecto, fecha_inicio);
            };
        }else if(!fechaInicio.equals("undefined") && !fechaFin.equals("undefined") && comprobante.equals("undefined") && estado.equals("undefined") && tipoPago.equals("undefined")){
            specification = (root, query, builder) -> {
                Predicate proyecto = builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
                Predicate fecha_inicio = builder.greaterThanOrEqualTo(root.get("fecha"), fechaInicio);
                Predicate fecha_fin = builder.lessThanOrEqualTo(root.get("fecha"), fechaFin);
                return builder.and(proyecto, fecha_inicio, fecha_fin);
            };
        }else if(!fechaInicio.equals("undefined") && !fechaFin.equals("undefined") && !comprobante.equals("undefined") && estado.equals("undefined") && tipoPago.equals("undefined")){
            specification = (root, query, builder) -> {
                Predicate proyecto = builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
                Predicate fecha_inicio = builder.greaterThanOrEqualTo(root.get("fecha"), fechaInicio);
                Predicate fecha_fin = builder.lessThanOrEqualTo(root.get("fecha"), fechaFin);
                Predicate p_comprobante = builder.lessThanOrEqualTo(root.get("tipo"), comprobante);
                return builder.and(proyecto, fecha_inicio, fecha_fin, p_comprobante);
            };
        }else if(!fechaInicio.equals("undefined") && !fechaFin.equals("undefined") && !comprobante.equals("undefined") && !estado.equals("undefined") && tipoPago.equals("undefined")){
            specification = (root, query, builder) -> {
                Predicate proyecto = builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
                Predicate fecha_inicio = builder.greaterThanOrEqualTo(root.get("fecha"), fechaInicio);
                Predicate fecha_fin = builder.lessThanOrEqualTo(root.get("fecha"), fechaFin);
                Predicate p_comprobante = builder.lessThanOrEqualTo(root.get("tipo"), comprobante);
                Predicate p_estado = builder.lessThanOrEqualTo(root.get("estado"), estado);
                return builder.and(proyecto, fecha_inicio, fecha_fin, p_comprobante, p_estado);
            };
        }else if(!fechaInicio.equals("undefined") && !fechaFin.equals("undefined") && !comprobante.equals("undefined") && !estado.equals("undefined") && !tipoPago.equals("undefined")){
            specification = (root, query, builder) -> {
                Predicate proyecto = builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
                Predicate fecha_inicio = builder.greaterThanOrEqualTo(root.get("fecha"), fechaInicio);
                Predicate fecha_fin = builder.lessThanOrEqualTo(root.get("fecha"), fechaFin);
                Predicate p_comprobante = builder.lessThanOrEqualTo(root.get("tipo"), comprobante);
                Predicate p_estado = builder.lessThanOrEqualTo(root.get("estado"), estado);
                Predicate p_cuenta = builder.lessThanOrEqualTo(root.get("cuentaBancaria").get("idCuenta"), tipoPago);
                return builder.and(proyecto, fecha_inicio, fecha_fin, p_comprobante, p_estado, p_cuenta);
            };
        }else if(fechaInicio.equals("undefined") && fechaFin.equals("undefined") && !comprobante.equals("") && !estado.equals("undefined") && !tipoPago.equals("undefined")){
            specification = (root, query, builder) -> {
                Predicate proyecto = builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
                Predicate p_comprobante = builder.lessThanOrEqualTo(root.get("tipo"), comprobante);
                Predicate p_estado = builder.lessThanOrEqualTo(root.get("estado"), estado);
                Predicate p_cuenta = builder.lessThanOrEqualTo(root.get("cuentaBancaria").get("idCuenta"), tipoPago);
                return builder.and(proyecto, p_comprobante, p_estado, p_cuenta);
            };
        }else if(fechaInicio.equals("undefined") && fechaFin.equals("undefined") && comprobante.equals("undefined") && !estado.equals("undefined") && !tipoPago.equals("undefined")){
            specification = (root, query, builder) -> {
                Predicate proyecto = builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto);
                Predicate p_estado = builder.lessThanOrEqualTo(root.get("estado"), estado);
                Predicate p_cuenta = builder.lessThanOrEqualTo(root.get("cuentaBancaria").get("idCuenta"), tipoPago);
                return builder.and(proyecto, p_estado, p_cuenta);
            };
        }   
        return pagoDao.findAll(input, specification);
    }
}
