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
@Table(name = "VISITANTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visitante.findAll", query = "SELECT v FROM Visitante v"),
    @NamedQuery(name = "Visitante.findByIdVisitante", query = "SELECT v FROM Visitante v WHERE v.idVisitante = :idVisitante"),
    @NamedQuery(name = "Visitante.findByRol", query = "SELECT v FROM Visitante v WHERE v.rol = :rol"),
    @NamedQuery(name = "Visitante.findByEmpleador", query = "SELECT v FROM Visitante v WHERE v.empleador = :empleador")})
public class Visitante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VISITANTE")
    @SequenceGenerator(name = "S_VISITANTE", sequenceName = "S_VISITANTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_VISITANTE")
    private Long idVisitante;
    @Size(max = 20)
    @Column(name = "ROL")
    private String rol;
    @Size(max = 200)
    @Column(name = "EMPLEADOR")
    private String empleador;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @ManyToOne
    private Persona persona;
    @Column(name = "ID_LIST_DOCUMENTO")
    private Integer idDocumento;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVisitante != null ? idVisitante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Visitante)) {
            return false;
        }
        Visitante other = (Visitante) object;
        if ((this.idVisitante == null && other.idVisitante != null) || (this.idVisitante != null && !this.idVisitante.equals(other.idVisitante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Visitante[ idVisitante=" + idVisitante + " ]";
    }
    
}
