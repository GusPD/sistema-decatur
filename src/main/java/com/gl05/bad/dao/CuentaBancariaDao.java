package com.gl05.bad.dao;

import com.gl05.bad.domain.CuentaBancaria;
import com.gl05.bad.domain.Proyecto;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface CuentaBancariaDao extends DataTablesRepository<CuentaBancaria, Long> {
    List<CuentaBancaria> findByProyecto (Proyecto proyecto);
}