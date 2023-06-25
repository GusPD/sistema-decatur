package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "ROL")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "IDROL")
    @Id
    @SequenceGenerator(name = "ROL_SEQ", sequenceName = "ROL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROL_SEQ")
    private Long idRol;

    @Column(name = "NOMBRE")
    private String nombre;

    @ManyToMany(mappedBy = "roles")
    private Collection<Usuario> users;

    //Establezco la relación con la base de datos
  @ManyToMany//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ROLES_PERMISOS",
            joinColumns = @JoinColumn(name = "IDROL"),
            inverseJoinColumns = @JoinColumn(name = "IDPERMISO")
    )
    private Set<Permisos> permisos = new HashSet<>();

    public Roles() {
    
    }
    
    public Roles(Long idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public Roles(String nombre) {
        this.nombre = nombre;
    }
    
    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Permisos> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permisos> permisos) {
        this.permisos = permisos;
    }

    @Override
    public String toString() {
        return nombre ;
    }

    //Añade permisos al rol
    public void añadirPermiso(Permisos permiso){
        this.permisos.add(permiso);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.idRol);
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
        final Roles other = (Roles) obj;
        return Objects.equals(this.idRol, other.idRol);
    }
    
    

    
}
