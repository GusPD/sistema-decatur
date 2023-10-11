package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByIntentos", query = "SELECT u FROM Usuario u WHERE u.intentos = :intentos"),
    @NamedQuery(name = "Usuario.findByBloqueado", query = "SELECT u FROM Usuario u WHERE u.bloqueado = :bloqueado"),
    @NamedQuery(name = "Usuario.findByHabilitado", query = "SELECT u FROM Usuario u WHERE u.habilitado = :habilitado")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_USUARIO")
    @SequenceGenerator(name = "S_USUARIO", sequenceName = "S_USUARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_USUARIO")
    private Long idUsuario;
    
    @Size(max = 50)
    @Column(name = "USERNAME")
    private String username;
    
    @Size(max = 100)
    @Column(name = "PASSWORD")
    private String password;

    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "INTENTOS")
    private int intentos;
    
    @Column(name = "BLOQUEADO")
    private int bloqueado;
    
    @Column(name = "HABILITADO")
    private boolean habilitado;

    @ManyToMany
    @JoinTable(
        name = "USUARIO_ROL",
        joinColumns = @JoinColumn(name = "ID_USUARIO"),
        inverseJoinColumns = @JoinColumn(name = "ID_ROL")
    )
    private Set<Rol> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "ASIGNACION_PROYECTO",
        joinColumns = @JoinColumn(name = "ID_USUARIO"),
        inverseJoinColumns = @JoinColumn(name = "ID_PROYECTO")
    )
    private Set<Proyecto> proyectos = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "ASIGNACION_EMPRESA",
        joinColumns = @JoinColumn(name = "ID_USUARIO"),
        inverseJoinColumns = @JoinColumn(name = "ID_EMPRESA")
    )
    private Set<Empresa> empresas = new HashSet<>();
    
    public void añadirRol(Rol rol){
        this.roles.add(rol);
    }
    
    public void añadirProyecto(Proyecto proyecto){
        this.proyectos.add(proyecto);
    }
    
    public void eliminarProyecto(Proyecto proyecto){
        this.proyectos.remove(proyecto);
    }
    
    public void añadirEmpresa(Empresa empresa){
        this.empresas.add(empresa);
    }
    
    public void eliminarEmpresa(Empresa empresa){
        this.empresas.remove(empresa);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idUsuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.idUsuario, other.idUsuario);
    }

    @Override
    public String toString() {
        return username ;
    }
}
