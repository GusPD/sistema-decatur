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
    public void AgregarUsuarios(Usuario usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    @Transactional
    public void eliminarUsuario(Usuario usuario) {
        // Obtener el usuario iniciado en sesión
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuarioActual = usuarioDao.findByUsername(username);

        // Verificar si el usuario a eliminar es el usuario iniciado en sesión
        if (usuarioActual.getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new IllegalArgumentException("No se puede eliminar el usuario iniciado en sesión.");
        }

        // Proceder con la eliminación del usuario
        usuarioDao.delete(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario encontrarUsuario(Long idUsuario) {
        return usuarioDao.findById(idUsuario).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario encontrarUsuarioPorUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario encontrarUsuarioPorEmail(String email) {
        return usuarioDao.findByEmail(email);
    }

    @Override
    @Transactional
    public void actualizarUsuario(Usuario usuario) {
        if (usuarioDao.existsById(usuario.getIdUsuario())) {
            // Actualiza el rol en la base de datos
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
