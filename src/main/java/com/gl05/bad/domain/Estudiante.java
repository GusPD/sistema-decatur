package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "ESTUDIANTE")
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.findByIdEstudiante", query = "SELECT e FROM Estudiante e WHERE e.idEstudiante = :idEstudiante"),
    @NamedQuery(name = "Estudiante.findByCarnetE", query = "SELECT e FROM Estudiante e WHERE e.carnetE = :carnetE"),
    @NamedQuery(name = "Estudiante.findByNombresE", query = "SELECT e FROM Estudiante e WHERE e.nombresE = :nombresE"),
    @NamedQuery(name = "Estudiante.findByApellidosE", query = "SELECT e FROM Estudiante e WHERE e.apellidosE = :apellidosE"),
    @NamedQuery(name = "Estudiante.findBySexoE", query = "SELECT e FROM Estudiante e WHERE e.sexoE = :sexoE"),
    @NamedQuery(name = "Estudiante.findByFechaNacE", query = "SELECT e FROM Estudiante e WHERE e.fechaNacE = :fechaNacE"),
    @NamedQuery(name = "Estudiante.findByNacionalidadE", query = "SELECT e FROM Estudiante e WHERE e.nacionalidadE = :nacionalidadE"),
    @NamedQuery(name = "Estudiante.findByDuiE", query = "SELECT e FROM Estudiante e WHERE e.duiE = :duiE"),
    @NamedQuery(name = "Estudiante.findByNitE", query = "SELECT e FROM Estudiante e WHERE e.nitE = :nitE"),
    @NamedQuery(name = "Estudiante.findByNupE", query = "SELECT e FROM Estudiante e WHERE e.nupE = :nupE"),
    @NamedQuery(name = "Estudiante.findByPasaporteE", query = "SELECT e FROM Estudiante e WHERE e.pasaporteE = :pasaporteE"),
    @NamedQuery(name = "Estudiante.findByDocPersonalE", query = "SELECT e FROM Estudiante e WHERE e.docPersonalE = :docPersonalE")})
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "S_ESTUDIANTE", sequenceName = "S_ESTUDIANTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ESTUDIANTE")
    @Column(name = "ID_ESTUDIANTE")
    private Long idEstudiante;
    
    @Column(name = "CARNET_E")
    private String carnetE;
    
    @Column(name = "NOMBRES_E")
    private String nombresE;
    
    @Column(name = "APELLIDOS_E")
    private String apellidosE;
   
    @Column(name = "SEXO_E")
    private String sexoE;
    
    @Column(name = "FECHA_NAC_E")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yy")
    private Date fechaNacE;
    
    @Column(name = "NACIONALIDAD_E")
    private String nacionalidadE;
    
    @Column(name = "DUI_E")
    private String duiE;
    
    @Column(name = "NIT_E")
    private String nitE;
    
    @Column(name = "NUP_E")
    private String nupE;
    
    @Column(name = "PASAPORTE_E")
    private String pasaporteE;
    
    @Column(name = "DOC_PERSONAL_E")
    private String docPersonalE;
    
    @Column(name = "CORREO_E")
    private String correoE;
    
    @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID_PAIS")
    @ManyToOne(optional = false)
    private Pais idPais;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstudiante")
//    private Collection<EstudianteCohorte> estudianteCohorteCollection;

    public Estudiante() {
    }

    public Estudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Estudiante(Long idEstudiante, String carnetE, String nombresE, String apellidosE, String sexoE, Date fechaNacE, String nacionalidadE, Serializable fotografiaE) {
        this.idEstudiante = idEstudiante;
        this.carnetE = carnetE;
        this.nombresE = nombresE;
        this.apellidosE = apellidosE;
        this.sexoE = sexoE;
        this.fechaNacE = fechaNacE;
        this.nacionalidadE = nacionalidadE;
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCarnetE() {
        return carnetE;
    }

    public void setCarnetE(String carnetE) {
        this.carnetE = carnetE;
    }

    public String getNombresE() {
        return nombresE;
    }

    public void setNombresE(String nombresE) {
        this.nombresE = nombresE;
    }

    public String getApellidosE() {
        return apellidosE;
    }

    public void setApellidosE(String apellidosE) {
        this.apellidosE = apellidosE;
    }

    public String getSexoE() {
        return sexoE;
    }

    public void setSexoE(String sexoE) {
        this.sexoE = sexoE;
    }

    public Date getFechaNacE() {
        return fechaNacE;
    }

    public void setFechaNacE(Date fechaNacE) {
        this.fechaNacE = fechaNacE;
    }

    public String getNacionalidadE() {
        return nacionalidadE;
    }

    public void setNacionalidadE(String nacionalidadE) {
        this.nacionalidadE = nacionalidadE;
    }

    public String getDuiE() {
        return duiE;
    }

    public void setDuiE(String duiE) {
        this.duiE = duiE;
    }

    public String getNitE() {
        return nitE;
    }

    public void setNitE(String nitE) {
        this.nitE = nitE;
    }

    public String getNupE() {
        return nupE;
    }

    public void setNupE(String nupE) {
        this.nupE = nupE;
    }

    public String getPasaporteE() {
        return pasaporteE;
    }

    public void setPasaporteE(String pasaporteE) {
        this.pasaporteE = pasaporteE;
    }

    public String getDocPersonalE() {
        return docPersonalE;
    }

    public void setDocPersonalE(String docPersonalE) {
        this.docPersonalE = docPersonalE;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    public String getCorreoE() {
        return correoE;
    }

    public void setCorreoE(String correo) {
        this.correoE = correo;
    }


//    public Collection<EstudianteCohorte> getEstudianteCohorteCollection() {
//        return estudianteCohorteCollection;
//    }
//
//    public void setEstudianteCohorteCollection(Collection<EstudianteCohorte> estudianteCohorteCollection) {
//        this.estudianteCohorteCollection = estudianteCohorteCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstudiante != null ? idEstudiante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.idEstudiante == null && other.idEstudiante != null) || (this.idEstudiante != null && !this.idEstudiante.equals(other.idEstudiante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Estudiante[ idEstudiante=" + idEstudiante + " ]";
    }
    
}
