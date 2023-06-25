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
@Table(name = "LISTADO_RED_SOCIAL")
@NamedQueries({
    @NamedQuery(name = "ListadoRedSocial.findAll", query = "SELECT l FROM ListadoRedSocial l"),
    @NamedQuery(name = "ListadoRedSocial.findByIdListRs", query = "SELECT l FROM ListadoRedSocial l WHERE l.idListRs = :idListRs")})
public class ListadoRedSocial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LIST_RS")
    private Long idListRs;
    @OneToMany(mappedBy = "idListRs")
    private Collection<AspiranteProfesor> aspiranteProfesorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idListRs")
    private Collection<RedSocial> redSocialCollection;

    public ListadoRedSocial() {
    }

    public ListadoRedSocial(Long idListRs) {
        this.idListRs = idListRs;
    }

    public Long getIdListRs() {
        return idListRs;
    }

    public void setIdListRs(Long idListRs) {
        this.idListRs = idListRs;
    }

    public Collection<AspiranteProfesor> getAspiranteProfesorCollection() {
        return aspiranteProfesorCollection;
    }

    public void setAspiranteProfesorCollection(Collection<AspiranteProfesor> aspiranteProfesorCollection) {
        this.aspiranteProfesorCollection = aspiranteProfesorCollection;
    }

    public Collection<RedSocial> getRedSocialCollection() {
        return redSocialCollection;
    }

    public void setRedSocialCollection(Collection<RedSocial> redSocialCollection) {
        this.redSocialCollection = redSocialCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idListRs != null ? idListRs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListadoRedSocial)) {
            return false;
        }
        ListadoRedSocial other = (ListadoRedSocial) object;
        if ((this.idListRs == null && other.idListRs != null) || (this.idListRs != null && !this.idListRs.equals(other.idListRs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ListadoRedSocial[ idListRs=" + idListRs + " ]";
    }
    
}
