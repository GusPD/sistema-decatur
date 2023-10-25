package com.gl05.bad.servicio;

import com.gl05.bad.domain.Pago;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gl05.bad.dao.PagoDao;

@Service
public class PagoServiceImp implements PagoService{
    
    @Autowired
    private PagoDao pagoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Pago> listaPagos() {
        return (List<Pago>) pagoDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Pago pago) {
        pagoDao.save(pago);
    }

    @Override
    @Transactional
    public void eliminar(Pago pago) {
        pagoDao.delete(pago);
    }

    @Override
    @Transactional(readOnly = true)
    public Pago encontrar(Long idPago) {
        return pagoDao.findById(idPago).orElse(null);
    }

    @Override
    @Transactional
    public void actualizar(Pago pago) {
        if (pagoDao.existsById(pago.getIdPago())) {
            pagoDao.save(pago);
        } else {
            throw new IllegalArgumentException("El pago no existe.");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Pago> listarPagos(DataTablesInput input, Long idProyecto) {
        Specification<Pago> specification = (root, query, builder) -> {
            return builder.equal(root.get("proyecto").get("idProyecto"), idProyecto);
        };
        return pagoDao.findAll(input, specification);
    }
}
