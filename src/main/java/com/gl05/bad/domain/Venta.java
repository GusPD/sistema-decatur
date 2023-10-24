package com.gl05.bad.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "VENTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v"),
    @NamedQuery(name = "Venta.findByIdVenta", query = "SELECT v FROM Venta v WHERE v.idVenta = :idVenta"),
    @NamedQuery(name = "Venta.findByNombre", query = "SELECT v FROM Venta v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "Venta.findByFecha", query = "SELECT v FROM Venta v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "Venta.findByPrecio", query = "SELECT v FROM Venta v WHERE v.precio = :precio"),
    @NamedQuery(name = "Venta.findByDescuento", query = "SELECT v FROM Venta v WHERE v.descuento = :descuento"),
    @NamedQuery(name = "Venta.findByMonto", query = "SELECT v FROM Venta v WHERE v.monto = :monto"),
    @NamedQuery(name = "Venta.findByEstado", query = "SELECT v FROM Venta v WHERE v.estado = :estado")})
public class Venta implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VENTA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;
    @JoinColumn(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha;
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Column(name = "MONTO")
    private BigDecimal monto;
    @JoinColumn(name = "ID_LIST_DOCUMENTO")
    private Integer idListDocumento;
    @JoinColumn(name = "ESTADO")
    private String estado;
    @ManyToOne
    @JoinColumn(name = "ID_TERRENO", referencedColumnName = "ID_TERRENO")
    private Terreno terreno;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenta != null ? idVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.idVenta == null && other.idVenta != null) || (this.idVenta != null && !this.idVenta.equals(other.idVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Venta[ idVenta=" + idVenta + " ]";
    }
    
}
