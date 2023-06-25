package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROGRAMA_ASIGNATURA")
@NamedQueries({
    @NamedQuery(name = "ProgramaAsignatura.findAll", query = "SELECT p FROM ProgramaAsignatura p"),
    @NamedQuery(name = "ProgramaAsignatura.findByIdProgramAsignatura", query = "SELECT p FROM ProgramaAsignatura p WHERE p.idProgramAsignatura = :idProgramAsignatura"),
    @NamedQuery(name = "ProgramaAsignatura.findByDuracionSemanas", query = "SELECT p FROM ProgramaAsignatura p WHERE p.duracionSemanas = :duracionSemanas"),
    @NamedQuery(name = "ProgramaAsignatura.findByHorasCiclo", query = "SELECT p FROM ProgramaAsignatura p WHERE p.horasCiclo = :horasCiclo"),
    @NamedQuery(name = "ProgramaAsignatura.findByHorasTeoricoSemana", query = "SELECT p FROM ProgramaAsignatura p WHERE p.horasTeoricoSemana = :horasTeoricoSemana"),
    @NamedQuery(name = "ProgramaAsignatura.findByHorasPracticaSemana", query = "SELECT p FROM ProgramaAsignatura p WHERE p.horasPracticaSemana = :horasPracticaSemana"),
    @NamedQuery(name = "ProgramaAsignatura.findByIntroduccion", query = "SELECT p FROM ProgramaAsignatura p WHERE p.introduccion = :introduccion"),
    @NamedQuery(name = "ProgramaAsignatura.findByDescripcionPrograma", query = "SELECT p FROM ProgramaAsignatura p WHERE p.descripcionPrograma = :descripcionPrograma"),
    @NamedQuery(name = "ProgramaAsignatura.findByObjetivos", query = "SELECT p FROM ProgramaAsignatura p WHERE p.objetivos = :objetivos"),
    @NamedQuery(name = "ProgramaAsignatura.findByMetodologiaEnsenanza", query = "SELECT p FROM ProgramaAsignatura p WHERE p.metodologiaEnsenanza = :metodologiaEnsenanza"),
    @NamedQuery(name = "ProgramaAsignatura.findBySistemaEvaluacion", query = "SELECT p FROM ProgramaAsignatura p WHERE p.sistemaEvaluacion = :sistemaEvaluacion"),
    @NamedQuery(name = "ProgramaAsignatura.findByBibliografia", query = "SELECT p FROM ProgramaAsignatura p WHERE p.bibliografia = :bibliografia")})
