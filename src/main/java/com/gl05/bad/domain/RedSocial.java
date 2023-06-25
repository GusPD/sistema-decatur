/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl05.bad.domain;

import java.io.Serializable;
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
import lombok.Data;

@Data
@Entity
@Table(name = "RED_SOCIAL")
@NamedQueries({
    @NamedQuery(name = "RedSocial.findAll", query = "SELECT r FROM RedSocial r"),
    @NamedQuery(name = "RedSocial.findByIdRs", query = "SELECT r FROM RedSocial r WHERE r.idRs = :idRs"),
    @NamedQuery(name = "RedSocial.findByNombreRs", query = "SELECT r FROM RedSocial r WHERE r.nombreRs = :nombreRs"),
    @NamedQuery(name = "RedSocial.findByLink", query = "SELECT r FROM RedSocial r WHERE r.link = :link")})
public class RedSocial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_RS")
    @SequenceGenerator(name = "S_RED_SOCIAL", sequenceName = "S_RED_SOCIAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_RED_SOCIAL")
    private Long idRs;
    @Column(name = "NOMBRE_RS", nullable=true)
    private String nombreRs;
    @Column(name = "LINK", nullable=true)
    private String link;
    @Column(name = "ID_LIST_RS", nullable=true)
    private Integer idListRs;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRs != null ? idRs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RedSocial)) {
            return false;
        }
        RedSocial other = (RedSocial) object;
        if ((this.idRs == null && other.idRs != null) || (this.idRs != null && !this.idRs.equals(other.idRs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.RedSocial[ idRs=" + idRs + " ]";
    }
    
}
