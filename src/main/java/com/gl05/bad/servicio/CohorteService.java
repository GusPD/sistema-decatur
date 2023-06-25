package com.gl05.bad.servicio;

import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.Maestria;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface CohorteService {
    
    public DataTablesOutput<Cohorte> listarCohorte(DataTablesInput input);
      
   public void proAgregar(Long idMaestria, String nombreCohorte,Date fechaApertura, short estadoCohorte);
      
    public void eliminarCohorte(Cohorte cohorte);
    
    public void actualizarCohorte(Cohorte cohorte);
    
    public Cohorte encontrarCohorte(Cohorte cohorte);
    
    public DataTablesOutput<Cohorte> listarCohorteFiltrado(DataTablesInput input, Long idMaestria);
    
    public List<Cohorte> encontrarCohortesActivasPorIdMaestria(Maestria maestria, short estadoCohorte);
    
    public List<Cohorte> encontrarCohortesPorIdMaestria(Maestria maestria);
}
