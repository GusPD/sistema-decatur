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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "REFERENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Referencia.findAll", query = "SELECT r FROM Referencia r"),
    @NamedQuery(name = "Referencia.findByIdReferencia", query = "SELECT r FROM Referencia r WHERE r.idReferencia = :idReferencia"),
    @NamedQuery(name = "Referencia.findByNombre", query = "SELECT r FROM Referencia r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Referencia.findByApellido", query = "SELECT r FROM Referencia r WHERE r.apellido = :apellido"),
    @NamedQuery(name = "Referencia.findByTelefono", query = "SELECT r FROM Referencia r WHERE r.telefono = :telefono"),
    @NamedQuery(name = "Referencia.findByCorreo", query = "SELECT r FROM Referencia r WHERE r.correo = :correo")})
public class Referencia implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_REFERENCIA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReferencia;
    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 200)
    @Column(name = "APELLIDO")
    private String apellido;
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 150)
    @Column(name = "CORREO")
    private String correo;
    @JoinColumn(name = "ID_PROPIETARIO", referencedColumnName = "ID_PROPIETARIO")
    private Long idPropietario;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReferencia != null ? idReferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Referencia)) {
            return false;
        }
        Referencia other = (Referencia) object;
        if ((this.idReferencia == null && other.idReferencia != null) || (this.idReferencia != null && !this.idReferencia.equals(other.idReferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Referencia[ idReferencia=" + idReferencia + " ]";
    }
    
}
