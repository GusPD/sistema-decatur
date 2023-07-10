/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl05.bad.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gustavo Delgado
 */
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

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VISITA")
    private Integer idVisita;
    @Column(name = "CANTIDAD_ADULTOS")
    private BigInteger cantidadAdultos;
    @Column(name = "CANTIDAD_NINOS")
    private BigInteger cantidadNinos;
    @Column(name = "FECHA_ENTRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrada;
    @Column(name = "HORA_ENTRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaEntrada;
    @Column(name = "FECHA_SALIDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;
    @Column(name = "HORA_SALIDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaSalida;
    @Size(max = 500)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @OneToMany(mappedBy = "idVisita")
    private Collection<Visitante> visitanteCollection;
    @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
    @ManyToOne
    private Proyecto idProyecto;

    public Visita() {
    }

    public Visita(Integer idVisita) {
        this.idVisita = idVisita;
    }

    public Integer getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(Integer idVisita) {
        this.idVisita = idVisita;
    }

    public BigInteger getCantidadAdultos() {
        return cantidadAdultos;
    }

    public void setCantidadAdultos(BigInteger cantidadAdultos) {
        this.cantidadAdultos = cantidadAdultos;
    }

    public BigInteger getCantidadNinos() {
        return cantidadNinos;
    }

    public void setCantidadNinos(BigInteger cantidadNinos) {
        this.cantidadNinos = cantidadNinos;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public Collection<Visitante> getVisitanteCollection() {
        return visitanteCollection;
    }

    public void setVisitanteCollection(Collection<Visitante> visitanteCollection) {
        this.visitanteCollection = visitanteCollection;
    }

    public Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVisita != null ? idVisita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
