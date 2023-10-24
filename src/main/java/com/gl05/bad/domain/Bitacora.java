package com.gl05.bad.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "BITACORA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bitacora.findAll", query = "SELECT b FROM Bitacora b"),
    @NamedQuery(name = "Bitacora.findByIdBitacora", query = "SELECT b FROM Bitacora b WHERE b.idBitacora = :idBitacora"),
    @NamedQuery(name = "Bitacora.findByUsername", query = "SELECT b FROM Bitacora b WHERE b.username = :username"),
    @NamedQuery(name = "Bitacora.findByEvento", query = "SELECT b FROM Bitacora b WHERE b.evento = :evento"),
    @NamedQuery(name = "Bitacora.findByHora", query = "SELECT b FROM Bitacora b WHERE b.hora = :hora"),
    @NamedQuery(name = "Bitacora.findByIpEquipo", query = "SELECT b FROM Bitacora b WHERE b.ipEquipo = :ipEquipo")})
public class Bitacora implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BITACORA")
    private Integer idBitacora;
    @Size(max = 50)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 50)
    @Column(name = "EVENTO")
    private String evento;
    @Column(name = "HORA")
    private LocalDateTime hora;
    @Size(max = 15)
    @Column(name = "IP_EQUIPO")
    private String ipEquipo;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacora != null ? idBitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Bitacora)) {
            return false;
        }
        Bitacora other = (Bitacora) object;
        if ((this.idBitacora == null && other.idBitacora != null) || (this.idBitacora != null && !this.idBitacora.equals(other.idBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Bitacora[ idBitacora=" + idBitacora + " ]";
    }
    
}
