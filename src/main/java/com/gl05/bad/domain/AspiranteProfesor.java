/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gl05.bad.domain;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "ASPIRANTE_PROFESOR")
@NamedQueries({
    @NamedQuery(name = "AspiranteProfesor.findAll", query = "SELECT a FROM AspiranteProfesor a"),
    @NamedQuery(name = "AspiranteProfesor.findByIdAspiranteProfesor", query = "SELECT a FROM AspiranteProfesor a WHERE a.idAspiranteProfesor = :idAspiranteProfesor"),
    @NamedQuery(name = "AspiranteProfesor.findByCodAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.codAp = :codAp"),
    @NamedQuery(name = "AspiranteProfesor.findByNombresAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.nombresAp = :nombresAp"),
    @NamedQuery(name = "AspiranteProfesor.findByApellidosAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.apellidosAp = :apellidosAp"),
    @NamedQuery(name = "AspiranteProfesor.findBySexoAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.sexoAp = :sexoAp"),
    @NamedQuery(name = "AspiranteProfesor.findByGeneroAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.generoAp = :generoAp"),
    @NamedQuery(name = "AspiranteProfesor.findByFechaNacAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.fechaNacAp = :fechaNacAp"),
    @NamedQuery(name = "AspiranteProfesor.findByEstadoCivilAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.estadoCivilAp = :estadoCivilAp"),
    @NamedQuery(name = "AspiranteProfesor.findByNacionalidadAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.nacionalidadAp = :nacionalidadAp"),
    @NamedQuery(name = "AspiranteProfesor.findByDuiAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.duiAp = :duiAp"),
    @NamedQuery(name = "AspiranteProfesor.findByNitAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.nitAp = :nitAp"),
    @NamedQuery(name = "AspiranteProfesor.findByNupAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.nupAp = :nupAp"),
    @NamedQuery(name = "AspiranteProfesor.findByPasaporteAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.pasaporteAp = :pasaporteAp"),
    @NamedQuery(name = "AspiranteProfesor.findByDocPersonalAp", query = "SELECT a FROM AspiranteProfesor a WHERE a.docPersonalAp = :docPersonalAp")})
public class AspiranteProfesor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_ASPIRANTE_PROFESOR")
    @SequenceGenerator(name = "S_ASPIRANTE_PROFESOR", sequenceName = "S_ASPIRANTE_PROFESOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ASPIRANTE_PROFESOR")
    private Long idAspiranteProfesor;
    @Column(name = "COD_AP", nullable=true)
    private String codAp;
    @Column(name = "NOMBRES_AP", nullable=true)
    private String nombresAp;
    @Column(name = "APELLIDOS_AP", nullable=true)
    private String apellidosAp;
    @Column(name = "SEXO_AP", nullable=true)
    private String sexoAp;
    @Column(name = "GENERO_AP", nullable=true)
    private String generoAp;
    @Column(name = "FECHA_NAC_AP", nullable=true)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yy")
    private Date fechaNacAp;
    @Column(name = "ESTADO_CIVIL_AP", nullable=true)
    private String estadoCivilAp;
    @Column(name = "NACIONALIDAD_AP", nullable=true)
    private String nacionalidadAp;
    @Lob
    @Column(name = "FOTOGRAFIA_AP", nullable=true)
    private Blob fotografiaAp;
    @Column(name = "DUI_AP", nullable=true)
    private String duiAp;
    @Column(name = "NIT_AP", nullable=true)
    private String nitAp;
    @Column(name = "NUP_AP", nullable=true)
    private String nupAp;
    @Column(name = "PASAPORTE_AP", nullable=true)
    private String pasaporteAp;
    @Column(name = "DOC_PERSONAL_AP", nullable=true)
    private String docPersonalAp;
    
    @Column(name = "ID_LIST_CORREO", nullable=true)
    private Integer idListCorreo;
    @Column(name = "ID_LIST_DP", nullable=true)
    private Integer idListDp;
    @Column(name = "ID_LIST_EL", nullable=true)
    private Integer idListEl;
    @Column(name = "ID_LIST_RS", nullable=true)
    private Integer idListRs;
    @Column(name = "ID_LIST_TELEFONO", nullable=true)
    private Integer idListTelefono;
    @Column(name = "ID_LIST_TA", nullable=true)
    private Integer idListTa;
    @Column(name = "ID_PAIS", nullable=true)
    private Integer idPais;
    @Column(name = "IDUSUARIO", nullable=true)
    private Integer idusuario;
    
    public Date getFechaNacAp() {
        return fechaNacAp;
    }

    public void setFechaNacAp(Date fechaNacAp) {
        this.fechaNacAp = fechaNacAp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAspiranteProfesor != null ? idAspiranteProfesor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AspiranteProfesor)) {
            return false;
        }
        AspiranteProfesor other = (AspiranteProfesor) object;
        if ((this.idAspiranteProfesor == null && other.idAspiranteProfesor != null) || (this.idAspiranteProfesor != null && !this.idAspiranteProfesor.equals(other.idAspiranteProfesor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.AspiranteProfesor[ idAspiranteProfesor=" + idAspiranteProfesor + " ]";
    }    
}