public class ProgramaAsignatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROGRAM_ASIGNATURA")
    private Long idProgramAsignatura;
    @Basic(optional = false)
    @Column(name = "DURACION_SEMANAS")
    private short duracionSemanas;
    @Basic(optional = false)
    @Column(name = "HORAS_CICLO")
    private short horasCiclo;
    @Basic(optional = false)
    @Column(name = "HORAS_TEORICO_SEMANA")
    private short horasTeoricoSemana;
    @Basic(optional = false)
    @Column(name = "HORAS_PRACTICA_SEMANA")
    private short horasPracticaSemana;
    @Basic(optional = false)
    private String introduccion;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION_PROGRAMA")
    private String descripcionPrograma;
    @Basic(optional = false)
    private String objetivos;
    @Basic(optional = false)
    @Column(name = "METODOLOGIA_ENSENANZA")
    private String metodologiaEnsenanza;
    @Basic(optional = false)
    @Column(name = "SISTEMA_EVALUACION")
    private String sistemaEvaluacion;
    @Basic(optional = false)
    private String bibliografia;
    //@OneToMany(mappedBy = "idProgramAsignatura")
    //private Collection<Asignatura> asignaturaCollection;
    @JoinColumn(name = "ID_LIST_AE", referencedColumnName = "ID_LIST_AE")
    @ManyToOne
    private ListadoActividadEvaluada idListAe;

    public ProgramaAsignatura() {
    }

    public ProgramaAsignatura(Long idProgramAsignatura) {
        this.idProgramAsignatura = idProgramAsignatura;
    }

    public ProgramaAsignatura(Long idProgramAsignatura, short duracionSemanas, short horasCiclo, short horasTeoricoSemana, short horasPracticaSemana, String introduccion, String descripcionPrograma, String objetivos, String metodologiaEnsenanza, String sistemaEvaluacion, String bibliografia) {
        this.idProgramAsignatura = idProgramAsignatura;
        this.duracionSemanas = duracionSemanas;
        this.horasCiclo = horasCiclo;
        this.horasTeoricoSemana = horasTeoricoSemana;
        this.horasPracticaSemana = horasPracticaSemana;
        this.introduccion = introduccion;
        this.descripcionPrograma = descripcionPrograma;
        this.objetivos = objetivos;
        this.metodologiaEnsenanza = metodologiaEnsenanza;
        this.sistemaEvaluacion = sistemaEvaluacion;
        this.bibliografia = bibliografia;
    }

    public Long getIdProgramAsignatura() {
        return idProgramAsignatura;
    }

    public void setIdProgramAsignatura(Long idProgramAsignatura) {
        this.idProgramAsignatura = idProgramAsignatura;
    }

    public short getDuracionSemanas() {
        return duracionSemanas;
    }

    public void setDuracionSemanas(short duracionSemanas) {
        this.duracionSemanas = duracionSemanas;
    }

    public short getHorasCiclo() {
        return horasCiclo;
    }

    public void setHorasCiclo(short horasCiclo) {
        this.horasCiclo = horasCiclo;
    }

    public short getHorasTeoricoSemana() {
        return horasTeoricoSemana;
    }

    public void setHorasTeoricoSemana(short horasTeoricoSemana) {
        this.horasTeoricoSemana = horasTeoricoSemana;
    }

    public short getHorasPracticaSemana() {
        return horasPracticaSemana;
    }

    public void setHorasPracticaSemana(short horasPracticaSemana) {
        this.horasPracticaSemana = horasPracticaSemana;
    }

    public String getIntroduccion() {
        return introduccion;
    }

    public void setIntroduccion(String introduccion) {
        this.introduccion = introduccion;
    }

    public String getDescripcionPrograma() {
        return descripcionPrograma;
    }

    public void setDescripcionPrograma(String descripcionPrograma) {
        this.descripcionPrograma = descripcionPrograma;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getMetodologiaEnsenanza() {
        return metodologiaEnsenanza;
    }

    public void setMetodologiaEnsenanza(String metodologiaEnsenanza) {
        this.metodologiaEnsenanza = metodologiaEnsenanza;
    }

    public String getSistemaEvaluacion() {
        return sistemaEvaluacion;
    }

    public void setSistemaEvaluacion(String sistemaEvaluacion) {
        this.sistemaEvaluacion = sistemaEvaluacion;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    /*public Collection<Asignatura> getAsignaturaCollection() {
        return asignaturaCollection;
    }

    public void setAsignaturaCollection(Collection<Asignatura> asignaturaCollection) {
        this.asignaturaCollection = asignaturaCollection;
    }*/

    public ListadoActividadEvaluada getIdListAe() {
        return idListAe;
    }

    public void setIdListAe(ListadoActividadEvaluada idListAe) {
        this.idListAe = idListAe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProgramAsignatura != null ? idProgramAsignatura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramaAsignatura)) {
            return false;
        }
        ProgramaAsignatura other = (ProgramaAsignatura) object;
        if ((this.idProgramAsignatura == null && other.idProgramAsignatura != null) || (this.idProgramAsignatura != null && !this.idProgramAsignatura.equals(other.idProgramAsignatura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ProgramaAsignatura[ idProgramAsignatura=" + idProgramAsignatura + " ]";
    }
    
}
