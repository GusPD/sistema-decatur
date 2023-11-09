package com.gl05.bad.domain;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "PAGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p"),
    @NamedQuery(name = "Pago.findByIdPago", query = "SELECT p FROM Pago p WHERE p.idPago = :idPago"),
    @NamedQuery(name = "Pago.findByFechaRegistro", query = "SELECT p FROM Pago p WHERE p.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Pago.findByFecha", query = "SELECT p FROM Pago p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Pago.findByEstado", query = "SELECT p FROM Pago p WHERE p.estado = :estado"),
    @NamedQuery(name = "Pago.findByComprobante", query = "SELECT p FROM Pago p WHERE p.comprobante = :comprobante"),
    @NamedQuery(name = "Pago.findByRecibo", query = "SELECT p FROM Pago p WHERE p.recibo = :recibo"),
    @NamedQuery(name = "Pago.findByTipo", query = "SELECT p FROM Pago p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "Pago.findByMonto", query = "SELECT p FROM Pago p WHERE p.monto = :monto"),
    @NamedQuery(name = "Pago.findByOtros", query = "SELECT p FROM Pago p WHERE p.otros = :otros"),
    @NamedQuery(name = "Pago.findByDescuento", query = "SELECT p FROM Pago p WHERE p.descuento = :descuento"),
    @NamedQuery(name = "Pago.findByObservaciones", query = "SELECT p FROM Pago p WHERE p.observaciones = :observaciones")})
public class Pago implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PAGO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fecha;
    @Column(name = "RECIBO")
    private BigInteger recibo;
    @Column(name = "ESTADO")
    private Boolean estado;
    @Size(max = 20)
    @Column(name = "COMPROBANTE")
    private String comprobante;
    @Size(max = 20)
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "MONTO")
    private double monto;
    @Column(name = "OTROS")
    private double otros;
    @Column(name = "DESCUENTO")
    private double descuento;
    @Size(max = 500)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @JoinColumn(name = "ID_CUENTA_BANCARIA")
    @ManyToOne
    private CuentaBancaria cuentaBancaria;
    @JoinColumn(name = "ID_VENTA")
    @ManyToOne
    private Venta venta;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPago != null ? idPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Pago[ idPago=" + idPago + " ]";
    }
    
}
