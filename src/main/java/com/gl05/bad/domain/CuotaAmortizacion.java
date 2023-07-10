/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl05.bad.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gustavo Delgado
 */
@Entity
@Table(name = "CUOTA_AMORTIZACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuotaAmortizacion.findAll", query = "SELECT c FROM CuotaAmortizacion c"),
    @NamedQuery(name = "CuotaAmortizacion.findByIdCuotaAmortizacion", query = "SELECT c FROM CuotaAmortizacion c WHERE c.idCuotaAmortizacion = :idCuotaAmortizacion"),
    @NamedQuery(name = "CuotaAmortizacion.findByFechaCuotaM", query = "SELECT c FROM CuotaAmortizacion c WHERE c.fechaCuotaM = :fechaCuotaM"),
    @NamedQuery(name = "CuotaAmortizacion.findByMontoM", query = "SELECT c FROM CuotaAmortizacion c WHERE c.montoM = :montoM"),
    @NamedQuery(name = "CuotaAmortizacion.findByDiasInteres", query = "SELECT c FROM CuotaAmortizacion c WHERE c.diasInteres = :diasInteres"),
    @NamedQuery(name = "CuotaAmortizacion.findByInteres", query = "SELECT c FROM CuotaAmortizacion c WHERE c.interes = :interes"),
    @NamedQuery(name = "CuotaAmortizacion.findByComision", query = "SELECT c FROM CuotaAmortizacion c WHERE c.comision = :comision"),
    @NamedQuery(name = "CuotaAmortizacion.findByCapital", query = "SELECT c FROM CuotaAmortizacion c WHERE c.capital = :capital"),
    @NamedQuery(name = "CuotaAmortizacion.findBySaldo", query = "SELECT c FROM CuotaAmortizacion c WHERE c.saldo = :saldo")})
public class CuotaAmortizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CUOTA_AMORTIZACION")
    private Integer idCuotaAmortizacion;
    @Column(name = "FECHA_CUOTA_M")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCuotaM;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MONTO_M")
    private BigDecimal montoM;
    @Column(name = "DIAS_INTERES")
    private BigInteger diasInteres;
    @Column(name = "INTERES")
    private BigDecimal interes;
    @Column(name = "COMISION")
    private BigDecimal comision;
    @Column(name = "CAPITAL")
    private BigDecimal capital;
    @Column(name = "SALDO")
    private BigDecimal saldo;
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @ManyToOne
    private Venta idVenta;

    public CuotaAmortizacion() {
    }

    public CuotaAmortizacion(Integer idCuotaAmortizacion) {
        this.idCuotaAmortizacion = idCuotaAmortizacion;
    }

    public Integer getIdCuotaAmortizacion() {
        return idCuotaAmortizacion;
    }

    public void setIdCuotaAmortizacion(Integer idCuotaAmortizacion) {
        this.idCuotaAmortizacion = idCuotaAmortizacion;
    }

    public Date getFechaCuotaM() {
        return fechaCuotaM;
    }

    public void setFechaCuotaM(Date fechaCuotaM) {
        this.fechaCuotaM = fechaCuotaM;
    }

    public BigDecimal getMontoM() {
        return montoM;
    }

    public void setMontoM(BigDecimal montoM) {
        this.montoM = montoM;
    }

    public BigInteger getDiasInteres() {
        return diasInteres;
    }

    public void setDiasInteres(BigInteger diasInteres) {
        this.diasInteres = diasInteres;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
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
        hash += (idCuotaAmortizacion != null ? idCuotaAmortizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuotaAmortizacion)) {
            return false;
        }
        CuotaAmortizacion other = (CuotaAmortizacion) object;
        if ((this.idCuotaAmortizacion == null && other.idCuotaAmortizacion != null) || (this.idCuotaAmortizacion != null && !this.idCuotaAmortizacion.equals(other.idCuotaAmortizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.CuotaAmortizacion[ idCuotaAmortizacion=" + idCuotaAmortizacion + " ]";
    }
    
}
