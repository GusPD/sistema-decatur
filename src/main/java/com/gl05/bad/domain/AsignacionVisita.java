/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "ASIGNACION_PROYECTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignacionVisita.findAll", query = "SELECT a FROM AsignacionPropietario a"),
    @NamedQuery(name = "AsignacionVisita.findByIdAsignacion", query = "SELECT a FROM AsignacionVisita a WHERE a.idAsignacion = :idAsignacion")})
public class AsignacionVisita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ASIG_VISITA")
    @SequenceGenerator(name = "S_ASIGNACION_VISITA", sequenceName = "S_ASIGNACION_VISITA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ASIGNACION_VISITA")
    private Long idAsignacion;
    @Column(name = "REPRESENTANTE")
    private Long representante;
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @OneToOne
    private Venta venta;
    @JoinColumn(name = "ID_VISITA", referencedColumnName = "ID_VISITA")
    @OneToOne
    private Visita visita;
    @JoinColumn(name = "ID_PROPIETARIO", referencedColumnName = "ID_PROPIETARIO")
    @ManyToOne
    private Propietario propietario;
    @JoinColumn(name = "ID_VISITANTE", referencedColumnName = "ID_VISITANTE")
    @ManyToOne
    private Visitante visitante;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsignacion != null ? idAsignacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsignacionVisita)) {
            return false;
        }
        AsignacionVisita other = (AsignacionVisita) object;
        if ((this.idAsignacion == null && other.idAsignacion != null) || (this.idAsignacion != null && !this.idAsignacion.equals(other.idAsignacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.AsignacionVisita[ idAsignacion=" + idAsignacion + " ]";
    }
    
}
