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

@Entity
@Table(name = "PROFESOR_ASIGNATURA")
@NamedQueries({
    @NamedQuery(name = "ProfesorAsignatura.findAll", query = "SELECT p FROM ProfesorAsignatura p"),
    @NamedQuery(name = "ProfesorAsignatura.findByIdProfesorAsig", query = "SELECT p FROM ProfesorAsignatura p WHERE p.idProfesorAsig = :idProfesorAsig")})
public class ProfesorAsignatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_PROFESOR_ASIGNATURA")
    @SequenceGenerator(name = "S_PROFESOR_ASIGNATURA", sequenceName = "S_PROFESOR_ASIGNATURA", allocationSize = 1)  
    @Basic(optional = false)
    @Column(name = "ID_PROFESOR_ASIG")
    private Long idProfesorAsig;
    @JoinColumn(name = "ID_ASIGNATURA", referencedColumnName = "ID_ASIGNATURA")
    @ManyToOne(optional = false)
    private Asignatura idAsignatura;
    @JoinColumn(name = "ID_PROFESOR", referencedColumnName = "ID_PROFESOR")
    @ManyToOne(optional = false)
    private ProfesorCohorte idProfesor;

    public ProfesorAsignatura() {
    }

    public ProfesorAsignatura(Long idProfesorAsig) {
        this.idProfesorAsig = idProfesorAsig;
    }

    public Long getIdProfesorAsig() {
        return idProfesorAsig;
    }

    public void setIdProfesorAsig(Long idProfesorAsig) {
        this.idProfesorAsig = idProfesorAsig;
    }

    public Asignatura getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(Asignatura idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public ProfesorCohorte getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(ProfesorCohorte idProfesor) {
        this.idProfesor = idProfesor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProfesorAsig != null ? idProfesorAsig.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfesorAsignatura)) {
            return false;
        }
        ProfesorAsignatura other = (ProfesorAsignatura) object;
        if ((this.idProfesorAsig == null && other.idProfesorAsig != null) || (this.idProfesorAsig != null && !this.idProfesorAsig.equals(other.idProfesorAsig))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ProfesorAsignatura[ idProfesorAsig=" + idProfesorAsig + " ]";
    }
    
}
