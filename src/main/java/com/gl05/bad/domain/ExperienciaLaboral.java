/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl05.bad.domain;

import java.io.Serializable;
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
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "EXPERIENCIA_LABORAL")
@NamedQueries({
    @NamedQuery(name = "ExperienciaLaboral.findAll", query = "SELECT e FROM ExperienciaLaboral e"),
    @NamedQuery(name = "ExperienciaLaboral.findByIdEl", query = "SELECT e FROM ExperienciaLaboral e WHERE e.idEl = :idEl"),
    @NamedQuery(name = "ExperienciaLaboral.findByNombreInstitucion", query = "SELECT e FROM ExperienciaLaboral e WHERE e.nombreInstitucion = :nombreInstitucion"),
    @NamedQuery(name = "ExperienciaLaboral.findByCargo", query = "SELECT e FROM ExperienciaLaboral e WHERE e.cargo = :cargo"),
    @NamedQuery(name = "ExperienciaLaboral.findByFunciones", query = "SELECT e FROM ExperienciaLaboral e WHERE e.funciones = :funciones"),
    @NamedQuery(name = "ExperienciaLaboral.findByPeriodoInicio", query = "SELECT e FROM ExperienciaLaboral e WHERE e.periodoInicio = :periodoInicio"),
    @NamedQuery(name = "ExperienciaLaboral.findByPeriodoFin", query = "SELECT e FROM ExperienciaLaboral e WHERE e.periodoFin = :periodoFin")})
public class ExperienciaLaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_EL")
    @SequenceGenerator(name = "S_EXPERIENCIA_LABORAL", sequenceName = "S_EXPERIENCIA_LABORAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_EXPERIENCIA_LABORAL")
    private Long idEl;
    @Column(name = "NOMBRE_INSTITUCION", nullable=true)
    private String nombreInstitucion;
    @Column(name = "CARGO", nullable=true)
    private String cargo;
    @Column(name = "FUNCIONES", nullable=true)
    private String funciones;
    @Column(name = "PERIODO_INICIO", nullable=true)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yy")
    private Date periodoInicio;
    @Column(name = "PERIODO_FIN", nullable=true)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yy")
    private Date periodoFin;
    @Column(name = "ID_LIST_EL", nullable=true)
    private Integer idListEl;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEl != null ? idEl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExperienciaLaboral)) {
            return false;
        }
        ExperienciaLaboral other = (ExperienciaLaboral) object;
        if ((this.idEl == null && other.idEl != null) || (this.idEl != null && !this.idEl.equals(other.idEl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ExperienciaLaboral[ idEl=" + idEl + " ]";
    }
    
}
