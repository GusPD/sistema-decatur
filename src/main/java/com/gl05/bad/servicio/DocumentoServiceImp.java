package com.gl05.bad.servicio;

import static antlr.build.ANTLR.root;
import com.gl05.bad.dao.DocumentoDao;
import com.gl05.bad.domain.Documento;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import static org.springframework.security.core.userdetails.User.builder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentoServiceImp implements DocumentoService{
  
    @Autowired
    private DocumentoDao docDao;

    @Override
    public List<Documento> listarDocumentos() {
      return (List<Documento>)docDao.findAll();
    }

    @Override
    public Documento encontrar(Documento doc) {
      return docDao.findById(doc.getIdDocumento()).orElse(null);
    }
    @Override
    @Transactional
    public void agregar(Documento doc) {
      docDao.save(doc);
    }
  
    @Override
    public void eliminar(Documento doc) {
      docDao.delete(doc);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Documento> listarDocumentos(DataTablesInput input, Integer idListDocumento) {
        Specification<Documento> specification = (root, query, builder) -> {
            return builder.equal(root.get("idListDocumento"), idListDocumento);
        };        
        return docDao.findAll(input, specification);
    }
}
