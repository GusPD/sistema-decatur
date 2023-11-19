package com.gl05.bad.servicio;

import com.gl05.bad.domain.TipoDocumento;
import java.util.List;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface TipoDocumentoService {
    
    public List<TipoDocumento> listaTipoDocumentos();
    
    public DataTablesOutput<TipoDocumento> listarTipoDocumentos(DataTablesInput input);
   
    public void agregar(TipoDocumento tipoDocumento);
        
    public void eliminar(TipoDocumento tipoDocumento);
    
    public TipoDocumento encontrar(Long idTipoDocumento);
    
    public void actualizar(TipoDocumento tipoDocumento);
}
