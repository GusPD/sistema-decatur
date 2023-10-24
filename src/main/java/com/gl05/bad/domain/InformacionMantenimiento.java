package com.gl05.bad.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "INFO_MANTENIMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformacionMantenimiento.findAll", query = "SELECT i FROM InformacionMantenimiento i"),
    @NamedQuery(name = "InformacionMantenimiento.findByIdAsignacion", query = "SELECT i FROM InformacionMantenimiento i WHERE i.idAsignacion = :idAsignacion"),
    @NamedQuery(name = "InformacionMantenimiento.findByFechaAplicacion", query = "SELECT i FROM InformacionMantenimiento i WHERE i.fechaAplicacion = :fechaAplicacion"),
    @NamedQuery(name = "InformacionMantenimiento.findByCuota", query = "SELECT i FROM InformacionMantenimiento i WHERE i.cuota = :cuota"),
    @NamedQuery(name = "InformacionMantenimiento.findByMulta", query = "SELECT i FROM InformacionMantenimiento i WHERE i.multa = :multa")})
public class InformacionMantenimiento implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_INFO_MANTENIMIENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignacion;
    @Column(name = "FECHA_APLICACION")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaAplicacion;
    @Column(name = "CUOTA_MANTENIMIENTO")
    private BigDecimal cuota;
    @Column(name = "MULTA_MANTENIMIENTO")
    private BigDecimal multa;
    @ManyToOne
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    private Venta venta;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsignacion != null ? idAsignacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformacionMantenimiento)) {
            return false;
        }
        InformacionMantenimiento other = (InformacionMantenimiento) object;
        if ((this.idAsignacion == null && other.idAsignacion != null) || (this.idAsignacion != null && !this.idAsignacion.equals(other.idAsignacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.InformacionMantenimiento[ idAsignacion=" + idAsignacion + " ]";
    }
    
}
