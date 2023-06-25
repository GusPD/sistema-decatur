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
@Table(name = "LISTADO_TITULOS_ACADEMICOS")
@NamedQueries({
    @NamedQuery(name = "ListadoTitulosAcademicos.findAll", query = "SELECT l FROM ListadoTitulosAcademicos l"),
    @NamedQuery(name = "ListadoTitulosAcademicos.findByIdListTa", query = "SELECT l FROM ListadoTitulosAcademicos l WHERE l.idListTa = :idListTa")})
public class ListadoTitulosAcademicos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LIST_TA")
    private Long idListTa;
    
    @OneToMany(mappedBy = "idListTa")
    private Collection<AspiranteProfesor> aspiranteProfesorCollection;
    
    @OneToMany(mappedBy = "idListTa")
    private Collection<CoordinadorAcademico> coordinadorAcademicoCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idListTa")
    private Collection<AtestadoTa> atestadoTaCollection;

    public ListadoTitulosAcademicos() {
    }

    public ListadoTitulosAcademicos(Long idListTa) {
        this.idListTa = idListTa;
    }

    public Long getIdListTa() {
        return idListTa;
    }

    public void setIdListTa(Long idListTa) {
        this.idListTa = idListTa;
    }

    public Collection<AspiranteProfesor> getAspiranteProfesorCollection() {
        return aspiranteProfesorCollection;
    }

    public void setAspiranteProfesorCollection(Collection<AspiranteProfesor> aspiranteProfesorCollection) {
        this.aspiranteProfesorCollection = aspiranteProfesorCollection;
    }

    public Collection<CoordinadorAcademico> getCoordinadorAcademicoCollection() {
        return coordinadorAcademicoCollection;
    }

    public void setCoordinadorAcademicoCollection(Collection<CoordinadorAcademico> coordinadorAcademicoCollection) {
        this.coordinadorAcademicoCollection = coordinadorAcademicoCollection;
    }

    public Collection<AtestadoTa> getAtestadoTaCollection() {
        return atestadoTaCollection;
    }

    public void setAtestadoTaCollection(Collection<AtestadoTa> atestadoTaCollection) {
        this.atestadoTaCollection = atestadoTaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idListTa != null ? idListTa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListadoTitulosAcademicos)) {
            return false;
        }
        ListadoTitulosAcademicos other = (ListadoTitulosAcademicos) object;
        if ((this.idListTa == null && other.idListTa != null) || (this.idListTa != null && !this.idListTa.equals(other.idListTa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ListadoTitulosAcademicos[ idListTa=" + idListTa + " ]";
    }
    
}
