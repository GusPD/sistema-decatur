package com.gl05.bad.servicio;

import com.gl05.bad.dao.UsuarioDao;
import com.gl05.bad.domain.Permiso;
import com.gl05.bad.domain.Rol;
import com.gl05.bad.domain.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Recuperamos al usuario
        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
            
        }

        if (usuario.getBloqueado() == 1) {
            throw new LockedException("La cuenta está bloqueada");
        }

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(), usuario.getPassword(), usuario.isHabilitado(), true, true,
                true, getAuthorities(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Rol> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Rol> roles) {
        List<String> privileges = new ArrayList<>();
        List<Permiso> collection = new ArrayList<>();
        for (Rol role : roles) {
            privileges.add(role.getNombre());
            collection.addAll(role.getPermisos());
        }
        for (Permiso item : collection) {
            privileges.add(item.getNombre());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
