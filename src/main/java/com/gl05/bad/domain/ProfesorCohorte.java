package com.gl05.bad.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import lombok.Data;

@Data
@Entity
@Table(name = "PROFESOR_COHORTE")
@NamedQueries({
    @NamedQuery(name = "ProfesorCohorte.findAll", query = "SELECT p FROM ProfesorCohorte p"),
    @NamedQuery(name = "ProfesorCohorte.findByIdProfesor", query = "SELECT p FROM ProfesorCohorte p WHERE p.idProfesor = :idProfesor"),
    @NamedQuery(name = "ProfesorCohorte.findByMontoPagarHora", query = "SELECT p FROM ProfesorCohorte p WHERE p.montoPagarHora = :montoPagarHora"),
    @NamedQuery(name = "ProfesorCohorte.findByUnidadHorasImpartir", query = "SELECT p FROM ProfesorCohorte p WHERE p.unidadHorasImpartir = :unidadHorasImpartir"),
    @NamedQuery(name = "ProfesorCohorte.findByMontoTotalServicios", query = "SELECT p FROM ProfesorCohorte p WHERE p.montoTotalServicios = :montoTotalServicios"),
    @NamedQuery(name = "ProfesorCohorte.findByFechaContratacion", query = "SELECT p FROM ProfesorCohorte p WHERE p.fechaContratacion = :fechaContratacion")})
public class ProfesorCohorte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_PROFESOR_COHORTE")
    @SequenceGenerator(name = "S_PROFESOR_COHORTE", sequenceName = "S_PROFESOR_COHORTE", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID_PROFESOR")
    private Long idProfesor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @DecimalMin(value = "0.00", inclusive = true)
    @DecimalMax(value = "99999.99", inclusive = true)
     @Column(name = "MONTO_PAGAR_HORA", precision = 7, scale = 2, nullable = false)
    private BigDecimal montoPagarHora;
    @Column(name = "UNIDAD_HORAS_IMPARTIR")
    private Short unidadHorasImpartir;
    @DecimalMin(value = "0.00", inclusive = true)
    @DecimalMax(value = "99999.99", inclusive = true)
    @Column(name = "MONTO_TOTAL_SERVICIOS", precision = 7, scale = 2, nullable = false)
    private BigDecimal montoTotalServicios;
    @Column(name = "FECHA_CONTRATACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaContratacion;
    
    @JoinColumn(name = "ID_ASPIRANTE_PROFESOR", referencedColumnName = "ID_ASPIRANTE_PROFESOR")
    @ManyToOne(optional = false)
    private AspiranteProfesor idAspiranteProfesor;
    
    /*@Column(name="ID_ASPIRANTE_PROFESOR")
    private Integer idAspiranteProfesor;*/
  
    @JoinColumn(name = "ID_COHORTE", referencedColumnName = "ID_COHORTE")
    @ManyToOne(optional = false)
    private Cohorte idCohorte;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "idProfesor")
    //private Collection<ProfesorAsignatura> profesorAsignaturaCollection;

    public ProfesorCohorte() {
    }

    public ProfesorCohorte(Long idProfesor) {
        this.idProfesor = idProfesor;
    }

    public Long getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Long idProfesor) {
        this.idProfesor = idProfesor;
    }

    public BigDecimal getMontoPagarHora() {
        return montoPagarHora;
    }

    public void setMontoPagarHora(BigDecimal montoPagarHora) {
        this.montoPagarHora = montoPagarHora;
    }

    public Short getUnidadHorasImpartir() {
        return unidadHorasImpartir;
    }

    public void setUnidadHorasImpartir(Short unidadHorasImpartir) {
        this.unidadHorasImpartir = unidadHorasImpartir;
    }

    public BigDecimal getMontoTotalServicios() {
        return montoTotalServicios;
    }

    public void setMontoTotalServicios(BigDecimal montoTotalServicios) {
        this.montoTotalServicios = montoTotalServicios;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Cohorte getIdCohorte() {
        return idCohorte;
    }

    public void setIdCohorte(Cohorte idCohorte) {
        this.idCohorte = idCohorte;
    }
/*
    public Collection<ProfesorAsignatura> getProfesorAsignaturaCollection() {
        return profesorAsignaturaCollection;
    }

    public void setProfesorAsignaturaCollection(Collection<ProfesorAsignatura> profesorAsignaturaCollection) {
        this.profesorAsignaturaCollection = profesorAsignaturaCollection;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProfesor != null ? idProfesor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfesorCohorte)) {
            return false;
        }
        ProfesorCohorte other = (ProfesorCohorte) object;
        if ((this.idProfesor == null && other.idProfesor != null) || (this.idProfesor != null && !this.idProfesor.equals(other.idProfesor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ProfesorCohorte[ idProfesor=" + idProfesor + " ]";
    }
    
}
