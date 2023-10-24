package com.gl05.bad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "ROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findByIdRol", query = "SELECT r FROM Rol r WHERE r.idRol = :idRol"),
    @NamedQuery(name = "Rol.findByNombre", query = "SELECT r FROM Rol r WHERE r.nombre = :nombre")})
public class Rol implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ROL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    
    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;
    
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<Usuario> users;

    @ManyToMany
    @JoinTable(
            name = "ROL_PERMISO",
            joinColumns = @JoinColumn(name = "ID_ROL"),
            inverseJoinColumns = @JoinColumn(name = "ID_PERMISO")
    )
    private Set<Permiso> permisos = new HashSet<>();
    
    @Override
    public String toString() {
        return nombre ;
    }

    public void añadirPermiso(Permiso permiso){
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
        final Rol other = (Rol) obj;
        return Objects.equals(this.idRol, other.idRol);
    }
    
}
