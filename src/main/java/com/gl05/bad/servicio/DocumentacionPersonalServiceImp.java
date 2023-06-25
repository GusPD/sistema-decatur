package com.gl05.bad.servicio;

import com.gl05.bad.dao.DocumentacionPersonalDao;
import com.gl05.bad.domain.ListadoDocumentacionPersonal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentacionPersonalServiceImp implements DocumentacionPersonalService{

  @Autowired
  private DocumentacionPersonalDao docPersonalDao;
  
  @Override
  public List<ListadoDocumentacionPersonal> listarDocumentosPersonales() {
    return (List<ListadoDocumentacionPersonal>)docPersonalDao.findAll();
  }

  @Override
  public ListadoDocumentacionPersonal encontrarDocPersonal(ListadoDocumentacionPersonal doc) {
    return docPersonalDao.findById(doc.getIdListDp()).orElse(null);
  }

  /*@Override
  @Transactional
  public void actualizarCampo(ListadoDocumentacionPersonal doc, String nombreCampo, Blob blob) {
    ListadoDocumentacionPersonal docExistente = docPersonalDao.findById(doc.getIdListDp()).orElse(null);

    switch (nombreCampo) {
        case "duiFile":
            docExistente.setDuiFile(blob);
            break;
        case "nitFile":
            docExistente.setNitFile(blob);
            break;
        case "nupFile":
            docExistente.setNupFile(blob);
            break;
        case "pasaporteFile":
            docExistente.setPasaporteFile(blob);
            break;
        case "cvFile":
            docExistente.setCvFile(blob);
            break;
        case "docPersonalFile":
            docExistente.setDocPersonalFile(blob);
            break;
    }

    docPersonalDao.save(docExistente);
  }*/
  
}
