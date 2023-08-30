package com.gl05.bad.servicio;

import com.gl05.bad.dao.VentaDao;
import com.gl05.bad.domain.Venta;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaServiceImp implements VentaService{
    
    @Autowired
    private VentaDao ventaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listaVentas() {
        return (List<Venta>) ventaDao.findAll();
    }

    @Override
    @Transactional
    public void agregarVenta(Venta venta) {
        ventaDao.save(venta);
    }

    @Override
    @Transactional
    public void eliminarVenta(Venta venta) {
        ventaDao.delete(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Venta encontrarVenta(Long idVenta) {
        return ventaDao.findById(idVenta).orElse(null);
    }

    @Override
    @Transactional
    public void actualizarVenta(Venta venta) {
        if (ventaDao.existsById(venta.getIdVenta())) {
            ventaDao.save(venta);
        } else {
            throw new IllegalArgumentException("La venta no existe.");
        }
    }

    /*@Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Terreno> listarTerrenos(DataTablesInput input) {
        return (DataTablesOutput<Terreno>) terrenoDao.findAll(input);
    }*/
    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Venta> listarVentas(DataTablesInput input, Long idTerreno) {
        Specification<Venta> specification = (root, query, builder) -> {
            return builder.equal(root.get("terreno").get("idTerreno"), idTerreno);
        };
        return ventaDao.findAll(input, specification);
    }
}
