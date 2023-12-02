package com.gl05.bad.dao;

import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagoDao extends DataTablesRepository<Pago, Long> {
    @Query("SELECT p FROM Pago p WHERE p.estado = :estado AND p.tipo = :tipo AND p.venta = :venta AND p.monto > p.otros ORDER BY p.fechaRegistro ASC")
    List<Pago> findByEstadoAndTipoAndVenta(Boolean estado, String tipo,Venta venta);
    Pago findByTipoAndReciboAndVentaTerrenoProyectoAndComprobante(String tipo,Integer recibo, Proyecto proyecto, String comprobante);
    void deleteByTipoAndVentaAndFechaRegistroGreaterThanEqual(String tipo, Venta venta, LocalDateTime fechaRegistro);
    List<Pago> findByTipoAndVentaAndFechaRegistroAfter(String tipo, Venta venta, LocalDateTime fechaRegistro);
    void deleteByTipoAndVenta(String tipo, Venta venta);
}