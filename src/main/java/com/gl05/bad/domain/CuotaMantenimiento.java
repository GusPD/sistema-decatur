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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "CUOTA_MANTENIMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuotaMantenimiento.findAll", query = "SELECT c FROM CuotaMantenimiento c"),
    @NamedQuery(name = "CuotaMantenimiento.findByIdCuotaMantenimiento", query = "SELECT c FROM CuotaMantenimiento c WHERE c.idCuotaMantenimiento = :idCuotaMantenimiento"),
    @NamedQuery(name = "CuotaMantenimiento.findByFechaCuota", query = "SELECT c FROM CuotaMantenimiento c WHERE c.fechaCuota = :fechaCuota"),
    @NamedQuery(name = "CuotaMantenimiento.findByMonto", query = "SELECT c FROM CuotaMantenimiento c WHERE c.monto = :monto"),
    @NamedQuery(name = "CuotaMantenimiento.findByFechaRecargo", query = "SELECT c FROM CuotaMantenimiento c WHERE c.fechaRecargo = :fechaRecargo"),
    @NamedQuery(name = "CuotaMantenimiento.findByRecargo", query = "SELECT c FROM CuotaMantenimiento c WHERE c.recargo = :recargo"),
    @NamedQuery(name = "CuotaMantenimiento.findByConceptoDescuentoM", query = "SELECT c FROM CuotaMantenimiento c WHERE c.conceptoDescuento = :conceptoDescuento"),
    @NamedQuery(name = "CuotaMantenimiento.findByDescuento", query = "SELECT c FROM CuotaMantenimiento c WHERE c.descuento = :descuento"),
    @NamedQuery(name = "CuotaMantenimiento.findByEstadoMantenimiento", query = "SELECT c FROM CuotaMantenimiento c WHERE c.estadoMantenimiento = :estadoMantenimiento"),
    @NamedQuery(name = "CuotaMantenimiento.findByEstadoRecargo", query = "SELECT c FROM CuotaMantenimiento c WHERE c.estadoRecargo = :estadoRecargo")})
public class CuotaMantenimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CUOTA_MANTENIMIENTO")
    @SequenceGenerator(name = "S_CUOTA_MANTENIMIENTO", sequenceName = "S_CUOTA_MANTENIMIENTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_CUOTA_MANTENIMIENTO")
    private Integer idCuotaMantenimiento;
    @Column(name = "FECHA_CUOTA")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaCuota;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Column(name = "FECHA_RECARGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecargo;
    @Column(name = "RECARGO")
    private BigDecimal recargo;
    @Size(max = 200)
    @Column(name = "CONCEPTO_DESCUENTO")
    private String conceptoDescuento;
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Column(name = "ESTADO_MANTENIMIENTO")
    private BigInteger estadoMantenimiento;
    @Column(name = "ESTADO_RECARGO")
    private BigInteger estadoRecargo;
    @JoinColumn(name = "ID_PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne
    private Pago pago;
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    @ManyToOne
    private Venta venta;

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
