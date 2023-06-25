
package com.gl05.bad.dao;

import com.gl05.bad.domain.Usuario;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends DataTablesRepository<Usuario, Long>{
    Usuario findByUsername(String username);
    Usuario findByEmail(String email);
}
