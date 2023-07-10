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
@Table(name = "PROYECTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findByIdProyecto", query = "SELECT p FROM Proyecto p WHERE p.idProyecto = :idProyecto"),
    @NamedQuery(name = "Proyecto.findByNombre", query = "SELECT p FROM Proyecto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proyecto.findByEmpresa", query = "SELECT p FROM Proyecto p WHERE p.empresa = :empresa")})
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROYECTO")
    private Integer idProyecto;
    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 200)
    @Column(name = "EMPRESA")
    private String empresa;
    @OneToMany(mappedBy = "idProyecto")
    private Collection<Visitante> visitanteCollection;
    @OneToMany(mappedBy = "idProyecto")
    private Collection<Terreno> terrenoCollection;
    @OneToMany(mappedBy = "idProyecto")
    private Collection<Propietario> propietarioCollection;
    @OneToMany(mappedBy = "idProyecto")
    private Collection<CuentaBancaria> cuentaBancariaCollection;
    @OneToMany(mappedBy = "idProyecto")
    private Collection<Visita> visitaCollection;

    public Proyecto() {
    }

    public Proyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @XmlTransient
    public Collection<Visitante> getVisitanteCollection() {
        return visitanteCollection;
    }

    public void setVisitanteCollection(Collection<Visitante> visitanteCollection) {
        this.visitanteCollection = visitanteCollection;
    }

    @XmlTransient
    public Collection<Terreno> getTerrenoCollection() {
        return terrenoCollection;
    }

    public void setTerrenoCollection(Collection<Terreno> terrenoCollection) {
        this.terrenoCollection = terrenoCollection;
    }

    @XmlTransient
    public Collection<Propietario> getPropietarioCollection() {
        return propietarioCollection;
    }

    public void setPropietarioCollection(Collection<Propietario> propietarioCollection) {
        this.propietarioCollection = propietarioCollection;
    }

    @XmlTransient
    public Collection<CuentaBancaria> getCuentaBancariaCollection() {
        return cuentaBancariaCollection;
    }

    public void setCuentaBancariaCollection(Collection<CuentaBancaria> cuentaBancariaCollection) {
        this.cuentaBancariaCollection = cuentaBancariaCollection;
    }

    @XmlTransient
    public Collection<Visita> getVisitaCollection() {
        return visitaCollection;
    }

    public void setVisitaCollection(Collection<Visita> visitaCollection) {
        this.visitaCollection = visitaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProyecto != null ? idProyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.idProyecto == null && other.idProyecto != null) || (this.idProyecto != null && !this.idProyecto.equals(other.idProyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Proyecto[ idProyecto=" + idProyecto + " ]";
    }
    
}
