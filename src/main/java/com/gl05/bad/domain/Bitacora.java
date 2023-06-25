package com.gl05.bad.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@NamedQueries({
    @NamedQuery(name = "Bitacora.findAll", query = "SELECT b FROM Bitacora b"),
    @NamedQuery(name = "Bitacora.findByIdBitacora", query = "SELECT b FROM Bitacora b WHERE b.idBitacora = :idBitacora"),
    @NamedQuery(name = "Bitacora.findByNombreEvento", query = "SELECT b FROM Bitacora b WHERE b.nombreEvento = :nombreEvento"),
    @NamedQuery(name = "Bitacora.findByHoraEvento", query = "SELECT b FROM Bitacora b WHERE b.horaEvento = :horaEvento"),
    @NamedQuery(name = "Bitacora.findByIpEquipo", query = "SELECT b FROM Bitacora b WHERE b.ipEquipo = :ipEquipo")})
public class Bitacora implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_BITACORA")
    @SequenceGenerator(name = "S_BITACORA", sequenceName = "S_BITACORA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_BITACORA")
    private Long idBitacora;    
    
    @Basic(optional = false)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    
    @Basic(optional = false)
    @Column(name = "NOMBRE_EVENTO")
    private String nombreEvento;
    
    @Basic(optional = false)
    @Column(name = "HORA_EVENTO")
    private LocalDateTime horaEvento;
    
    @Basic(optional = false)
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
