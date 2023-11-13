package com.gl05.bad.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
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
@Table(name = "CUOTA_FINANCIAMIENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuotaFinanciamiento.findAll", query = "SELECT c FROM CuotaFinanciamiento c"),
    @NamedQuery(name = "CuotaFinanciamiento.findByIdCuotaFinanciamiento", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.idCuotaFinanciamiento = :idCuotaFinanciamiento"),
    @NamedQuery(name = "CuotaFinanciamiento.findByFechaRegistro", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "CuotaFinanciamiento.findByFechaCuota", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.fechaCuota = :fechaCuota"),
    @NamedQuery(name = "CuotaFinanciamiento.findByDiasInteresCorriente", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.diasInteresCorriente = :diasInteresCorriente"),
    @NamedQuery(name = "CuotaFinanciamiento.findByInteresCorriente", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.interesCorriente = :interesCorriente"),
    @NamedQuery(name = "CuotaFinanciamiento.findByDiasInteresMora", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.diasInteresMora = :diasInteresMora"),
    @NamedQuery(name = "CuotaFinanciamiento.findByInteresMora", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.interesMora = :interesMora"),
    @NamedQuery(name = "CuotaFinanciamiento.findByPagadoInteres", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.pagadoInteres = :pagadoInteres"),
    @NamedQuery(name = "CuotaFinanciamiento.findByPendienteInteres", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.pendienteInteres = :pendienteInteres"),
    @NamedQuery(name = "CuotaFinanciamiento.findByComision", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.comision = :comision"),
    @NamedQuery(name = "CuotaFinanciamiento.findByRecargo", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.recargo = :recargo"),
    @NamedQuery(name = "CuotaFinanciamiento.findByOtros", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.otros = :otros"),
    @NamedQuery(name = "CuotaFinanciamiento.findByPagadoOtros", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.pagadoOtros = :pagadoOtros"),
    @NamedQuery(name = "CuotaFinanciamiento.findByPendienteOtros", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.pendienteOtros = :pendienteOtros"),
    @NamedQuery(name = "CuotaFinanciamiento.findByCapital", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.capital = :capital"),
    @NamedQuery(name = "CuotaFinanciamiento.findBySaldo", query = "SELECT c FROM CuotaFinanciamiento c WHERE c.saldo = :saldo")})
public class CuotaFinanciamiento implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CUOTA_FINANCIAMIENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuotaFinanciamiento;
    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;
    @Column(name = "FECHA_CUOTA")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaCuota;
    @Column(name = "DIAS_INTERES_CORRIENTE")
    private BigInteger diasInteresCorriente;
    @Column(name = "INTERES_CORRIENTE")
    private BigDecimal interesCorriente;
    @Column(name = "DIAS_INTERES_MORA")
    private BigInteger diasInteresMora;
    @Column(name = "INTERES_MORA")
    private BigDecimal interesMora;
    @Column(name = "PAGADO_INTERES")
    private BigDecimal pagadoInteres;
    @Column(name = "PENDIENTE_INTERES")
    private BigDecimal pendienteInteres;
    @Column(name = "COMISION")
    private BigDecimal comision;
    @Column(name = "RECARGO")
    private BigDecimal recargo;
    @Column(name = "OTROS")
    private BigDecimal otros;
    @Column(name = "PAGADO_OTROS")
    private BigDecimal pagadoOtros;
    @Column(name = "PENDIENTE_OTROS")
    private BigDecimal pendienteOtros;
    @Column(name = "CAPITAL")
    private BigDecimal capital;
    @Column(name = "SALDO")
    private BigDecimal saldo;
    @JoinColumn(name = "ID_PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne
    private Pago pago;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuotaFinanciamiento != null ? idCuotaFinanciamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CuotaFinanciamiento)) {
            return false;
        }
        CuotaFinanciamiento other = (CuotaFinanciamiento) object;
        if ((this.idCuotaFinanciamiento == null && other.idCuotaFinanciamiento != null) || (this.idCuotaFinanciamiento != null && !this.idCuotaFinanciamiento.equals(other.idCuotaFinanciamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.CuotaFinanciamiento[ idCuotaFinanciamiento=" + idCuotaFinanciamiento + " ]";
    }
    
}
