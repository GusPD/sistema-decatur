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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author william
 */
@Entity
@Table(name = "MALLA_CURRICULAR")
@NamedQueries({
    @NamedQuery(name = "MallaCurricular.findAll", query = "SELECT m FROM MallaCurricular m"),
    @NamedQuery(name = "MallaCurricular.findByIdMallaCurricular", query = "SELECT m FROM MallaCurricular m WHERE m.idMallaCurricular = :idMallaCurricular")})
public class MallaCurricular implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MALLA_CURRICULAR")
    private Long idMallaCurricular;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMallaCurricular")
    private Collection<Asignatura> asignaturaCollection;
    @JoinColumn(name = "ID_PLAN_ESTUDIO", referencedColumnName = "ID_PLAN_ESTUDIO")
    @ManyToOne
    private PlanEstudio idPlanEstudio;

    public MallaCurricular() {
    }

    public MallaCurricular(Long idMallaCurricular) {
        this.idMallaCurricular = idMallaCurricular;
    }

    public Long getIdMallaCurricular() {
        return idMallaCurricular;
    }

    public void setIdMallaCurricular(Long idMallaCurricular) {
        this.idMallaCurricular = idMallaCurricular;
    }

    public Collection<Asignatura> getAsignaturaCollection() {
        return asignaturaCollection;
    }

    public void setAsignaturaCollection(Collection<Asignatura> asignaturaCollection) {
        this.asignaturaCollection = asignaturaCollection;
    }

    public PlanEstudio getIdPlanEstudio() {
        return idPlanEstudio;
    }

    public void setIdPlanEstudio(PlanEstudio idPlanEstudio) {
        this.idPlanEstudio = idPlanEstudio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMallaCurricular != null ? idMallaCurricular.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MallaCurricular)) {
            return false;
        }
        MallaCurricular other = (MallaCurricular) object;
        if ((this.idMallaCurricular == null && other.idMallaCurricular != null) || (this.idMallaCurricular != null && !this.idMallaCurricular.equals(other.idMallaCurricular))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.MallaCurricular[ idMallaCurricular=" + idMallaCurricular + " ]";
    }
    
}
