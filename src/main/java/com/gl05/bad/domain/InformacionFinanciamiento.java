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
@Table(name = "INFO_FINANCIAMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InformacionFinanciamiento.findAll", query = "SELECT i FROM InformacionFinanciamiento i"),
    @NamedQuery(name = "InformacionFinanciamiento.findByIdAsignacion", query = "SELECT i FROM InformacionFinanciamiento i WHERE i.idAsignacion = :idAsignacion"),
    @NamedQuery(name = "InformacionFinanciamiento.findByFechaAplicacion", query = "SELECT i FROM InformacionFinanciamiento i WHERE i.fechaAplicacion = :fechaAplicacion"),
    @NamedQuery(name = "InformacionFinanciamiento.findByMonto", query = "SELECT i FROM InformacionFinanciamiento i WHERE i.monto = :monto"),
    @NamedQuery(name = "InformacionFinanciamiento.findByPlazo", query = "SELECT i FROM InformacionFinanciamiento i WHERE i.plazo = :plazo"),
    @NamedQuery(name = "InformacionFinanciamiento.findByTasa", query = "SELECT i FROM InformacionFinanciamiento i WHERE i.tasa = :tasa"),
    @NamedQuery(name = "InformacionFinanciamiento.findByCuota", query = "SELECT i FROM InformacionFinanciamiento i WHERE i.cuota = :cuota"),
    @NamedQuery(name = "InformacionFinanciamiento.findByMulta", query = "SELECT i FROM InformacionFinanciamiento i WHERE i.multa = :multa")})
public class InformacionFinanciamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_INFO_FINANCIAMIENTO")
    @SequenceGenerator(name = "S_INFO_FINANCIAMIENTO", sequenceName = "S_INFO_FINANCIAMIENTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_INFO_FINANCIAMIENTO")
    private Long idAsignacion;
    @Column(name = "FECHA_APLICACION")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaAplicacion;
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Column(name = "PLAZO")
    private Integer plazo;
    @Column(name = "TASA")
    private BigDecimal tasa;
    @Column(name = "CUOTA_KI")
    private BigDecimal cuota;
    @Column(name = "MULTA_FINANCIAMIENTO")
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
        if (!(object instanceof InformacionFinanciamiento)) {
            return false;
        }
        InformacionFinanciamiento other = (InformacionFinanciamiento) object;
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
