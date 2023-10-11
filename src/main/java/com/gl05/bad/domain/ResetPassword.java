package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "RESET_PASSWORD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResetPassword.findAll", query = "SELECT u FROM ResetPassword u"),
    @NamedQuery(name = "ResetPassword.findByIdAsignacion", query = "SELECT u FROM ResetPassword u WHERE u.idAsignacion = :idAsignacion"),
    @NamedQuery(name = "ResetPassword.findByUsername", query = "SELECT u FROM ResetPassword u WHERE u.username = :username"),
    @NamedQuery(name = "ResetPassword.findByToken", query = "SELECT u FROM ResetPassword u WHERE u.token= :token"),
    @NamedQuery(name = "ResetPassword.findByFecha", query = "SELECT u FROM ResetPassword u WHERE u.fecha = :fecha")})
public class ResetPassword implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ASIGNACION")
    @SequenceGenerator(name = "S_RESET_PASSWORD", sequenceName = "S_RESET_PASSWORD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_RESET_PASSWORD")
    private Long idAsignacion;
    
    @Size(max = 100)
    @Column(name = "USERNAME")
    private String username;
    
    @Size(max = 300)
    @Column(name = "TOKEN")
    private String token;

    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idAsignacion);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ResetPassword)) {
            return false;
        }
        ResetPassword other = (ResetPassword) object;
        if ((this.idAsignacion == null && other.idAsignacion != null) || (this.idAsignacion != null && !this.idAsignacion.equals(other.idAsignacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ResetPassword[ idAsignacion=" + idAsignacion + " ]";
    }
}
