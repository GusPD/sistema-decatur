package com.gl05.bad.servicio;

import com.gl05.bad.dao.EmpresaDao;
import com.gl05.bad.dao.UsuarioDao;
import com.gl05.bad.domain.Empresa;
import com.gl05.bad.domain.Usuario;
import java.util.List;
import javax.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpresaServiceImp implements EmpresaService{
    
    @Autowired
    private EmpresaDao empresaDao;
    
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Empresa> listaEmpresas() {
        return (List<Empresa>) empresaDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Empresa empresa) {
        empresaDao.save(empresa);
    }

    @Override
    @Transactional
    public void eliminar(Empresa empresa) {
        empresaDao.delete(empresa);
    }

    @Override
    @Transactional(readOnly = true)
    public Empresa encontrar(Long idEmpresa) {
        return empresaDao.findById(idEmpresa).orElse(null);
    }

    @Override
    @Transactional
    public void actualizar(Empresa empresa) {
        if (empresaDao.existsById(empresa.getIdEmpresa())) {
            empresaDao.save(empresa);
        } else {
            throw new IllegalArgumentException("La empresa no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Empresa> listarEmpresas(DataTablesInput input) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuarioActual = usuarioDao.findByUsername(username);
        Specification<Empresa> specification = (root, query, builder) -> {
            Join<Empresa, Usuario> empresaUsuarioJoin = root.join("usuariosEmpresa");
            return builder.equal(empresaUsuarioJoin.get("idUsuario"), usuarioActual.getIdUsuario());
        };
        return (DataTablesOutput<Empresa>) empresaDao.findAll(input,specification);
    }
}
