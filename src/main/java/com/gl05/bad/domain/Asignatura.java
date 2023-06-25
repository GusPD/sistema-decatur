package com.gl05.bad.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


@Entity
@NamedQueries({
    @NamedQuery(name = "Asignatura.findAll", query = "SELECT a FROM Asignatura a"),
    @NamedQuery(name = "Asignatura.findByIdAsignatura", query = "SELECT a FROM Asignatura a WHERE a.idAsignatura = :idAsignatura"),
    @NamedQuery(name = "Asignatura.findByCodAsignatura", query = "SELECT a FROM Asignatura a WHERE a.codAsignatura = :codAsignatura"),
    @NamedQuery(name = "Asignatura.findByNombreMateria", query = "SELECT a FROM Asignatura a WHERE a.nombreMateria = :nombreMateria"),
    @NamedQuery(name = "Asignatura.findByUnidadesValorativas", query = "SELECT a FROM Asignatura a WHERE a.unidadesValorativas = :unidadesValorativas"),
    @NamedQuery(name = "Asignatura.findByNumeroCorrelativo", query = "SELECT a FROM Asignatura a WHERE a.numeroCorrelativo = :numeroCorrelativo"),
    @NamedQuery(name = "Asignatura.findByCiclo", query = "SELECT a FROM Asignatura a WHERE a.ciclo = :ciclo")})
public class Asignatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "S_ACTIVIDAD", sequenceName = "S_ACTIVIDAD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ACTIVIDAD")
    @Column(name = "ID_ASIGNATURA")
    private Long idAsignatura;
    
    @Basic(optional = false)
    @Column(name = "COD_ASIGNATURA")
    private String codAsignatura;
    
    @Basic(optional = false)
    @Column(name = "NOMBRE_MATERIA")
    private String nombreMateria;
    @Basic(optional = false)
    @Column(name = "UNIDADES_VALORATIVAS")
    private short unidadesValorativas;
    @Basic(optional = false)
    @Column(name = "NUMERO_CORRELATIVO")
    private short numeroCorrelativo;
    
    @Basic(optional = false)
    @Column(name = "CICLO")
    private short ciclo;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAsignatura")
//    private Collection<EstudianteAsignatura> estudianteAsignaturaCollection;
    
    //@JsonBackReference
    @JoinColumn(name = "ID_AREA_CONOCIMIENTO", referencedColumnName = "ID_AREA_CONOCIMIENTO")
    @ManyToOne(optional = false)
    private AreaConocimiento idAreaConocimiento;
    
    @JsonBackReference
    @JoinColumn(name = "ID_MALLA_CURRICULAR", referencedColumnName = "ID_MALLA_CURRICULAR")
    @ManyToOne(optional = false)
    private MallaCurricular idMallaCurricular;
    
    //@JsonBackReference
    @JoinColumn(name = "ID_PROGRAM_ASIGNATURA", referencedColumnName = "ID_PROGRAM_ASIGNATURA")
    @ManyToOne(optional = false)
    private ProgramaAsignatura idProgramAsignatura;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAsignatura")
//    private Collection<ProfesorAsignatura> profesorAsignaturaCollection;

    public Asignatura() {
    }

    public Asignatura(Long idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public Asignatura(Long idAsignatura, String codAsignatura, String nombreMateria, short unidadesValorativas, short numeroCorrelativo, short ciclo) {
        this.idAsignatura = idAsignatura;
        this.codAsignatura = codAsignatura;
        this.nombreMateria = nombreMateria;
        this.unidadesValorativas = unidadesValorativas;
        this.numeroCorrelativo = numeroCorrelativo;
        this.ciclo = ciclo;
    }

    public Long getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(Long idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public String getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(String codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public short getUnidadesValorativas() {
        return unidadesValorativas;
    }

    public void setUnidadesValorativas(short unidadesValorativas) {
        this.unidadesValorativas = unidadesValorativas;
    }

    public short getNumeroCorrelativo() {
        return numeroCorrelativo;
    }

    public void setNumeroCorrelativo(short numeroCorrelativo) {
        this.numeroCorrelativo = numeroCorrelativo;
    }

    public short getCiclo() {
        return ciclo;
    }

    public void setCiclo(short ciclo) {
        this.ciclo = ciclo;
    }
//
//    public Collection<EstudianteAsignatura> getEstudianteAsignaturaCollection() {
//        return estudianteAsignaturaCollection;
//    }
//
//    public void setEstudianteAsignaturaCollection(Collection<EstudianteAsignatura> estudianteAsignaturaCollection) {
//        this.estudianteAsignaturaCollection = estudianteAsignaturaCollection;
//    }

    public AreaConocimiento getIdAreaConocimiento() {
        return idAreaConocimiento;
    }

    public void setIdAreaConocimiento(AreaConocimiento idAreaConocimiento) {
        this.idAreaConocimiento = idAreaConocimiento;
    }

    public MallaCurricular getIdMallaCurricular() {
        return idMallaCurricular;
    }

    public void setIdMallaCurricular(MallaCurricular idMallaCurricular) {
        this.idMallaCurricular = idMallaCurricular;
    }

    public ProgramaAsignatura getIdProgramAsignatura() {
        return idProgramAsignatura;
    }

    public void setIdProgramAsignatura(ProgramaAsignatura idProgramAsignatura) {
        this.idProgramAsignatura = idProgramAsignatura;
    }

//    public Collection<ProfesorAsignatura> getProfesorAsignaturaCollection() {
//        return profesorAsignaturaCollection;
//    }
//
//    public void setProfesorAsignaturaCollection(Collection<ProfesorAsignatura> profesorAsignaturaCollection) {
//        this.profesorAsignaturaCollection = profesorAsignaturaCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsignatura != null ? idAsignatura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignatura)) {
            return false;
        }
        Asignatura other = (Asignatura) object;
        if ((this.idAsignatura == null && other.idAsignatura != null) || (this.idAsignatura != null && !this.idAsignatura.equals(other.idAsignatura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Asignatura[ idAsignatura=" + idAsignatura + " ]";
    }
    
}
