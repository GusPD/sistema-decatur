package com.gl05.bad.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "PROPIETARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propietario.findAll", query = "SELECT p FROM Propietario p"),
    @NamedQuery(name = "Propietario.findByIdPropietario", query = "SELECT p FROM Propietario p WHERE p.idPropietario = :idPropietario"),
    @NamedQuery(name = "Propietario.findByPersona", query = "SELECT p FROM Propietario p WHERE p.persona = :persona"),
    @NamedQuery(name = "Propietario.findByIdDocumento", query = "SELECT p FROM Propietario p WHERE p.idDocumento = :idDocumento"),
    @NamedQuery(name = "Propietario.findByProfesion", query = "SELECT p FROM Propietario p WHERE p.profesion = :profesion"),
    @NamedQuery(name = "Propietario.findByDireccionCasa", query = "SELECT p FROM Propietario p WHERE p.direccionCasa = :direccionCasa"),
    @NamedQuery(name = "Propietario.findByLugarTrabajo", query = "SELECT p FROM Propietario p WHERE p.lugarTrabajo = :lugarTrabajo"),
    @NamedQuery(name = "Propietario.findByDireccionTrabajo", query = "SELECT p FROM Propietario p WHERE p.direccionTrabajo = :direccionTrabajo")})
public class Propietario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROPIETARIO")
    @SequenceGenerator(name = "S_PROPIETARIO", sequenceName = "S_PROPIETARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_PROPIETARIO")
    private Long idPropietario;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @OneToOne
    private Persona persona;
    @Column(name = "ID_LIST_DOCUMENTO")
    private Integer idDocumento;
    @Size(max = 200)
    @Column(name = "PROFESION")
    private String profesion;
    @Size(max = 300)
    @Column(name = "DIRECCION_CASA")
    private String direccionCasa;
    @Size(max = 200)
    @Column(name = "LUGAR_TRABAJO")
    private String lugarTrabajo;
    @Size(max = 300)
    @Column(name = "DIRECCION_TRABAJO")
    private String direccionTrabajo;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPropietario != null ? idPropietario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Propietario)) {
            return false;
        }
        Propietario other = (Propietario) object;
        if ((this.idPropietario == null && other.idPropietario != null) || (this.idPropietario != null && !this.idPropietario.equals(other.idPropietario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Propietario[ idPropietario=" + idPropietario + " ]";
    }
    
}
