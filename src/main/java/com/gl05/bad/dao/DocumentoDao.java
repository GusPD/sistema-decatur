package com.gl05.bad.dao;

import com.gl05.bad.domain.Documento;
import com.gl05.bad.domain.ListadoDocumentacionPersonal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoDao extends JpaRepository<Documento, Long>{
  List<Documento> findByIdListDp(ListadoDocumentacionPersonal listaDoc);
  List<Documento> findByTipoFile(List<Documento> doc);
}

