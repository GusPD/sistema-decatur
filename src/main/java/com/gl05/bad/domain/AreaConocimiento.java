package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "AREA_CONOCIMIENTO" , uniqueConstraints = @UniqueConstraint(columnNames = "NOMBRE_AREA"))
@NamedQueries({
    @NamedQuery(name = "AreaConocimiento.findAll", query = "SELECT a FROM AreaConocimiento a"),
    @NamedQuery(name = "AreaConocimiento.findByIdAreaConocimiento", query = "SELECT a FROM AreaConocimiento a WHERE a.idAreaConocimiento = :idAreaConocimiento"),
    @NamedQuery(name = "AreaConocimiento.findByNombreArea", query = "SELECT a FROM AreaConocimiento a WHERE a.nombreArea = :nombreArea"),
    @NamedQuery(name = "AreaConocimiento.findByDescripcion", query = "SELECT a FROM AreaConocimiento a WHERE a.descripcion = :descripcion")})
public class AreaConocimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_AREA_CONOCIMIENTO")
    @SequenceGenerator(name = "S_AREA_CONOCIMIENTO", sequenceName = "S_AREA_CONOCIMIENTO", allocationSize = 1)
    @Column(name = "ID_AREA_CONOCIMIENTO")
    private Long idAreaConocimiento;
    @Basic(optional = false)
    @Column(name = "NOMBRE_AREA", unique = true)
    private String nombreArea;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "idAreaConocimiento")
    private Collection<Asignatura> asignaturaCollection;*/

    public AreaConocimiento() {
    }

    public AreaConocimiento(Long idAreaConocimiento) {
        this.idAreaConocimiento = idAreaConocimiento;
    }
    //Long idAreaConocimiento,
    public AreaConocimiento( String nombreArea, String descripcion) {
        //this.idAreaConocimiento = idAreaConocimiento;
        this.nombreArea = nombreArea;
        this.descripcion = descripcion;
    }

    public Long getIdAreaConocimiento() {
        return idAreaConocimiento;
    }

    public void setIdAreaConocimiento(Long idAreaConocimiento) {
        this.idAreaConocimiento = idAreaConocimiento;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /*public Collection<Asignatura> getAsignaturaCollection() {
        return asignaturaCollection;
    }

    public void setAsignaturaCollection(Collection<Asignatura> asignaturaCollection) {
        this.asignaturaCollection = asignaturaCollection;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAreaConocimiento != null ? idAreaConocimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaConocimiento)) {
            return false;
        }
        AreaConocimiento other = (AreaConocimiento) object;
        if ((this.idAreaConocimiento == null && other.idAreaConocimiento != null) || (this.idAreaConocimiento != null && !this.idAreaConocimiento.equals(other.idAreaConocimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.AreaConocimiento[ idAreaConocimiento=" + idAreaConocimiento + " ]";
    }
    
}
