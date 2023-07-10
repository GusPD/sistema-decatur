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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Data;
import org.hibernate.FetchMode;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;

@Data
@Entity
@Table(name = "ROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findByIdRol", query = "SELECT r FROM Rol r WHERE r.idRol = :idRol"),
    @NamedQuery(name = "Rol.findByNombre", query = "SELECT r FROM Rol r WHERE r.nombre = :nombre")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ROL")
    @SequenceGenerator(name = "S_ROL", sequenceName = "S_ROL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ROL")
    private Long idRol;
    
    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;
    
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<Usuario> users;

    // Establezco la relación con la base de datos
    @ManyToMany//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    //Añade permisos al rol
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
