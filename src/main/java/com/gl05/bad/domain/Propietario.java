/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gustavo Delgado
 */
@Entity
@Table(name = "PROPIETARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propietario.findAll", query = "SELECT p FROM Propietario p"),
    @NamedQuery(name = "Propietario.findByIdPropietario", query = "SELECT p FROM Propietario p WHERE p.idPropietario = :idPropietario"),
    @NamedQuery(name = "Propietario.findByComprobante", query = "SELECT p FROM Propietario p WHERE p.comprobante = :comprobante"),
    @NamedQuery(name = "Propietario.findByProfesion", query = "SELECT p FROM Propietario p WHERE p.profesion = :profesion"),
    @NamedQuery(name = "Propietario.findByDireccionCasa", query = "SELECT p FROM Propietario p WHERE p.direccionCasa = :direccionCasa"),
    @NamedQuery(name = "Propietario.findByLugarTrabajo", query = "SELECT p FROM Propietario p WHERE p.lugarTrabajo = :lugarTrabajo"),
    @NamedQuery(name = "Propietario.findByDireccionTrabajo", query = "SELECT p FROM Propietario p WHERE p.direccionTrabajo = :direccionTrabajo")})
public class Propietario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROPIETARIO")
    private Integer idPropietario;
    @Column(name = "COMPROBANTE")
    private Short comprobante;
    @Size(max = 200)
    @Column(name = "PROFESION")
    private String profesion;
    @Size(max = 300)
    @Column(name = "DIRECCION_CASA")
    private String direccionCasa;
    @Size(max = 200)
    @Column(name = "LUGAR_TRABAJO")
    private String lugarTrabajo;
    @Size(max = 300)
    @Column(name = "DIRECCION_TRABAJO")
    private String direccionTrabajo;
    @OneToMany(mappedBy = "idPropietario")
    private Collection<Correo> correoCollection;
    @OneToMany(mappedBy = "idPropietario")
    private Collection<Referencia> referenciaCollection;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @ManyToOne
    private Persona idPersona;
    @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
    @ManyToOne
    private Proyecto idProyecto;
    @OneToMany(mappedBy = "idPropietario")
    private Collection<AsignacionPropietario> asignacionPropietarioCollection;
    @OneToMany(mappedBy = "idPropietario")
    private Collection<Telefono> telefonoCollection;

    public Propietario() {
    }

    public Propietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Short getComprobante() {
        return comprobante;
    }

    public void setComprobante(Short comprobante) {
        this.comprobante = comprobante;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getDireccionCasa() {
        return direccionCasa;
    }

    public void setDireccionCasa(String direccionCasa) {
        this.direccionCasa = direccionCasa;
    }

    public String getLugarTrabajo() {
        return lugarTrabajo;
    }

    public void setLugarTrabajo(String lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
    }

    public String getDireccionTrabajo() {
        return direccionTrabajo;
    }

    public void setDireccionTrabajo(String direccionTrabajo) {
        this.direccionTrabajo = direccionTrabajo;
    }

    @XmlTransient
    public Collection<Correo> getCorreoCollection() {
        return correoCollection;
    }

    public void setCorreoCollection(Collection<Correo> correoCollection) {
        this.correoCollection = correoCollection;
    }

    @XmlTransient
    public Collection<Referencia> getReferenciaCollection() {
        return referenciaCollection;
    }

    public void setReferenciaCollection(Collection<Referencia> referenciaCollection) {
        this.referenciaCollection = referenciaCollection;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }

    @XmlTransient
    public Collection<AsignacionPropietario> getAsignacionPropietarioCollection() {
        return asignacionPropietarioCollection;
    }

    public void setAsignacionPropietarioCollection(Collection<AsignacionPropietario> asignacionPropietarioCollection) {
        this.asignacionPropietarioCollection = asignacionPropietarioCollection;
    }

    @XmlTransient
    public Collection<Telefono> getTelefonoCollection() {
        return telefonoCollection;
    }

    public void setTelefonoCollection(Collection<Telefono> telefonoCollection) {
        this.telefonoCollection = telefonoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPropietario != null ? idPropietario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propietario)) {
            return false;
        }
        Propietario other = (Propietario) object;
        if ((this.idPropietario == null && other.idPropietario != null) || (this.idPropietario != null && !this.idPropietario.equals(other.idPropietario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Propietario[ idPropietario=" + idPropietario + " ]";
    }
    
}
