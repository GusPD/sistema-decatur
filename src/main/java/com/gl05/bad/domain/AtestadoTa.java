package com.gl05.bad.domain;

import java.io.Serializable;
import java.sql.Blob;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ATESTADO_TA")
@NamedQueries({
    @NamedQuery(name = "AtestadoTa.findAll", query = "SELECT a FROM AtestadoTa a"),
    @NamedQuery(name = "AtestadoTa.findByIdAtestadoTa", query = "SELECT a FROM AtestadoTa a WHERE a.idAtestadoTa = :idAtestadoTa"),
    @NamedQuery(name = "AtestadoTa.findByNombreAta", query = "SELECT a FROM AtestadoTa a WHERE a.nombreAta = :nombreAta"),
    @NamedQuery(name = "AtestadoTa.findByTipoAta", query = "SELECT a FROM AtestadoTa a WHERE a.tipoAta = :tipoAta"),
    @NamedQuery(name = "AtestadoTa.findByInstitucion", query = "SELECT a FROM AtestadoTa a WHERE a.institucion = :institucion"),
    @NamedQuery(name = "AtestadoTa.findByAnioTitulacion", query = "SELECT a FROM AtestadoTa a WHERE a.anioTitulacion = :anioTitulacion")})
public class AtestadoTa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_ATESTADO_TA")
    @SequenceGenerator(name = "S_ATESTADO_TA", sequenceName = "S_ATESTADO_TA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ATESTADO_TA")
    private Long idAtestadoTa;
  
    @Column(name = "NOMBRE_ATA")
    private String nombreAta;
    
    @Column(name = "TIPO_ATA")
    private String tipoAta;
    
    @Column(name = "INSTITUCION")
    private String institucion;
    
    @Lob
    @Column(name = "ARCHIVO_ATA")
    private Blob archivoAta;
    
     @Column(name = "ANIO_TITULACION")
    private Integer anioTitulacion;
    
    @Column(name = "ID_LIST_TA")
    private Integer idListTa;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtestadoTa != null ? idAtestadoTa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtestadoTa)) {
            return false;
        }
        AtestadoTa other = (AtestadoTa) object;
        if ((this.idAtestadoTa == null && other.idAtestadoTa != null) || (this.idAtestadoTa != null && !this.idAtestadoTa.equals(other.idAtestadoTa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.AtestadoTa[ idAtestadoTa=" + idAtestadoTa + " ]";
    }
    
}
