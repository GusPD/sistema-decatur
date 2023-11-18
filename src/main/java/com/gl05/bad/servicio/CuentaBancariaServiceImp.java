package com.gl05.bad.servicio;

import com.gl05.bad.dao.CuentaBancariaDao;
import com.gl05.bad.domain.CuentaBancaria;
import com.gl05.bad.domain.Empresa;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuentaBancariaServiceImp implements CuentaBancariaService{
    
    @Autowired
    private CuentaBancariaDao cuentaDao;

    @Override
    @Transactional(readOnly = true)
    public List<CuentaBancaria> listaCuentas() {
        return (List<CuentaBancaria>) cuentaDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(CuentaBancaria cuenta) {
        cuentaDao.save(cuenta);
    }

    @Override
    @Transactional
    public void eliminar(CuentaBancaria cuenta) {
        cuentaDao.delete(cuenta);
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaBancaria encontrar(Long idCuenta) {
        return cuentaDao.findById(idCuenta).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CuentaBancaria> encontrarEmpresa(Empresa empresa) {
        return cuentaDao.findByEmpresa(empresa);
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaBancaria encontrarNombreEmpresa(String nombre, Empresa empresa) {
        return cuentaDao.findByNombreAndEmpresa(nombre, empresa);
    }

    @Override
    @Transactional
    public void actualizar(CuentaBancaria cuenta) {
        if (cuentaDao.existsById(cuenta.getIdCuenta())) {
            cuentaDao.save(cuenta);
        } else {
            throw new IllegalArgumentException("La cuenta no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<CuentaBancaria> listarCuentas(DataTablesInput input, Long idEmpresa) {
        Specification<CuentaBancaria> specification = (root, query, builder) -> {
            return builder.equal(root.get("empresa").get("idEmpresa"), idEmpresa);
        };
        return cuentaDao.findAll(input, specification);
    }
}
