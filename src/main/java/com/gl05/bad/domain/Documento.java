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
@Table(name = "DOCUMENTO")
@NamedQueries({
    @NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d"),
    @NamedQuery(name = "Documento.findByIdDocumento", query = "SELECT d FROM Documento d WHERE d.idDocumento = :idDocumento"),
    @NamedQuery(name = "Documento.findByTipoFile", query = "SELECT d FROM Documento d WHERE d.tipoFile = :tipoFile"),
    @NamedQuery(name = "Documento.findByDocFile", query = "SELECT d FROM Documento d WHERE d.docFile = :docFile")})
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_DOCUMENTO")
    @SequenceGenerator(name = "S_DOCUMENTO", sequenceName = "S_DOCUMENTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_DOCUMENTO")
    private Long idDocumento;
    
    @Basic(optional = false)
    @Column(name = "TIPO_FILE")
    private String tipoFile;
        
    @Basic(optional = false)
    @Lob
    @Column(name = "DOC_FILE")
    private Blob docFile;
    
    @JoinColumn(name = "ID_LIST_DP", referencedColumnName = "ID_LIST_DP")
    @ManyToOne(optional = false)
    private ListadoDocumentacionPersonal idListDp;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumento != null ? idDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documento)) {
            return false;
        }
        Documento other = (Documento) object;
        if ((this.idDocumento == null && other.idDocumento != null) || (this.idDocumento != null && !this.idDocumento.equals(other.idDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Documento[ idDocumento=" + idDocumento + " ]";
    }
    
}
