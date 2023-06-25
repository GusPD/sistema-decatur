package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.Collection;
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

@Entity
@Table(name = "ESTUDIANTE_COHORTE")
@NamedQueries({
    @NamedQuery(name = "EstudianteCohorte.findAll", query = "SELECT e FROM EstudianteCohorte e"),
    @NamedQuery(name = "EstudianteCohorte.findByIdEstudCo", query = "SELECT e FROM EstudianteCohorte e WHERE e.idEstudCo = :idEstudCo")})
public class EstudianteCohorte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "S_ESTUDIANTE_COHORTE", sequenceName = "S_ESTUDIANTE_COHORTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ESTUDIANTE_COHORTE")
    @Column(name = "ID_ESTUD_CO")
    private Long idEstudCo;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstudCo")
    private Collection<EstudianteAsignatura> estudianteAsignaturaCollection;*/
    @JoinColumn(name = "ID_COHORTE", referencedColumnName = "ID_COHORTE")
    @ManyToOne(optional = false)
    private Cohorte idCohorte;
    @JoinColumn(name = "ID_ESTUDIANTE", referencedColumnName = "ID_ESTUDIANTE")
    @ManyToOne(optional = false)
    private Estudiante idEstudiante;

    public EstudianteCohorte() {
    }

    public EstudianteCohorte(Long idEstudCo) {
        this.idEstudCo = idEstudCo;
    }

    public Long getIdEstudCo() {
        return idEstudCo;
    }

    public void setIdEstudCo(Long idEstudCo) {
        this.idEstudCo = idEstudCo;
    }

    /*public Collection<EstudianteAsignatura> getEstudianteAsignaturaCollection() {
        return estudianteAsignaturaCollection;
    }

    public void setEstudianteAsignaturaCollection(Collection<EstudianteAsignatura> estudianteAsignaturaCollection) {
        this.estudianteAsignaturaCollection = estudianteAsignaturaCollection;
    }*/

    public Cohorte getIdCohorte() {
        return idCohorte;
    }

    public void setIdCohorte(Cohorte idCohorte) {
        this.idCohorte = idCohorte;
    }

    public Estudiante getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Estudiante idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstudCo != null ? idEstudCo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstudianteCohorte)) {
            return false;
        }
        EstudianteCohorte other = (EstudianteCohorte) object;
        if ((this.idEstudCo == null && other.idEstudCo != null) || (this.idEstudCo != null && !this.idEstudCo.equals(other.idEstudCo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.EstudianteCohorte[ idEstudCo=" + idEstudCo + " ]";
    }
    
}
