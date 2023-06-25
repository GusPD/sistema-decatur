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
@Table(name = "LISTADO_CORREO")
@NamedQueries({
    @NamedQuery(name = "ListadoCorreo.findAll", query = "SELECT l FROM ListadoCorreo l"),
    @NamedQuery(name = "ListadoCorreo.findByIdListCorreo", query = "SELECT l FROM ListadoCorreo l WHERE l.idListCorreo = :idListCorreo")})
public class ListadoCorreo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LIST_CORREO")
    private Long idListCorreo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idListCorreo")
    private Collection<Correo> correoCollection;
    @OneToMany(mappedBy = "idListCorreo")
    private Collection<AspiranteProfesor> aspiranteProfesorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idListCorreo")
    private Collection<CoordinadorAcademico> coordinadorAcademicoCollection;

    public ListadoCorreo() {
    }

    public ListadoCorreo(Long idListCorreo) {
        this.idListCorreo = idListCorreo;
    }

    public Long getIdListCorreo() {
        return idListCorreo;
    }

    public void setIdListCorreo(Long idListCorreo) {
        this.idListCorreo = idListCorreo;
    }

    public Collection<Correo> getCorreoCollection() {
        return correoCollection;
    }

    public void setCorreoCollection(Collection<Correo> correoCollection) {
        this.correoCollection = correoCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idListCorreo != null ? idListCorreo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListadoCorreo)) {
            return false;
        }
        ListadoCorreo other = (ListadoCorreo) object;
        if ((this.idListCorreo == null && other.idListCorreo != null) || (this.idListCorreo != null && !this.idListCorreo.equals(other.idListCorreo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ListadoCorreo[ idListCorreo=" + idListCorreo + " ]";
    }
    
}
