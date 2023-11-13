package com.gl05.bad.dao;

import com.gl05.bad.domain.CuotaMantenimiento;
import com.gl05.bad.domain.Pago;

import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

public interface CuotaMantenimientoDao extends DataTablesRepository<CuotaMantenimiento, Long> {
    @Query("SELECT c FROM CuotaMantenimiento c WHERE c.pago = :pago ORDER BY c.fechaRegistro ASC")
    List<CuotaMantenimiento> findByPago(Pago pago);
    void deleteByPago(Pago pago);
}