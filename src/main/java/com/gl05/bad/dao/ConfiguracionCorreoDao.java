package com.gl05.bad.dao;

import com.gl05.bad.domain.ConfiguracionCorreo;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface ConfiguracionCorreoDao extends DataTablesRepository<ConfiguracionCorreo, Long> {
    ConfiguracionCorreo findFirstByServidorAndVerificado(Boolean servidor, Boolean verificado);
    List<ConfiguracionCorreo> findAllByServidorNotAndVerificado(Boolean servidor, Boolean verificado);
}