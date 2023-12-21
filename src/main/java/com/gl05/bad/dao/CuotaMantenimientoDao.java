package com.gl05.bad.dao;

import com.gl05.bad.domain.CuotaMantenimiento;
import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Venta;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

public interface CuotaMantenimientoDao extends DataTablesRepository<CuotaMantenimiento, Long> {
    @Query("SELECT c FROM CuotaMantenimiento c WHERE c.pago = :pago ORDER BY c.fechaRegistro ASC")
    List<CuotaMantenimiento> findByPago(Pago pago);
    List<CuotaMantenimiento> findByPagoVenta(Venta venta);
    List<CuotaMantenimiento> findByFechaCuotaAndPagoVenta(Date fechaCuota, Venta venta);
    List<CuotaMantenimiento> findByFechaCuotaGreaterThanEqualAndPagoVenta(Date fechaCuota, Venta venta);
    @Query("SELECT cm FROM CuotaMantenimiento cm " +
       "JOIN cm.pago p " +
       "WHERE p.venta = :venta " +
       "AND (EXTRACT(YEAR FROM cm.fechaCuota) > EXTRACT(YEAR FROM CURRENT_DATE) " +
       "OR (EXTRACT(YEAR FROM cm.fechaCuota) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM cm.fechaCuota) > EXTRACT(MONTH FROM CURRENT_DATE))) " +
       "AND p.estado = :estado " +
       "ORDER BY cm.fechaRegistro ASC")
    List<CuotaMantenimiento> findByPagoEstadoAndVentaAndFechaCuotaGreaterThan(Boolean estado, Venta venta);
    void deleteByPagoVentaAndFechaRegistroGreaterThanEqual(Venta venta, LocalDateTime fechaRegistro);
    void deleteByPago(Pago pago);
    void deleteByPagoVenta(Venta venta);
}