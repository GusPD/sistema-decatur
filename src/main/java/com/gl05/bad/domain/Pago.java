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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gustavo Delgado
 */
@Entity
@Table(name = "PAGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p"),
    @NamedQuery(name = "Pago.findByIdPago", query = "SELECT p FROM Pago p WHERE p.idPago = :idPago"),
    @NamedQuery(name = "Pago.findByFechaEntrada", query = "SELECT p FROM Pago p WHERE p.fechaEntrada = :fechaEntrada"),
    @NamedQuery(name = "Pago.findByRecibo", query = "SELECT p FROM Pago p WHERE p.recibo = :recibo"),
    @NamedQuery(name = "Pago.findByTipo", query = "SELECT p FROM Pago p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "Pago.findByMontoM", query = "SELECT p FROM Pago p WHERE p.montoM = :montoM"),
    @NamedQuery(name = "Pago.findByOtros", query = "SELECT p FROM Pago p WHERE p.otros = :otros"),
    @NamedQuery(name = "Pago.findByDescuentoM", query = "SELECT p FROM Pago p WHERE p.descuentoM = :descuentoM"),
    @NamedQuery(name = "Pago.findByObservaciones", query = "SELECT p FROM Pago p WHERE p.observaciones = :observaciones")})
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PAGO")
    private Integer idPago;
    @Column(name = "FECHA_ENTRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrada;
    @Column(name = "RECIBO")
    private BigInteger recibo;
    @Size(max = 20)
    @Column(name = "TIPO")
    private String tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MONTO_M")
    private BigDecimal montoM;
    @Column(name = "OTROS")
    private BigDecimal otros;
    @Column(name = "DESCUENTO_M")
    private BigDecimal descuentoM;
    @Size(max = 500)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @OneToMany(mappedBy = "idPago")
    private Collection<CuotaFinanciamiento> cuotaFinanciamientoCollection;
    @OneToMany(mappedBy = "idPago")
    private Collection<CuotaMantenimiento> cuotaMantenimientoCollection;
    @JoinColumn(name = "ID_CUENTA_BANCARIA", referencedColumnName = "ID_CUENTA_BANCARIA")
    @ManyToOne
    private CuentaBancaria idCuentaBancaria;
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @ManyToOne
    private Venta idVenta;

    public Pago() {
    }

    public Pago(Integer idPago) {
        this.idPago = idPago;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public BigInteger getRecibo() {
        return recibo;
    }

    public void setRecibo(BigInteger recibo) {
        this.recibo = recibo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMontoM() {
        return montoM;
    }

    public void setMontoM(BigDecimal montoM) {
        this.montoM = montoM;
    }

    public BigDecimal getOtros() {
        return otros;
    }

    public void setOtros(BigDecimal otros) {
        this.otros = otros;
    }

    public BigDecimal getDescuentoM() {
        return descuentoM;
    }

    public void setDescuentoM(BigDecimal descuentoM) {
        this.descuentoM = descuentoM;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public CuentaBancaria getIdCuentaBancaria() {
        return idCuentaBancaria;
    }

    public void setIdCuentaBancaria(CuentaBancaria idCuentaBancaria) {
        this.idCuentaBancaria = idCuentaBancaria;
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
        hash += (idPago != null ? idPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Pago[ idPago=" + idPago + " ]";
    }
    
}
