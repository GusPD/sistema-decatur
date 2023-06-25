package com.gl05.bad.servicio;

import com.gl05.bad.dao.AtestadoTaDao;
import com.gl05.bad.domain.AtestadoTa;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtestadoTaServiceImp implements AtestadoTaService{

  @Autowired
    private AtestadoTaDao atestadoDao;

  @Override
  public List<AtestadoTa> listarAtestados() {
    return (List<AtestadoTa>) atestadoDao.findAll();
  }

  @Override
  @Transactional
  public void agregarAtestado(AtestadoTa atestado) {
    atestadoDao.save(atestado);
  }

  @Override
  @Transactional
  public void actualizarAtestado(AtestadoTa atestado) {
    AtestadoTa atestadoExistente = atestadoDao.findById(atestado.getIdAtestadoTa()).orElse(null);
    atestadoExistente.setTipoAta(atestado.getTipoAta());
    atestadoExistente.setNombreAta(atestado.getNombreAta());
    atestadoExistente.setInstitucion(atestado.getInstitucion());
    atestadoExistente.setAnioTitulacion(atestado.getAnioTitulacion());
    
    Blob archivoActual = atestadoExistente.getArchivoAta();

    if (atestado.getArchivoAta() == null && archivoActual != null) {
      byte[] archivoBytes;
      try {
        archivoBytes = archivoActual.getBytes(1, (int) archivoActual.length());
        atestadoExistente.setArchivoAta(new javax.sql.rowset.serial.SerialBlob(archivoBytes));
      } catch (SQLException ex) {
        Logger.getLogger(AtestadoTaServiceImp.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      atestadoExistente.setArchivoAta(atestado.getArchivoAta());
    }

    atestadoDao.save(atestadoExistente);
  }
  
  @Override
  public void eliminarAtestado(AtestadoTa atestado) {
    atestadoDao.delete(atestado);
  }

  @Override
  public AtestadoTa encontrarAtestado(AtestadoTa atestado) {
    return atestadoDao.findById(atestado.getIdAtestadoTa()).orElse(null);
  }
}