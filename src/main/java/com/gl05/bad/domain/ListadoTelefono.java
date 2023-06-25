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
@Table(name = "LISTADO_TELEFONO")
@NamedQueries({
    @NamedQuery(name = "ListadoTelefono.findAll", query = "SELECT l FROM ListadoTelefono l"),
    @NamedQuery(name = "ListadoTelefono.findByIdListTelefono", query = "SELECT l FROM ListadoTelefono l WHERE l.idListTelefono = :idListTelefono")})
public class ListadoTelefono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LIST_TELEFONO")
    private Long idListTelefono;
    @OneToMany(mappedBy = "idListTelefono")
    private Collection<AspiranteProfesor> aspiranteProfesorCollection;
    @OneToMany(mappedBy = "idListTelefono")
    private Collection<CoordinadorAcademico> coordinadorAcademicoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idListTelefono")
    private Collection<Telefono> telefonoCollection;

    public ListadoTelefono() {
    }

    public ListadoTelefono(Long idListTelefono) {
        this.idListTelefono = idListTelefono;
    }

    public Long getIdListTelefono() {
        return idListTelefono;
    }

    public void setIdListTelefono(Long idListTelefono) {
        this.idListTelefono = idListTelefono;
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

    public Collection<Telefono> getTelefonoCollection() {
        return telefonoCollection;
    }

    public void setTelefonoCollection(Collection<Telefono> telefonoCollection) {
        this.telefonoCollection = telefonoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idListTelefono != null ? idListTelefono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListadoTelefono)) {
            return false;
        }
        ListadoTelefono other = (ListadoTelefono) object;
        if ((this.idListTelefono == null && other.idListTelefono != null) || (this.idListTelefono != null && !this.idListTelefono.equals(other.idListTelefono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ListadoTelefono[ idListTelefono=" + idListTelefono + " ]";
    }
    
}
