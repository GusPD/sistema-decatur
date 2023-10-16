package com.gl05.bad.servicio;

import com.gl05.bad.dao.ReferenciaDao;
import com.gl05.bad.domain.Referencia;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReferenciaServiceImp implements ReferenciaService{
  
    @Autowired
    private ReferenciaDao referenciaDao;

    @Override
    public List<Referencia> listaReferencias() {
        return (List<Referencia>) referenciaDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Referencia referencia) {
        referenciaDao.save(referencia);
    }
    
    @Override
    @Transactional
    public void actualizar(Referencia referencia) {
        Referencia referenciaExistente = referenciaDao.findById(referencia.getIdReferencia()).orElse(null);
        referenciaDao.save(referenciaExistente);
    }

    @Override
    public void eliminar(Referencia referencia) {
        referenciaDao.delete(referencia);
    }

    @Override
    public Referencia encontrar(Referencia referencia) {
        return referenciaDao.findById(referencia.getIdReferencia()).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Referencia> listarReferencias(DataTablesInput input, Long idPropietario) {
        Specification<Referencia> specification = (root, query, builder) -> {
            return builder.equal(root.get("idPropietario"), idPropietario);
        };        
        return referenciaDao.findAll(input, specification);
    }
}
