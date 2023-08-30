/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import javax.persistence.SequenceGenerator;
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
    @NamedQuery(name = "Venta.findByPlazo", query = "SELECT v FROM Venta v WHERE v.plazo = :plazo"),
    @NamedQuery(name = "Venta.findByTasa", query = "SELECT v FROM Venta v WHERE v.tasa = :tasa"),
    @NamedQuery(name = "Venta.findByCuotaKi", query = "SELECT v FROM Venta v WHERE v.cuotaKi = :cuotaKi"),
    @NamedQuery(name = "Venta.findByCuotaMantenimiento", query = "SELECT v FROM Venta v WHERE v.cuotaMantenimiento = :cuotaMantenimiento"),
    @NamedQuery(name = "Venta.findByMultaMantenimiento", query = "SELECT v FROM Venta v WHERE v.multaMantenimiento = :multaMantenimiento"),
    @NamedQuery(name = "Venta.findByMultaFinanciamiento", query = "SELECT v FROM Venta v WHERE v.multaFinanciamiento = :multaFinanciamiento"),
    @NamedQuery(name = "Venta.findByEstado", query = "SELECT v FROM Venta v WHERE v.estado = :estado")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VENTA")
    @SequenceGenerator(name = "S_VENTA", sequenceName = "S_VENTA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_VENTA")
    private Long idVenta;
    @JoinColumn(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Column(name = "PLAZO")
    private Integer plazo;
    @Column(name = "TASA")
    private BigDecimal tasa;
    @Column(name = "CUOTA_KI")
    private BigDecimal cuotaKi;
    @Column(name = "CUOTA_MANTENIMIENTO")
    private BigDecimal cuotaMantenimiento;
    @Column(name = "MULTA_MANTENIMIENTO")
    private BigDecimal multaMantenimiento;
    @Column(name = "MULTA_FINANCIAMIENTO")
    private BigDecimal multaFinanciamiento;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
