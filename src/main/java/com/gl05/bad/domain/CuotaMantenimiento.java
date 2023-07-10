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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gustavo Delgado
 */
@Entity
@Table(name = "CUOTA_MANTENIMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuotaMantenimiento.findAll", query = "SELECT c FROM CuotaMantenimiento c"),
    @NamedQuery(name = "CuotaMantenimiento.findByIdCuotaMantenimiento", query = "SELECT c FROM CuotaMantenimiento c WHERE c.idCuotaMantenimiento = :idCuotaMantenimiento"),
    @NamedQuery(name = "CuotaMantenimiento.findByFechaCuotaM", query = "SELECT c FROM CuotaMantenimiento c WHERE c.fechaCuotaM = :fechaCuotaM"),
    @NamedQuery(name = "CuotaMantenimiento.findByMontoM", query = "SELECT c FROM CuotaMantenimiento c WHERE c.montoM = :montoM"),
    @NamedQuery(name = "CuotaMantenimiento.findByFechaRecargoM", query = "SELECT c FROM CuotaMantenimiento c WHERE c.fechaRecargoM = :fechaRecargoM"),
    @NamedQuery(name = "CuotaMantenimiento.findByRecargoM", query = "SELECT c FROM CuotaMantenimiento c WHERE c.recargoM = :recargoM"),
    @NamedQuery(name = "CuotaMantenimiento.findByConceptoDescuentoM", query = "SELECT c FROM CuotaMantenimiento c WHERE c.conceptoDescuentoM = :conceptoDescuentoM"),
    @NamedQuery(name = "CuotaMantenimiento.findByDescuentoM", query = "SELECT c FROM CuotaMantenimiento c WHERE c.descuentoM = :descuentoM"),
    @NamedQuery(name = "CuotaMantenimiento.findByEstadoMantenimiento", query = "SELECT c FROM CuotaMantenimiento c WHERE c.estadoMantenimiento = :estadoMantenimiento"),
    @NamedQuery(name = "CuotaMantenimiento.findByEstadoRecargo", query = "SELECT c FROM CuotaMantenimiento c WHERE c.estadoRecargo = :estadoRecargo")})
public class CuotaMantenimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CUOTA_MANTENIMIENTO")
    private Integer idCuotaMantenimiento;
    @Column(name = "FECHA_CUOTA_M")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCuotaM;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MONTO_M")
    private BigDecimal montoM;
    @Column(name = "FECHA_RECARGO_M")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecargoM;
    @Column(name = "RECARGO_M")
    private BigDecimal recargoM;
    @Size(max = 200)
    @Column(name = "CONCEPTO_DESCUENTO_M")
    private String conceptoDescuentoM;
    @Column(name = "DESCUENTO_M")
    private BigDecimal descuentoM;
    @Column(name = "ESTADO_MANTENIMIENTO")
    private BigInteger estadoMantenimiento;
    @Column(name = "ESTADO_RECARGO")
    private BigInteger estadoRecargo;
    @JoinColumn(name = "ID_PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne
    private Pago idPago;
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @ManyToOne
    private Venta idVenta;

    public CuotaMantenimiento() {
    }

    public CuotaMantenimiento(Integer idCuotaMantenimiento) {
        this.idCuotaMantenimiento = idCuotaMantenimiento;
    }

    public Integer getIdCuotaMantenimiento() {
        return idCuotaMantenimiento;
    }

    public void setIdCuotaMantenimiento(Integer idCuotaMantenimiento) {
        this.idCuotaMantenimiento = idCuotaMantenimiento;
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

    public Date getFechaRecargoM() {
        return fechaRecargoM;
    }

    public void setFechaRecargoM(Date fechaRecargoM) {
        this.fechaRecargoM = fechaRecargoM;
    }

    public BigDecimal getRecargoM() {
        return recargoM;
    }

    public void setRecargoM(BigDecimal recargoM) {
        this.recargoM = recargoM;
    }

    public String getConceptoDescuentoM() {
        return conceptoDescuentoM;
    }

    public void setConceptoDescuentoM(String conceptoDescuentoM) {
        this.conceptoDescuentoM = conceptoDescuentoM;
    }

    public BigDecimal getDescuentoM() {
        return descuentoM;
    }

    public void setDescuentoM(BigDecimal descuentoM) {
        this.descuentoM = descuentoM;
    }

    public BigInteger getEstadoMantenimiento() {
        return estadoMantenimiento;
    }

    public void setEstadoMantenimiento(BigInteger estadoMantenimiento) {
        this.estadoMantenimiento = estadoMantenimiento;
    }

    public BigInteger getEstadoRecargo() {
        return estadoRecargo;
    }

    public void setEstadoRecargo(BigInteger estadoRecargo) {
        this.estadoRecargo = estadoRecargo;
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
        hash += (idCuotaMantenimiento != null ? idCuotaMantenimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuotaMantenimiento)) {
            return false;
        }
        CuotaMantenimiento other = (CuotaMantenimiento) object;
        if ((this.idCuotaMantenimiento == null && other.idCuotaMantenimiento != null) || (this.idCuotaMantenimiento != null && !this.idCuotaMantenimiento.equals(other.idCuotaMantenimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.CuotaMantenimiento[ idCuotaMantenimiento=" + idCuotaMantenimiento + " ]";
    }
    
}
