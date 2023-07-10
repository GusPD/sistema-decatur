/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl05.bad.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gustavo Delgado
 */
@Entity
@Table(name = "ASIGNACION_PROPIETARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignacionPropietario.findAll", query = "SELECT a FROM AsignacionPropietario a"),
    @NamedQuery(name = "AsignacionPropietario.findByIdAsigPropietario", query = "SELECT a FROM AsignacionPropietario a WHERE a.idAsigPropietario = :idAsigPropietario")})
public class AsignacionPropietario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ASIG_PROPIETARIO")
    private Integer idAsigPropietario;
    @JoinColumn(name = "ID_PROPIETARIO", referencedColumnName = "ID_PROPIETARIO")
    @ManyToOne
    private Propietario idPropietario;
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @ManyToOne
    private Venta idVenta;

    public AsignacionPropietario() {
    }

    public AsignacionPropietario(Integer idAsigPropietario) {
        this.idAsigPropietario = idAsigPropietario;
    }

    public Integer getIdAsigPropietario() {
        return idAsigPropietario;
    }

    public void setIdAsigPropietario(Integer idAsigPropietario) {
        this.idAsigPropietario = idAsigPropietario;
    }

    public Propietario getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Propietario idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Venta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Venta idVenta) {
        this.idVenta = idVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsigPropietario != null ? idAsigPropietario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsignacionPropietario)) {
            return false;
        }
        AsignacionPropietario other = (AsignacionPropietario) object;
        if ((this.idAsigPropietario == null && other.idAsigPropietario != null) || (this.idAsigPropietario != null && !this.idAsigPropietario.equals(other.idAsigPropietario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.AsignacionPropietario[ idAsigPropietario=" + idAsigPropietario + " ]";
    }
    
}
