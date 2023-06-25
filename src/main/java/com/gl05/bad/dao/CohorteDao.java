package com.gl05.bad.dao;

import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.Maestria;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface CohorteDao extends DataTablesRepository<Cohorte, Long>{
    @Procedure(name = "sp_insert_cohorte")
    void sp_insert_cohorte(
        @Param("p_id_maestria") Long idMaestria,
        @Param("p_nombre_cohorte") String nombreCohorte,
        @Param("p_fecha_apertura") Date fechaApertura,
        @Param("p_estado_cohorte") short estadoCohorte
    );
    
    List<Cohorte> findByEstadoCohorteAndIdMaestria(short estadoCohorte, Maestria idMaestria);
    
    List<Cohorte> findByIdMaestria(Maestria idMaestria);
}
