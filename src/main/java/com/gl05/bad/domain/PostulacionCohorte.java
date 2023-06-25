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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "POSTULACION_COHORTE")
@NamedQueries({
    @NamedQuery(name = "PostulacionCohorte.findAll", query = "SELECT p FROM PostulacionCohorte p"),
    @NamedQuery(name = "PostulacionCohorte.findByIdPostulacion", query = "SELECT p FROM PostulacionCohorte p WHERE p.idPostulacion = :idPostulacion"),
    @NamedQuery(name = "PostulacionCohorte.findByFechaPostulacion", query = "SELECT p FROM PostulacionCohorte p WHERE p.fechaPostulacion = :fechaPostulacion")})
public class PostulacionCohorte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_POSTULACION_COHORTE")
    @SequenceGenerator(name = "S_POSTULACION_COHORTE", sequenceName = "S_POSTULACION_COHORTE", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID_POSTULACION")
    private Long idPostulacion;
    @Basic(optional = false)
    @Column(name = "FECHA_POSTULACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPostulacion;
    @JoinColumn(name = "ID_ASPIRANTE_PROFESOR", referencedColumnName = "ID_ASPIRANTE_PROFESOR")
    @ManyToOne(optional = false)
    private AspiranteProfesor idAspiranteProfesor;
    @JoinColumn(name = "ID_COHORTE", referencedColumnName = "ID_COHORTE")
    @ManyToOne(optional = false)
    private Cohorte idCohorte;

    public PostulacionCohorte() {
    }

    public PostulacionCohorte(Long idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    public PostulacionCohorte(Long idPostulacion, Date fechaPostulacion) {
        this.idPostulacion = idPostulacion;
        this.fechaPostulacion = fechaPostulacion;
    }

    public Long getIdPostulacion() {
        return idPostulacion;
    }

    public void setIdPostulacion(Long idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    public Date getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(Date fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    public AspiranteProfesor getIdAspiranteProfesor() {
        return idAspiranteProfesor;
    }

    public void setIdAspiranteProfesor(AspiranteProfesor idAspiranteProfesor) {
        this.idAspiranteProfesor = idAspiranteProfesor;
    }

    public Cohorte getIdCohorte() {
        return idCohorte;
    }

    public void setIdCohorte(Cohorte idCohorte) {
        this.idCohorte = idCohorte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPostulacion != null ? idPostulacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PostulacionCohorte)) {
            return false;
        }
        PostulacionCohorte other = (PostulacionCohorte) object;
        if ((this.idPostulacion == null && other.idPostulacion != null) || (this.idPostulacion != null && !this.idPostulacion.equals(other.idPostulacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.PostulacionCohorte[ idPostulacion=" + idPostulacion + " ]";
    }
    
}
