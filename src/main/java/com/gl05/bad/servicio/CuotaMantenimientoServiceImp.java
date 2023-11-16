package com.gl05.bad.servicio;

import com.gl05.bad.domain.CuotaMantenimiento;
import com.gl05.bad.domain.InformacionMantenimiento;
import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Venta;

import java.util.ArrayList;
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
public class CuotaMantenimientoServiceImp implements CuotaMantenimientoService{
    
    @Autowired
    private CuotaMantenimientoDao cuotaMantenimientoDao;

    @Autowired
    private PagoDao pagoDao;

    @Override
    @Transactional(readOnly = true)
    public List<CuotaMantenimiento> listaCuotaMantenimientos() {
        return (List<CuotaMantenimiento>) cuotaMantenimientoDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(CuotaMantenimiento cuotaMantenimiento) {
        cuotaMantenimientoDao.save(cuotaMantenimiento);
    }

    @Override
    @Transactional
    public void eliminar(CuotaMantenimiento cuotaMantenimiento) {
        cuotaMantenimientoDao.delete(cuotaMantenimiento);
    }

    @Override
    @Transactional
    public void eliminarInformacion(InformacionMantenimiento informacion) {
        List<CuotaMantenimiento> listaCuotas = cuotaMantenimientoDao.findByInformacion(informacion);
        int contador = 1;
        Pago pago = null;
        Pago pagoActual = null;
        for(CuotaMantenimiento cuotaMantenimiento : listaCuotas) {
            if(pago != null){
                pagoActual = cuotaMantenimiento.getPago();
                if(pago != pagoActual){
                    pagoDao.delete(pago);
                }
            }
            cuotaMantenimientoDao.deleteById(cuotaMantenimiento.getIdCuotaMantenimiento());
            pago = cuotaMantenimiento.getPago();
            if(contador == listaCuotas.size()){
                pagoDao.delete(pagoActual);
            }
            contador++;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CuotaMantenimiento encontrar(Long idCuotaMantenimiento) {
        return cuotaMantenimientoDao.findById(idCuotaMantenimiento).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public CuotaMantenimiento encontrarPenultimaCuota(Venta venta) {
        CuotaMantenimiento ultimaCuota=new CuotaMantenimiento();
        List<Pago> listaPagos = pagoDao.findByEstadoAndTipoAndVenta(true, "Mantenimiento",venta);
        if (!listaPagos.isEmpty()) {
            if(listaPagos.size()>=2){
                Pago ultimoPago = listaPagos.get(listaPagos.size() - 2);
                List<CuotaMantenimiento> listaCuotas = cuotaMantenimientoDao.findByPago(ultimoPago);
                if (!listaCuotas.isEmpty()) {
                    return ultimaCuota = listaCuotas.get(listaCuotas.size() - 1);
                } else {
                    ultimaCuota.setFechaCuota(venta.getFecha());
                    ultimaCuota.setSaldoCuota(0);
                    ultimaCuota.setSaldoRecargo(0);
                    return ultimaCuota;
                }
            }else {
                ultimaCuota.setFechaCuota(venta.getFecha());
                ultimaCuota.setSaldoCuota(0);
                ultimaCuota.setSaldoRecargo(0);
                return ultimaCuota;
            }
            
        } else {
            ultimaCuota.setFechaCuota(venta.getFecha());
            ultimaCuota.setSaldoCuota(0);
            ultimaCuota.setSaldoRecargo(0);
            return ultimaCuota;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CuotaMantenimiento encontrarUltimaCuota(Venta venta) {
        CuotaMantenimiento ultimaCuota=new CuotaMantenimiento();
        List<Pago> listaPagos = pagoDao.findByEstadoAndTipoAndVenta(true,"Mantenimiento",venta);
        if (!listaPagos.isEmpty()) {
            if(listaPagos.size()>=1){
                Pago ultimoPago = listaPagos.get(listaPagos.size() - 1);
                List<CuotaMantenimiento> listaCuotas = cuotaMantenimientoDao.findByPago(ultimoPago);
                if (!listaCuotas.isEmpty()) {
                    return ultimaCuota = listaCuotas.get(listaCuotas.size() - 1);
                } else {
                    ultimaCuota.setFechaCuota(venta.getFecha());
                    ultimaCuota.setSaldoCuota(0);
                    ultimaCuota.setSaldoRecargo(0);
                    return ultimaCuota;
                }
            }else {
                ultimaCuota.setFechaCuota(venta.getFecha());
                ultimaCuota.setSaldoCuota(0);
                ultimaCuota.setSaldoRecargo(0);
                return ultimaCuota;
            }
            
        } else {
            ultimaCuota.setFechaCuota(venta.getFecha());
            ultimaCuota.setSaldoCuota(0);
            ultimaCuota.setSaldoRecargo(0);
            return ultimaCuota;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuotaMantenimiento> encontrarPago(Pago pago) {
        return cuotaMantenimientoDao.findByPago(pago);
    }

    @Override
    @Transactional
    public void actualizar(CuotaMantenimiento cuotaMantenimiento) {
        if (cuotaMantenimientoDao.existsById(cuotaMantenimiento.getIdCuotaMantenimiento())) {
            cuotaMantenimientoDao.save(cuotaMantenimiento);
        } else {
            throw new IllegalArgumentException("La cuota mantenimiento no existe.");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<CuotaMantenimiento> listarCuotaMantenimientos(DataTablesInput input, Long idPago) {
        Specification<CuotaMantenimiento> specification = (root, query, builder) -> {
            return builder.equal(root.get("pago").get("idPago"), idPago);
        };
        return cuotaMantenimientoDao.findAll(input, specification);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<CuotaMantenimiento> listarVenta(DataTablesInput input, Long idVenta) {
        Specification<CuotaMantenimiento> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("informacion").get("venta").get("idVenta"), idVenta));
            predicates.add(builder.equal(root.get("pago").get("estado"), true));
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return cuotaMantenimientoDao.findAll(input, specification);
    }
}
