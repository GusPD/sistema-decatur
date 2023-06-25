package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "PERMISO")
public class Permisos implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Column(name="IDPERMISO")
    @Id
    @SequenceGenerator(name = "PERMISO_SEQ", sequenceName = "PERMISO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISO_SEQ")
    private Long idPermiso;
    
    @Column(name="NOMBRE")
    private String nombre;
    
    @ManyToMany(mappedBy = "permisos")
    private Collection<Roles> roles;

    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.idPermiso);
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
        final Permisos other = (Permisos) obj;
        return Objects.equals(this.idPermiso, other.idPermiso);
    }
    
    
    
    
}
