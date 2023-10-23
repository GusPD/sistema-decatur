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
@Table(name = "CUENTA_BANCARIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaBancaria.findAll", query = "SELECT c FROM CuentaBancaria c"),
    @NamedQuery(name = "CuentaBancaria.findByIdCuenta", query = "SELECT c FROM CuentaBancaria c WHERE c.idCuenta = :idCuenta"),
    @NamedQuery(name = "CuentaBancaria.findByNombre", query = "SELECT c FROM CuentaBancaria c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CuentaBancaria.findByTitular", query = "SELECT c FROM CuentaBancaria c WHERE c.titular = :titular"),
    @NamedQuery(name = "CuentaBancaria.findByBanco", query = "SELECT c FROM CuentaBancaria c WHERE c.banco = :banco"),
    @NamedQuery(name = "CuentaBancaria.findByTipo", query = "SELECT c FROM CuentaBancaria c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "CuentaBancaria.findByCuenta", query = "SELECT c FROM CuentaBancaria c WHERE c.cuenta = :cuenta")})
public class CuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CUENTA_BANCARIA")
    @SequenceGenerator(name = "S_CUENTA_BANCARIA", sequenceName = "S_CUENTA_BANCARIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_CUENTA_BANCARIA")
    private Long idCuenta;
    @Size(max = 200)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 200)
    @Column(name = "TITULAR")
    private String titular;
    @Size(max = 200)
    @Column(name = "BANCO")
    private String banco;
    @Size(max = 20)
    @Column(name = "TIPO")
    private String tipo;
    @Size(max = 20)
    @Column(name = "CUENTA")
    private String cuenta;
    @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECTO")
    @ManyToOne
    private Proyecto proyecto;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CuentaBancaria)) {
            return false;
        }
        CuentaBancaria other = (CuentaBancaria) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.CuentaBancaria[ idCuenta=" + idCuenta + " ]";
    }
    
}
