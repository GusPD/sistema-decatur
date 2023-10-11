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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "ASIGNACION_PROPIETARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignacionPropietario.findAll", query = "SELECT a FROM AsignacionPropietario a"),
    @NamedQuery(name = "AsignacionPropietario.findByIdAsignacion", query = "SELECT a FROM AsignacionPropietario a WHERE a.idAsignacion = :idAsignacion"),
    @NamedQuery(name = "AsignacionPropietario.findByEstado", query = "SELECT a FROM AsignacionPropietario a WHERE a.estado = :estado")})
public class AsignacionPropietario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ASIG_PROPIETARIO")
    @SequenceGenerator(name = "S_ASIGNACION_PROPIETARIO", sequenceName = "S_ASIGNACION_PROPIETARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ASIGNACION_PROPIETARIO")
    private Long idAsignacion;
    @JoinColumn(name = "ID_PROPIETARIO", referencedColumnName = "ID_PROPIETARIO")
    @ManyToOne
    private Propietario propietario;
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @ManyToOne
    private Venta venta;
    @Column(name = "ESTADO")
    private String estado;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsignacion != null ? idAsignacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AsignacionPropietario)) {
            return false;
        }
        AsignacionPropietario other = (AsignacionPropietario) object;
        if ((this.idAsignacion == null && other.idAsignacion != null) || (this.idAsignacion != null && !this.idAsignacion.equals(other.idAsignacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.AsignacionPropietario[ idAsignacion=" + idAsignacion + " ]";
    }
    
}
