package com.gl05.bad.servicio;

import com.gl05.bad.dao.TipoDocumentoDao;
import com.gl05.bad.domain.TipoDocumento;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoDocumentoServiceImp implements TipoDocumentoService{
    
    @Autowired
    private TipoDocumentoDao tipoDocumentoDao;

    @Override
    @Transactional(readOnly = true)
    public List<TipoDocumento> listaTipoDocumentos() {
        return (List<TipoDocumento>) tipoDocumentoDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(TipoDocumento tipoDocumento) {
        tipoDocumentoDao.save(tipoDocumento);
    }

    @Override
    @Transactional
    public void eliminar(TipoDocumento tipoDocumento) {
        tipoDocumentoDao.delete(tipoDocumento);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoDocumento encontrar(Long idTipoDocumento) {
        return tipoDocumentoDao.findById(idTipoDocumento).orElse(null);
    }

    @Override
    @Transactional
    public void actualizar(TipoDocumento tipoDocumento) {
        if (tipoDocumentoDao.existsById(tipoDocumento.getIdTipoDocumento())) {
            tipoDocumentoDao.save(tipoDocumento);
        } else {
            throw new IllegalArgumentException("La Tipo de Documento no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<TipoDocumento> listarTipoDocumentos(DataTablesInput input) {
        return (DataTablesOutput<TipoDocumento>) tipoDocumentoDao.findAll(input);
    }
}
