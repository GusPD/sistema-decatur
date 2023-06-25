/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author william
 */
@Entity
@Table(name = "ESTUDIANTE_ASIGNATURA")
@NamedQueries({
    @NamedQuery(name = "EstudianteAsignatura.findAll", query = "SELECT e FROM EstudianteAsignatura e"),
    @NamedQuery(name = "EstudianteAsignatura.findByIdEstuAsi", query = "SELECT e FROM EstudianteAsignatura e WHERE e.idEstuAsi = :idEstuAsi")})
public class EstudianteAsignatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "S_ESTUDIANTE_ASIGNATURA", sequenceName = "S_ESTUDIANTE_ASIGNATURA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ESTUDIANTE_ASIGNATURA")
    @Column(name = "ID_ESTU_ASI")
    private Long idEstuAsi;
    @JoinColumn(name = "ID_ASIGNATURA", referencedColumnName = "ID_ASIGNATURA")
    @ManyToOne(optional = false)
    private Asignatura idAsignatura;
    @JoinColumn(name = "ID_ESTUD_CO", referencedColumnName = "ID_ESTUD_CO")
    @ManyToOne(optional = false)
    private EstudianteCohorte idEstudCo;

    public EstudianteAsignatura() {
    }

    public EstudianteAsignatura(Long idEstuAsi) {
        this.idEstuAsi = idEstuAsi;
    }

    public Long getIdEstuAsi() {
        return idEstuAsi;
    }

    public void setIdEstuAsi(Long idEstuAsi) {
        this.idEstuAsi = idEstuAsi;
    }

    public Asignatura getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(Asignatura idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public EstudianteCohorte getIdEstudCo() {
        return idEstudCo;
    }

    public void setIdEstudCo(EstudianteCohorte idEstudCo) {
        this.idEstudCo = idEstudCo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstuAsi != null ? idEstuAsi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstudianteAsignatura)) {
            return false;
        }
        EstudianteAsignatura other = (EstudianteAsignatura) object;
        if ((this.idEstuAsi == null && other.idEstuAsi != null) || (this.idEstuAsi != null && !this.idEstuAsi.equals(other.idEstuAsi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.EstudianteAsignatura[ idEstuAsi=" + idEstuAsi + " ]";
    }
    
}
