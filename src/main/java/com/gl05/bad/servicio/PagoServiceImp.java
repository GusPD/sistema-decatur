package com.gl05.bad.servicio;

import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Venta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public DataTablesOutput<Pago> listarPagos(DataTablesInput input, Long idProyecto, Date fechaInicio, Date fechaFin, String comprobante, Boolean estado, Integer tipoPago) {
        Specification<Pago> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("venta").get("terreno").get("proyecto").get("idProyecto"), idProyecto));
            if (fechaInicio != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("fecha"), fechaInicio));
            }
            if (fechaFin != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("fecha"), fechaFin));
            }
            if (!comprobante.isEmpty()) {
                predicates.add(builder.equal(root.get("tipo"), comprobante));
            }
            if (estado != null) {
                predicates.add(builder.equal(root.get("estado"), estado));
            }
            if (tipoPago > 0) {
                predicates.add(builder.equal(root.get("cuentaBancaria").get("idCuenta"), tipoPago));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };

        return pagoDao.findAll(input, specification);
    }
}
