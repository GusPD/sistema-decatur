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
@Table(name = "CUOTA_FINANCIAMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuotaFinanciamiento.findAll", query = "SELECT c FROM CuotaFinanciamiento c"),
    @NamedQuery(name = "CuotaFinanciamiento.findByIdCuotaFinanciamiento", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.idCuotaFinanciamiento = :idCuotaFinanciamiento"),
    @NamedQuery(name = "CuotaFinanciamiento.findByFechaCuotaM", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.fechaCuotaM = :fechaCuotaM"),
    @NamedQuery(name = "CuotaFinanciamiento.findByDiasInteresCorriente", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.diasInteresCorriente = :diasInteresCorriente"),
    @NamedQuery(name = "CuotaFinanciamiento.findByInteresCorriente", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.interesCorriente = :interesCorriente"),
    @NamedQuery(name = "CuotaFinanciamiento.findByDiasInteresMora", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.diasInteresMora = :diasInteresMora"),
    @NamedQuery(name = "CuotaFinanciamiento.findByInteresMora", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.interesMora = :interesMora"),
    @NamedQuery(name = "CuotaFinanciamiento.findByPagadoInteres", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.pagadoInteres = :pagadoInteres"),
    @NamedQuery(name = "CuotaFinanciamiento.findByPendienteInteres", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.pendienteInteres = :pendienteInteres"),
    @NamedQuery(name = "CuotaFinanciamiento.findByComision", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.comision = :comision"),
    @NamedQuery(name = "CuotaFinanciamiento.findByRecargoM", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.recargoM = :recargoM"),
    @NamedQuery(name = "CuotaFinanciamiento.findByOtros", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.otros = :otros"),
    @NamedQuery(name = "CuotaFinanciamiento.findByPagadoOtros", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.pagadoOtros = :pagadoOtros"),
    @NamedQuery(name = "CuotaFinanciamiento.findByPendienteOtros", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.pendienteOtros = :pendienteOtros"),
    @NamedQuery(name = "CuotaFinanciamiento.findByCapital", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.capital = :capital"),
    @NamedQuery(name = "CuotaFinanciamiento.findBySaldo", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.saldo = :saldo")})
public class CuotaFinanciamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CUOTA_FINANCIAMIENTO")
    private Integer idCuotaFinanciamiento;
    @Column(name = "FECHA_CUOTA_M")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCuotaM;
    @Column(name = "DIAS_INTERES_CORRIENTE")
    private BigInteger diasInteresCorriente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "INTERES_CORRIENTE")
    private BigDecimal interesCorriente;
    @Column(name = "DIAS_INTERES_MORA")
    private BigInteger diasInteresMora;
    @Column(name = "INTERES_MORA")
    private BigDecimal interesMora;
    @Column(name = "PAGADO_INTERES")
    private BigDecimal pagadoInteres;
    @Column(name = "PENDIENTE_INTERES")
    private BigDecimal pendienteInteres;
    @Column(name = "COMISION")
    private BigDecimal comision;
    @Column(name = "RECARGO_M")
    private BigDecimal recargoM;
    @Column(name = "OTROS")
    private BigDecimal otros;
    @Column(name = "PAGADO_OTROS")
    private BigDecimal pagadoOtros;
    @Column(name = "PENDIENTE_OTROS")
    private BigDecimal pendienteOtros;
    @Column(name = "CAPITAL")
    private BigDecimal capital;
    @Column(name = "SALDO")
    private BigDecimal saldo;
    @JoinColumn(name = "ID_PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne
    private Pago idPago;
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @ManyToOne
    private Venta idVenta;

    public CuotaFinanciamiento() {
    }

    public CuotaFinanciamiento(Integer idCuotaFinanciamiento) {
        this.idCuotaFinanciamiento = idCuotaFinanciamiento;
    }

    public Integer getIdCuotaFinanciamiento() {
        return idCuotaFinanciamiento;
    }

    public void setIdCuotaFinanciamiento(Integer idCuotaFinanciamiento) {
        this.idCuotaFinanciamiento = idCuotaFinanciamiento;
    }

    public Date getFechaCuotaM() {
        return fechaCuotaM;
    }

    public void setFechaCuotaM(Date fechaCuotaM) {
        this.fechaCuotaM = fechaCuotaM;
    }

    public BigInteger getDiasInteresCorriente() {
        return diasInteresCorriente;
    }

    public void setDiasInteresCorriente(BigInteger diasInteresCorriente) {
        this.diasInteresCorriente = diasInteresCorriente;
    }

    public BigDecimal getInteresCorriente() {
        return interesCorriente;
    }

    public void setInteresCorriente(BigDecimal interesCorriente) {
        this.interesCorriente = interesCorriente;
    }

    public BigInteger getDiasInteresMora() {
        return diasInteresMora;
    }

    public void setDiasInteresMora(BigInteger diasInteresMora) {
        this.diasInteresMora = diasInteresMora;
    }

    public BigDecimal getInteresMora() {
        return interesMora;
    }

    public void setInteresMora(BigDecimal interesMora) {
        this.interesMora = interesMora;
    }

    public BigDecimal getPagadoInteres() {
        return pagadoInteres;
    }

    public void setPagadoInteres(BigDecimal pagadoInteres) {
        this.pagadoInteres = pagadoInteres;
    }

    public BigDecimal getPendienteInteres() {
        return pendienteInteres;
    }

    public void setPendienteInteres(BigDecimal pendienteInteres) {
        this.pendienteInteres = pendienteInteres;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getRecargoM() {
        return recargoM;
    }

    public void setRecargoM(BigDecimal recargoM) {
        this.recargoM = recargoM;
    }

    public BigDecimal getOtros() {
        return otros;
    }

    public void setOtros(BigDecimal otros) {
        this.otros = otros;
    }

    public BigDecimal getPagadoOtros() {
        return pagadoOtros;
    }

    public void setPagadoOtros(BigDecimal pagadoOtros) {
        this.pagadoOtros = pagadoOtros;
    }

    public BigDecimal getPendienteOtros() {
        return pendienteOtros;
    }

    public void setPendienteOtros(BigDecimal pendienteOtros) {
        this.pendienteOtros = pendienteOtros;
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

    public Pago getIdPago() {
        return idPago;
    }

    public void setIdPago(Pago idPago) {
        this.idPago = idPago;
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
        hash += (idCuotaFinanciamiento != null ? idCuotaFinanciamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuotaFinanciamiento)) {
            return false;
        }
        CuotaFinanciamiento other = (CuotaFinanciamiento) object;
        if ((this.idCuotaFinanciamiento == null && other.idCuotaFinanciamiento != null) || (this.idCuotaFinanciamiento != null && !this.idCuotaFinanciamiento.equals(other.idCuotaFinanciamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.CuotaFinanciamiento[ idCuotaFinanciamiento=" + idCuotaFinanciamiento + " ]";
    }
    
}
