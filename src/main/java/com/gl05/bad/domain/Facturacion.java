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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "FACTURACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facturacion.findAll", query = "SELECT f FROM Facturacion f"),
    @NamedQuery(name = "Facturacion.findByIdDocumento", query = "SELECT f FROM Facturacion f WHERE f.idFacturacion = :idFacturacion"),
    @NamedQuery(name = "Facturacion.findByNombre", query = "SELECT f FROM Facturacion f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "Facturacion.findByRegistro", query = "SELECT f FROM Facturacion f WHERE f.registro = :registro"),
    @NamedQuery(name = "Facturacion.findByNit", query = "SELECT f FROM Facturacion f WHERE f.nit = :nit"),
    @NamedQuery(name = "Facturacion.findByDui", query = "SELECT f FROM Facturacion f WHERE f.dui = :dui"),
    @NamedQuery(name = "Facturacion.findByDireccion", query = "SELECT f FROM Facturacion f WHERE f.direccion = :direccion"),
    @NamedQuery(name = "Facturacion.findByGiro", query = "SELECT f FROM Facturacion f WHERE f.giro = :giro")})
public class Facturacion implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_FACTURACION")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacturacion;
    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 12)
    @Column(name = "N_REGISTRO")
    private String registro;
    @Size(max = 14)
    @Column(name = "NIT")
    private String nit;
    @Size(max = 9)
    @Column(name = "DUI")
    private String dui;
    @Size(max = 300)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 200)
    @Column(name = "GIRO")
    private String giro;
    @ManyToOne
    @JoinColumn(name = "ID_VENTA", referencedColumnName = "ID_VENTA")
    private Venta venta;
    @ManyToOne
    @JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID_MUNICIPIO")
    private Municipio municipio;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFacturacion != null ? idFacturacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Facturacion)) {
            return false;
        }
        Facturacion other = (Facturacion) object;
        if ((this.idFacturacion == null && other.idFacturacion != null) || (this.idFacturacion != null && !this.idFacturacion.equals(other.idFacturacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Facturacion[ idFacturacion=" + idFacturacion + " ]";
    }
    
}
