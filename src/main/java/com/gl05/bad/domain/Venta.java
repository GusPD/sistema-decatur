/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl05.bad.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gustavo Delgado
 */
@Entity
@Table(name = "VENTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.findByIdVenta", query = "SELECT v FROM Venta v WHERE v.idVenta = :idVenta"),
    @NamedQuery(name = "Venta.findByFechaEntrada", query = "SELECT v FROM Venta v WHERE v.fechaEntrada = :fechaEntrada"),
    @NamedQuery(name = "Venta.findByPrecio", query = "SELECT v FROM Venta v WHERE v.precio = :precio"),
    @NamedQuery(name = "Venta.findByDescuentoM", query = "SELECT v FROM Venta v WHERE v.descuentoM = :descuentoM"),
    @NamedQuery(name = "Venta.findByMontoM", query = "SELECT v FROM Venta v WHERE v.montoM = :montoM"),
    @NamedQuery(name = "Venta.findByPlazo", query = "SELECT v FROM Venta v WHERE v.plazo = :plazo"),
    @NamedQuery(name = "Venta.findByTasa", query = "SELECT v FROM Venta v WHERE v.tasa = :tasa"),
    @NamedQuery(name = "Venta.findByCuotaKi", query = "SELECT v FROM Venta v WHERE v.cuotaKi = :cuotaKi"),
    @NamedQuery(name = "Venta.findByCuotaMantenimiento", query = "SELECT v FROM Venta v WHERE v.cuotaMantenimiento = :cuotaMantenimiento"),
    @NamedQuery(name = "Venta.findByMultaMantenimiento", query = "SELECT v FROM Venta v WHERE v.multaMantenimiento = :multaMantenimiento"),
    @NamedQuery(name = "Venta.findByCuotaRecargo", query = "SELECT v FROM Venta v WHERE v.cuotaRecargo = :cuotaRecargo"),
    @NamedQuery(name = "Venta.findByMultaRecargo", query = "SELECT v FROM Venta v WHERE v.multaRecargo = :multaRecargo")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VENTA")
    private Integer idVenta;
    @Column(name = "FECHA_ENTRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrada;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Column(name = "DESCUENTO_M")
    private BigDecimal descuentoM;
    @Column(name = "MONTO_M")
    private BigDecimal montoM;
    @Column(name = "PLAZO")
    private BigInteger plazo;
    @Column(name = "TASA")
    private BigDecimal tasa;
    @Column(name = "CUOTA_KI")
    private BigDecimal cuotaKi;
    @Column(name = "CUOTA_MANTENIMIENTO")
    private BigDecimal cuotaMantenimiento;
    @Column(name = "MULTA_MANTENIMIENTO")
    private BigDecimal multaMantenimiento;
    @Column(name = "CUOTA_RECARGO")
    private BigDecimal cuotaRecargo;
    @Column(name = "MULTA_RECARGO")
    private BigDecimal multaRecargo;
    @OneToMany(mappedBy = "idVenta")
    private Collection<Documento> documentoCollection;
    @OneToMany(mappedBy = "idVenta")
    private Collection<CuotaFinanciamiento> cuotaFinanciamientoCollection;
    @OneToMany(mappedBy = "idVenta")
    private Collection<CuotaMantenimiento> cuotaMantenimientoCollection;
    @JoinColumn(name = "ID_TERRENO", referencedColumnName = "ID_TERRENO")
    @ManyToOne
    private Terreno idTerreno;
    @OneToMany(mappedBy = "idVenta")
    private Collection<CuotaAmortizacion> cuotaAmortizacionCollection;
    @OneToMany(mappedBy = "idVenta")
    private Collection<AsignacionPropietario> asignacionPropietarioCollection;
    @OneToMany(mappedBy = "idVenta")
    private Collection<Pago> pagoCollection;

    public Venta() {
    }

    public Venta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getDescuentoM() {
        return descuentoM;
    }

    public void setDescuentoM(BigDecimal descuentoM) {
        this.descuentoM = descuentoM;
    }

    public BigDecimal getMontoM() {
        return montoM;
    }

    public void setMontoM(BigDecimal montoM) {
        this.montoM = montoM;
    }

    public BigInteger getPlazo() {
        return plazo;
    }

    public void setPlazo(BigInteger plazo) {
        this.plazo = plazo;
    }

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public BigDecimal getCuotaKi() {
        return cuotaKi;
    }

    public void setCuotaKi(BigDecimal cuotaKi) {
        this.cuotaKi = cuotaKi;
    }

    public BigDecimal getCuotaMantenimiento() {
        return cuotaMantenimiento;
    }

    public void setCuotaMantenimiento(BigDecimal cuotaMantenimiento) {
        this.cuotaMantenimiento = cuotaMantenimiento;
    }

    public BigDecimal getMultaMantenimiento() {
        return multaMantenimiento;
    }

    public void setMultaMantenimiento(BigDecimal multaMantenimiento) {
        this.multaMantenimiento = multaMantenimiento;
    }

    public BigDecimal getCuotaRecargo() {
        return cuotaRecargo;
    }

    public void setCuotaRecargo(BigDecimal cuotaRecargo) {
        this.cuotaRecargo = cuotaRecargo;
    }

    public BigDecimal getMultaRecargo() {
        return multaRecargo;
    }

    public void setMultaRecargo(BigDecimal multaRecargo) {
        this.multaRecargo = multaRecargo;
    }

    @XmlTransient
    public Collection<Documento> getDocumentoCollection() {
        return documentoCollection;
    }

    public void setDocumentoCollection(Collection<Documento> documentoCollection) {
        this.documentoCollection = documentoCollection;
    }

    @XmlTransient
    public Collection<CuotaFinanciamiento> getCuotaFinanciamientoCollection() {
        return cuotaFinanciamientoCollection;
    }

    public void setCuotaFinanciamientoCollection(Collection<CuotaFinanciamiento> cuotaFinanciamientoCollection) {
        this.cuotaFinanciamientoCollection = cuotaFinanciamientoCollection;
    }

    @XmlTransient
    public Collection<CuotaMantenimiento> getCuotaMantenimientoCollection() {
        return cuotaMantenimientoCollection;
    }

    public void setCuotaMantenimientoCollection(Collection<CuotaMantenimiento> cuotaMantenimientoCollection) {
        this.cuotaMantenimientoCollection = cuotaMantenimientoCollection;
    }

    public Terreno getIdTerreno() {
        return idTerreno;
    }

    public void setIdTerreno(Terreno idTerreno) {
        this.idTerreno = idTerreno;
    }

    @XmlTransient
    public Collection<CuotaAmortizacion> getCuotaAmortizacionCollection() {
        return cuotaAmortizacionCollection;
    }

    public void setCuotaAmortizacionCollection(Collection<CuotaAmortizacion> cuotaAmortizacionCollection) {
        this.cuotaAmortizacionCollection = cuotaAmortizacionCollection;
    }

    @XmlTransient
    public Collection<AsignacionPropietario> getAsignacionPropietarioCollection() {
        return asignacionPropietarioCollection;
    }

    public void setAsignacionPropietarioCollection(Collection<AsignacionPropietario> asignacionPropietarioCollection) {
        this.asignacionPropietarioCollection = asignacionPropietarioCollection;
    }

    @XmlTransient
    public Collection<Pago> getPagoCollection() {
        return pagoCollection;
    }

    public void setPagoCollection(Collection<Pago> pagoCollection) {
        this.pagoCollection = pagoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenta != null ? idVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.idVenta == null && other.idVenta != null) || (this.idVenta != null && !this.idVenta.equals(other.idVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Venta[ idVenta=" + idVenta + " ]";
    }
    
}
