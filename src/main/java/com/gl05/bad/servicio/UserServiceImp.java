package com.gl05.bad.servicio;

import com.gl05.bad.dao.UsuarioDao;
import com.gl05.bad.domain.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listaUsuarios() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Usuario usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Usuario usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuarioActual = usuarioDao.findByUsername(username);
        if (usuarioActual.getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new IllegalArgumentException("No se puede eliminar el usuario iniciado en sesión.");
        }
        usuarioDao.delete(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario encontrar(Long idUsuario) {
        return usuarioDao.findById(idUsuario).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario encontrarUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario encontrarEmail(String email) {
        return usuarioDao.findByEmail(email);
    }

    @Override
    @Transactional
    public void actualizar(Usuario usuario) {
        if (usuarioDao.existsById(usuario.getIdUsuario())) {
            usuarioDao.save(usuario);
        } else {
            throw new IllegalArgumentException("El usuario no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Usuario> listarUsuarios(DataTablesInput input) {
        return (DataTablesOutput<Usuario>) usuarioDao.findAll(input);
    }
}
