/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author william
 */
@Entity
@Table(name = "LISTADO_EXPERIENCIA_LABORAL")
@NamedQueries({
    @NamedQuery(name = "ListadoExperienciaLaboral.findAll", query = "SELECT l FROM ListadoExperienciaLaboral l"),
    @NamedQuery(name = "ListadoExperienciaLaboral.findByIdListEl", query = "SELECT l FROM ListadoExperienciaLaboral l WHERE l.idListEl = :idListEl")})
public class ListadoExperienciaLaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LIST_EL")
    private Long idListEl;
    @OneToMany(mappedBy = "idListEl")
    private Collection<AspiranteProfesor> aspiranteProfesorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idListEl")
    private Collection<ExperienciaLaboral> experienciaLaboralCollection;

    public ListadoExperienciaLaboral() {
    }

    public ListadoExperienciaLaboral(Long idListEl) {
        this.idListEl = idListEl;
    }

    public Long getIdListEl() {
        return idListEl;
    }

    public void setIdListEl(Long idListEl) {
        this.idListEl = idListEl;
    }

    public Collection<AspiranteProfesor> getAspiranteProfesorCollection() {
        return aspiranteProfesorCollection;
    }

    public void setAspiranteProfesorCollection(Collection<AspiranteProfesor> aspiranteProfesorCollection) {
        this.aspiranteProfesorCollection = aspiranteProfesorCollection;
    }

    public Collection<ExperienciaLaboral> getExperienciaLaboralCollection() {
        return experienciaLaboralCollection;
    }

    public void setExperienciaLaboralCollection(Collection<ExperienciaLaboral> experienciaLaboralCollection) {
        this.experienciaLaboralCollection = experienciaLaboralCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idListEl != null ? idListEl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListadoExperienciaLaboral)) {
            return false;
        }
        ListadoExperienciaLaboral other = (ListadoExperienciaLaboral) object;
        if ((this.idListEl == null && other.idListEl != null) || (this.idListEl != null && !this.idListEl.equals(other.idListEl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ListadoExperienciaLaboral[ idListEl=" + idListEl + " ]";
    }
    
}
