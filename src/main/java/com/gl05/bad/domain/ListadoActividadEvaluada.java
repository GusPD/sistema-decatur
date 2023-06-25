package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LISTADO_ACTIVIDAD_EVALUADA")
@NamedQueries({
    @NamedQuery(name = "ListadoActividadEvaluada.findAll", query = "SELECT l FROM ListadoActividadEvaluada l"),
    @NamedQuery(name = "ListadoActividadEvaluada.findByIdListAe", query = "SELECT l FROM ListadoActividadEvaluada l WHERE l.idListAe = :idListAe")})
public class ListadoActividadEvaluada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LIST_AE")
    private Long idListAe;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "idListAe")
    private Collection<Actividad> actividadCollection;
    @OneToMany(mappedBy = "idListAe")
    private Collection<ProgramaAsignatura> programaAsignaturaCollection;*/

    public ListadoActividadEvaluada() {
    }

    public ListadoActividadEvaluada(Long idListAe) {
        this.idListAe = idListAe;
    }

    public Long getIdListAe() {
        return idListAe;
    }

    public void setIdListAe(Long idListAe) {
        this.idListAe = idListAe;
    }

   /* public Collection<Actividad> getActividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
    }

    public Collection<ProgramaAsignatura> getProgramaAsignaturaCollection() {
        return programaAsignaturaCollection;
    }

    public void setProgramaAsignaturaCollection(Collection<ProgramaAsignatura> programaAsignaturaCollection) {
        this.programaAsignaturaCollection = programaAsignaturaCollection;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idListAe != null ? idListAe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListadoActividadEvaluada)) {
            return false;
        }
        ListadoActividadEvaluada other = (ListadoActividadEvaluada) object;
        if ((this.idListAe == null && other.idListAe != null) || (this.idListAe != null && !this.idListAe.equals(other.idListAe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ListadoActividadEvaluada[ idListAe=" + idListAe + " ]";
    }
    
}
