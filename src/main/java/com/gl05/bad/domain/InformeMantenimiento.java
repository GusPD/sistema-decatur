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
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "INFORME_MANTENIMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformeMantenimiento.findAll", query = "SELECT i FROM InformeMantenimiento i"),
    @NamedQuery(name = "InformeMantenimiento.findByIdAsignacion", query = "SELECT i FROM InformeMantenimiento i WHERE i.idInforme = :idInforme"),
    @NamedQuery(name = "InformeMantenimiento.findByFechaPago", query = "SELECT i FROM InformeMantenimiento i WHERE i.fechaPago = :fechaPago"),
    @NamedQuery(name = "InformeMantenimiento.findByFechaCuota", query = "SELECT i FROM InformeMantenimiento i WHERE i.fechaCuota = :fechaCuota"),
    @NamedQuery(name = "InformeMantenimiento.findByLote", query = "SELECT i FROM InformeMantenimiento i WHERE i.lote = :lote"),
    @NamedQuery(name = "InformeMantenimiento.findByEstado", query = "SELECT i FROM InformeMantenimiento i WHERE i.estado = :estado"),
    @NamedQuery(name = "InformeMantenimiento.findByCuota", query = "SELECT i FROM InformeMantenimiento i WHERE i.cuota = :cuota"),
    @NamedQuery(name = "InformeMantenimiento.findByMulta", query = "SELECT i FROM InformeMantenimiento i WHERE i.multa = :multa")})
public class InformeMantenimiento implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_INFORME_MANTENIMIENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInforme;
    @Column(name = "FECHA_PAGO")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaPago;
    @Column(name = "FECHA_CUOTA")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaCuota;
    @Column(name = "LOTE")
    private String lote;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "MONTO_CUOTA")
    private double cuota;
    @Column(name = "MONTO_MULTA")
    private double multa;
    @ManyToOne
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    private Venta venta;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInforme != null ? idInforme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof InformeMantenimiento)) {
            return false;
        }
        InformeMantenimiento other = (InformeMantenimiento) object;
        if ((this.idInforme == null && other.idInforme != null) || (this.idInforme != null && !this.idInforme.equals(other.idInforme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.InformeMantenimiento[ idInforme=" + idInforme + " ]";
    }
    
}
