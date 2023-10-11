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
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "CUOTA_AMORTIZACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuotaAmortizacion.findAll", query = "SELECT c FROM CuotaAmortizacion c"),
    @NamedQuery(name = "CuotaAmortizacion.findByIdCuotaAmortizacion", query = "SELECT c FROM CuotaAmortizacion c WHERE c.idCuotaAmortizacion = :idCuotaAmortizacion"),
    @NamedQuery(name = "CuotaAmortizacion.findByFechaCuota", query = "SELECT c FROM CuotaAmortizacion c WHERE c.fechaCuota = :fechaCuota"),
    @NamedQuery(name = "CuotaAmortizacion.findByMonto", query = "SELECT c FROM CuotaAmortizacion c WHERE c.monto = :monto"),
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
    @SequenceGenerator(name = "S_CUOTA_AMORTIZACION", sequenceName = "S_CUOTA_AMORTIZACION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_CUOTA_AMORTIZACION")
    private Integer idCuotaAmortizacion;
    @Column(name = "FECHA_CUOTA")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaCuota;
    @Column(name = "MONTO")
    private BigDecimal monto;
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
    private Venta venta;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuotaAmortizacion != null ? idCuotaAmortizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
