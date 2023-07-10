/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl05.bad.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gustavo Delgado
 */
@Entity
@Table(name = "TERRENO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terreno.findAll", query = "SELECT t FROM Terreno t"),
    @NamedQuery(name = "Terreno.findByIdTerreno", query = "SELECT t FROM Terreno t WHERE t.idTerreno = :idTerreno"),
    @NamedQuery(name = "Terreno.findByNumero", query = "SELECT t FROM Terreno t WHERE t.numero = :numero"),
    @NamedQuery(name = "Terreno.findByPoligono", query = "SELECT t FROM Terreno t WHERE t.poligono = :poligono"),
    @NamedQuery(name = "Terreno.findBySeccion", query = "SELECT t FROM Terreno t WHERE t.seccion = :seccion"),
    @NamedQuery(name = "Terreno.findByAreaMetros", query = "SELECT t FROM Terreno t WHERE t.areaMetros = :areaMetros"),
    @NamedQuery(name = "Terreno.findByAreaVaras", query = "SELECT t FROM Terreno t WHERE t.areaVaras = :areaVaras")})
public class Terreno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TERRENO")
    private Integer idTerreno;
    @Column(name = "NUMERO")
    private BigInteger numero;
    @Column(name = "POLIGONO")
    private Character poligono;
    @Column(name = "SECCION")
    private Character seccion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AREA_METROS")
    private BigDecimal areaMetros;
    @Column(name = "AREA_VARAS")
    private BigDecimal areaVaras;
    @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
    @ManyToOne
    private Proyecto idProyecto;
    @OneToMany(mappedBy = "idTerreno")
    private Collection<Venta> ventaCollection;

    public Terreno() {
    }

    public Terreno(Integer idTerreno) {
        this.idTerreno = idTerreno;
    }

    public Integer getIdTerreno() {
        return idTerreno;
    }

    public void setIdTerreno(Integer idTerreno) {
        this.idTerreno = idTerreno;
    }

    public BigInteger getNumero() {
        return numero;
    }

    public void setNumero(BigInteger numero) {
        this.numero = numero;
    }

    public Character getPoligono() {
        return poligono;
    }

    public void setPoligono(Character poligono) {
        this.poligono = poligono;
    }

    public Character getSeccion() {
        return seccion;
    }

    public void setSeccion(Character seccion) {
        this.seccion = seccion;
    }

    public BigDecimal getAreaMetros() {
        return areaMetros;
    }

    public void setAreaMetros(BigDecimal areaMetros) {
        this.areaMetros = areaMetros;
    }

    public BigDecimal getAreaVaras() {
        return areaVaras;
    }

    public void setAreaVaras(BigDecimal areaVaras) {
        this.areaVaras = areaVaras;
    }

    public Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }

    @XmlTransient
    public Collection<Venta> getVentaCollection() {
        return ventaCollection;
    }

    public void setVentaCollection(Collection<Venta> ventaCollection) {
        this.ventaCollection = ventaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTerreno != null ? idTerreno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Terreno)) {
            return false;
        }
        Terreno other = (Terreno) object;
        if ((this.idTerreno == null && other.idTerreno != null) || (this.idTerreno != null && !this.idTerreno.equals(other.idTerreno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Terreno[ idTerreno=" + idTerreno + " ]";
    }
    
}
