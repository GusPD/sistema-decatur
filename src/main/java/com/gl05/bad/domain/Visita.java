package com.gl05.bad.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "VISITA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visita.findAll", query = "SELECT v FROM Visita v"),
    @NamedQuery(name = "Visita.findByIdVisita", query = "SELECT v FROM Visita v WHERE v.idVisita = :idVisita"),
    @NamedQuery(name = "Visita.findByCantidadAdultos", query = "SELECT v FROM Visita v WHERE v.cantidadAdultos = :cantidadAdultos"),
    @NamedQuery(name = "Visita.findByCantidadNinos", query = "SELECT v FROM Visita v WHERE v.cantidadNinos = :cantidadNinos"),
    @NamedQuery(name = "Visita.findByFechaEntrada", query = "SELECT v FROM Visita v WHERE v.fechaEntrada = :fechaEntrada"),
    @NamedQuery(name = "Visita.findByHoraEntrada", query = "SELECT v FROM Visita v WHERE v.horaEntrada = :horaEntrada"),
    @NamedQuery(name = "Visita.findByFechaSalida", query = "SELECT v FROM Visita v WHERE v.fechaSalida = :fechaSalida"),
    @NamedQuery(name = "Visita.findByHoraSalida", query = "SELECT v FROM Visita v WHERE v.horaSalida = :horaSalida"),
    @NamedQuery(name = "Visita.findByObservaciones", query = "SELECT v FROM Visita v WHERE v.observaciones = :observaciones")})
public class Visita implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VISITA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVisita;
    @Column(name = "CANTIDAD_ADULTOS")
    private BigInteger cantidadAdultos;
    @Column(name = "CANTIDAD_NINOS")
    private BigInteger cantidadNinos;
    @Column(name = "FECHA_ENTRADA")
    @Temporal(TemporalType.TIMESTAMP)
     @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaEntrada;
    @Column(name = "HORA_ENTRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaEntrada;
    @Column(name = "FECHA_SALIDA")
    @Temporal(TemporalType.TIMESTAMP)
     @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaSalida;
    @Column(name = "HORA_SALIDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaSalida;
    @Size(max = 500)
    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVisita != null ? idVisita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Visita)) {
            return false;
        }
        Visita other = (Visita) object;
        if ((this.idVisita == null && other.idVisita != null) || (this.idVisita != null && !this.idVisita.equals(other.idVisita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Visita[ idVisita=" + idVisita + " ]";
    }
    
}
