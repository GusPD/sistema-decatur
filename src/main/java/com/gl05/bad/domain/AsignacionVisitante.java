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
@Table(name = "ASIGNACION_VISITANTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignacionVisitante.findAll", query = "SELECT a FROM AsignacionPropietario a"),
    @NamedQuery(name = "AsignacionVisitante.findByIdAsignacion", query = "SELECT a FROM AsignacionVisitante a WHERE a.idAsignacion = :idAsignacion")})
public class AsignacionVisitante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ASIG_VISITANTE")
    @SequenceGenerator(name = "S_ASIGNACION_VISITANTE", sequenceName = "S_ASIGNACION_VISITANTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ASIGNACION_VISITANTE")
    private Long idAsignacion;
    @JoinColumn(name = "ID_VISITANTE", referencedColumnName = "ID_VISITANTE")
    @ManyToOne
    private Visitante visitante;
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @ManyToOne
    private Venta venta;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsignacion != null ? idAsignacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AsignacionVisitante)) {
            return false;
        }
        AsignacionVisitante other = (AsignacionVisitante) object;
        if ((this.idAsignacion == null && other.idAsignacion != null) || (this.idAsignacion != null && !this.idAsignacion.equals(other.idAsignacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.AsignacionVisitante[ idAsignacion=" + idAsignacion + " ]";
    }
    
}
