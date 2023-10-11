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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "CORREO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Correo.findAll", query = "SELECT c FROM Correo c"),
    @NamedQuery(name = "Correo.findByIdCorreo", query = "SELECT c FROM Correo c WHERE c.idCorreo = :idCorreo"),
    @NamedQuery(name = "Correo.findByTipo", query = "SELECT c FROM Correo c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "Correo.findByCorreo", query = "SELECT c FROM Correo c WHERE c.correo = :correo")})
public class Correo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CORREO")
    @SequenceGenerator(name = "S_CORREO", sequenceName = "S_CORREO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_CORREO")
    private Long idCorreo;
    @Size(max = 20)
    @Column(name = "TIPO")
    private String tipo;
    @Size(max = 150)
    @Column(name = "CORREO")
    private String correo;
    @JoinColumn(name = "ID_PROPIETARIO", referencedColumnName = "ID_PROPIETARIO")
    private Long idPropietario;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCorreo != null ? idCorreo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Correo)) {
            return false;
        }
        Correo other = (Correo) object;
        if ((this.idCorreo == null && other.idCorreo != null) || (this.idCorreo != null && !this.idCorreo.equals(other.idCorreo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Correo[ idCorreo=" + idCorreo + " ]";
    }
    
}
