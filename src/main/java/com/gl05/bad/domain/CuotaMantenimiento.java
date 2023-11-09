package com.gl05.bad.domain;

import java.io.Serializable;
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
    @NamedQuery(name = "CuotaMantenimiento.findByFechaRegistro", query = "SELECT c FROM CuotaMantenimiento c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "CuotaMantenimiento.findByFechaCuota", query = "SELECT c FROM CuotaMantenimiento c WHERE c.fechaCuota = :fechaCuota"),
    @NamedQuery(name = "CuotaMantenimiento.findByCuota", query = "SELECT c FROM CuotaMantenimiento c WHERE c.cuota = :cuota"),
    @NamedQuery(name = "CuotaMantenimiento.findBySaldoCuota", query = "SELECT c FROM CuotaMantenimiento c WHERE c.saldoCuota = :saldoCuota"),
    @NamedQuery(name = "CuotaMantenimiento.findByFechaRecargo", query = "SELECT c FROM CuotaMantenimiento c WHERE c.fechaRecargo = :fechaRecargo"),
    @NamedQuery(name = "CuotaMantenimiento.findByRecargo", query = "SELECT c FROM CuotaMantenimiento c WHERE c.recargo = :recargo"),
    @NamedQuery(name = "CuotaMantenimiento.findBySaldoRecargo", query = "SELECT c FROM CuotaMantenimiento c WHERE c.saldoRecargo = :saldoRecargo"),
    @NamedQuery(name = "CuotaMantenimiento.findByDescuento", query = "SELECT c FROM CuotaMantenimiento c WHERE c.descuento = :descuento")})
public class CuotaMantenimiento implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CUOTA_MANTENIMIENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuotaMantenimiento;
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "FECHA_CUOTA")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaCuota;
    @Column(name = "CUOTA")
    private double cuota;
    @Column(name = "SALDO_CUOTA")
    private double saldoCuota;
    @Column(name = "FECHA_RECARGO")
    @Temporal(TemporalType.DATE)
    private Date fechaRecargo;
    @Column(name = "RECARGO")
    private double recargo;
    @Column(name = "SALDO_RECARGO")
    private double saldoRecargo;
    @Size(max = 200)
    @Column(name = "DESCUENTO")
    private double descuento;
    @JoinColumn(name = "ID_PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne
    private Pago pago;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuotaMantenimiento != null ? idCuotaMantenimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
