package com.gl05.bad.dao;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Terreno;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

public interface TerrenoDao extends DataTablesRepository<Terreno, Long> {
    @Query("SELECT t FROM Terreno t WHERE t.poligono = :poligono AND t.numero = :numero AND t.seccion = :seccion AND t.proyecto = :proyecto")
    Terreno findByPoligonoAndNumeroAndSeccionAndProyecto(String poligono, Long numero, String seccion, Proyecto proyecto);
    @Query("SELECT t FROM Terreno t WHERE t.matricula = :matricula")
    Terreno findByMatricula(String matricula);
}