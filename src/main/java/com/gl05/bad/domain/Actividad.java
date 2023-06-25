package com.gl05.bad.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({
    @NamedQuery(name = "Actividad.findAll", query = "SELECT a FROM Actividad a"),
    @NamedQuery(name = "Actividad.findByIdActividad", query = "SELECT a FROM Actividad a WHERE a.idActividad = :idActividad"),
    @NamedQuery(name = "Actividad.findByNombreActividad", query = "SELECT a FROM Actividad a WHERE a.nombreActividad = :nombreActividad"),
    @NamedQuery(name = "Actividad.findByPonderacion", query = "SELECT a FROM Actividad a WHERE a.ponderacion = :ponderacion")})
public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ACTIVIDAD")
    @SequenceGenerator(name = "S_ACTIVIDAD", sequenceName = "S_ACTIVIDAD", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID_ACTIVIDAD")
    private Long idActividad;
    @Basic(optional = false)
    @Column(name = "NOMBRE_ACTIVIDAD")
    private String nombreActividad;
    @Basic(optional = false)
    private short ponderacion;
    @JoinColumn(name = "ID_LIST_AE", referencedColumnName = "ID_LIST_AE")
    @ManyToOne(optional = false)
    private ListadoActividadEvaluada idListAe;

    public Actividad() {
    }

    public Actividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public Actividad(Long idActividad, String nombreActividad, short ponderacion) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.ponderacion = ponderacion;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public short getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(short ponderacion) {
        this.ponderacion = ponderacion;
    }

    public ListadoActividadEvaluada getIdListAe() {
        return idListAe;
    }

    public void setIdListAe(ListadoActividadEvaluada idListAe) {
        this.idListAe = idListAe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividad != null ? idActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.idActividad == null && other.idActividad != null) || (this.idActividad != null && !this.idActividad.equals(other.idActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Actividad[ idActividad=" + idActividad + " ]";
    }
    
}
