package com.gl05.bad.dao;

import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Venta;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagoDao extends DataTablesRepository<Pago, Long> {
    @Query("SELECT p FROM Pago p WHERE p.tipo = :tipo AND p.venta = :venta ORDER BY p.fechaRegistro ASC")
    List<Pago> findByTipoAndVenta(String tipo,Venta venta);
}