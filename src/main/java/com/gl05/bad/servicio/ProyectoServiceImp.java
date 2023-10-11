package com.gl05.bad.servicio;

import com.gl05.bad.dao.ProyectoDao;
import com.gl05.bad.dao.UsuarioDao;
import com.gl05.bad.domain.Proyecto;
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
public class ProyectoServiceImp implements ProyectoService{
    
    @Autowired
    private ProyectoDao proyectoDao;
    
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Proyecto> listaProyectos() {
        return (List<Proyecto>) proyectoDao.findAll();
    }

    @Override
    @Transactional
    public void agregar(Proyecto proyecto) {
        proyectoDao.save(proyecto);
    }

    @Override
    @Transactional
    public void eliminar(Proyecto proyecto) {
        proyectoDao.delete(proyecto);
    }

    @Override
    @Transactional(readOnly = true)
    public Proyecto encontrar(Long idProyecto) {
        return proyectoDao.findById(idProyecto).orElse(null);
    }

    @Override
    @Transactional
    public void actualizar(Proyecto proyecto) {
        if (proyectoDao.existsById(proyecto.getIdProyecto())) {
            proyectoDao.save(proyecto);
        } else {
            throw new IllegalArgumentException("El proyecto no existe.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataTablesOutput<Proyecto> listarProyectos(DataTablesInput input) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuarioActual = usuarioDao.findByUsername(username);
        Specification<Proyecto> specification = (root, query, builder) -> {
            Join<Proyecto, Usuario> proyectoUsuarioJoin = root.join("users"); // Usar el nombre del atributo en la entidad Proyecto
            return builder.equal(proyectoUsuarioJoin.get("idUsuario"), usuarioActual.getIdUsuario());
        };
        return (DataTablesOutput<Proyecto>) proyectoDao.findAll(input,specification);
    }
}
