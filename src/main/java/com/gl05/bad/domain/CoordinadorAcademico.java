package com.gl05.bad.domain;

import java.sql.Blob;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@Entity
@Table(name="COORDINADOR_ACADEMICO")
@NamedQueries({
  @NamedQuery(name = "CoordinadorAcademico.findAll", query = "SELECT c FROM CoordinadorAcademico c"),
  @NamedQuery(name = "CoordinadorAcademico.findByIdCoorAca", query = "SELECT c FROM CoordinadorAcademico c WHERE c.idCoorAca = :idCoorAca"),
  @NamedQuery(name = "CoordinadorAcademico.findByCodCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.codCa = :codCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByNombresCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.nombresCa = :nombresCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByApellidosCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.apellidosCa = :apellidosCa"),
  @NamedQuery(name = "CoordinadorAcademico.findBySexoCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.sexoCa = :sexoCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByGeneroCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.generoCa = :generoCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByFechaNacCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.fechaNacCa = :fechaNacCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByEstadoCivilCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.estadoCivilCa = :estadoCivilCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByNacionalidadCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.nacionalidadCa = :nacionalidadCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByDuiCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.duiCa = :duiCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByNitCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.nitCa = :nitCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByNupCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.nupCa = :nupCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByPasaporteCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.pasaporteCa = :pasaporteCa"),
  @NamedQuery(name = "CoordinadorAcademico.findByDocPersonalCa", query = "SELECT c FROM CoordinadorAcademico c WHERE c.docPersonalCa = :docPersonalCa")})

public class CoordinadorAcademico implements Serializable{
  private static final long serialVersionUID = 1L;
  
  @Id
  @Column(name="ID_COOR_ACA")
  @SequenceGenerator(name = "S_COORDINADOR_ACADEMICO", sequenceName = "S_COORDINADOR_ACADEMICO", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_COORDINADOR_ACADEMICO")
    private Long idCoorAca;
  
  @Column(name="COD_CA", nullable=true)
    private String codCa;
  
  @Column(name="NOMBRES_CA", nullable=true)
    private String nombresCa;
  
  @Column(name="APELLIDOS_CA", nullable=true)
    private String apellidosCa;
  
  @Column(name="SEXO_CA", nullable=true)
    private String sexoCa;
  
  @Column(name="GENERO_CA", nullable=true)
    private String generoCa;
  
  @Column(name="FECHA_NAC_CA", nullable=true)
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yy")
    private Date fechaNacCa;  

  @Column(name="ESTADO_CIVIL_CA", nullable=true)
    private String estadoCivilCa;
  
  @Column(name="NACIONALIDAD_CA", nullable=true)
    private String nacionalidadCa;
  
  @JsonIgnore
  @Lob
  @Column(name="FOTOGRAFIA_CA", nullable=true)
    private Blob fotografiaCa;
  
  @Column(name="DUI_CA", nullable=true)
    private String duiCa;
  
  @Column(name="NIT_CA", nullable=true)
    private String nitCa;
  
  @Column(name="NUP_CA", nullable=true)
    private String nupCa;
  
  @Column(name="PASAPORTE_CA", nullable=true)
    private String pasaporteCa;
  
  @Column(name="DOC_PERSONAL_CA", nullable=true)
    private String docPersonalCa;
 
  @Column(name="ID_LIST_CORREO", nullable=true)
    private Integer idListCorreo;
  @Column(name="ID_LIST_DP", nullable=true)
    private Integer idListDp;
  @Column(name="ID_LIST_TELEFONO", nullable=true)
    private Integer idListTelefono;
  @Column(name="ID_LIST_TA", nullable=true)
    private Integer idListTa;
  @Column(name="ID_PAIS", nullable=true)
    private Integer idPais;
  @Column(name="IDUSUARIO", nullable=true)
    private Integer idusuario;
  
  /*@JsonIgnore
  @JoinColumn(name = "ID_LIST_CORREO", referencedColumnName = "ID_LIST_CORREO", nullable=true)
  @ManyToOne(optional = false)
    private ListadoCorreo idListCorreo;

  @JoinColumn(name = "ID_LIST_DP", referencedColumnName = "ID_LIST_DP", nullable=true)
  @ManyToOne
    private ListadoDocumentacionPersonal idListDp;

  @JoinColumn(name = "ID_LIST_TELEFONO", referencedColumnName = "ID_LIST_TELEFONO", nullable=true)
  @ManyToOne
    private ListadoTelefono idListTelefono;

  @JoinColumn(name = "ID_LIST_TA", referencedColumnName = "ID_LIST_TA", nullable=true)
  @ManyToOne
    private ListadoTitulosAcademicos idListTa;

  @JsonIgnore
  @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID_PAIS", nullable=true)
  @ManyToOne(optional = false)
    private Pais idPais;
  
  @JsonIgnore
  @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO", nullable=true)
  @ManyToOne
    private Usuario idusuario;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCoorAca")
    private Collection<Maestria> maestriaCollection;*/
  
  /*public CoordinadorAcademico() {}

  public CoordinadorAcademico(Long idCoorAca) {
    this.idCoorAca = idCoorAca;
  }

  public CoordinadorAcademico(Long idCoorAca, String codCa, String nombresCa, String apellidosCa, String sexoCa, String generoCa, Date fechaNacCa, String estadoCivilCa, String nacionalidadCa, Blob fotografiaCa) {
    this.idCoorAca = idCoorAca;
    this.codCa = codCa;
    this.nombresCa = nombresCa;
    this.apellidosCa = apellidosCa;
    this.sexoCa = sexoCa;
    this.generoCa = generoCa;
    this.fechaNacCa = fechaNacCa;
    this.estadoCivilCa = estadoCivilCa;
    this.nacionalidadCa = nacionalidadCa;
    this.fotografiaCa = fotografiaCa;
  }*/

  /*public Long getIdCoorAca() {
    return idCoorAca;
  }

  public void setIdCoorAca(Long idCoorAca) {
    this.idCoorAca = idCoorAca;
  }

  public String getCodCa() {
    return codCa;
  }

  public void setCodCa(String codCa) {
    this.codCa = codCa;
  }

  public String getNombresCa() {
    return nombresCa;
  }

  public void setNombresCa(String nombresCa) {
    this.nombresCa = nombresCa;
  }

  public String getApellidosCa() {
    return apellidosCa;
  }

  public void setApellidosCa(String apellidosCa) {
    this.apellidosCa = apellidosCa;
  }

  public String getSexoCa() {
    return sexoCa;
  }

  public void setSexoCa(String sexoCa) {
    this.sexoCa = sexoCa;
  }

  public String getGeneroCa() {
    return generoCa;
  }

  public void setGeneroCa(String generoCa) {
    this.generoCa = generoCa;
  }

  public Date getFechaNacCa() {
    return fechaNacCa;
  }

  public void setFechaNacCa(Date fechaNacCa) {
    this.fechaNacCa = fechaNacCa;
  }

  public String getEstadoCivilCa() {
    return estadoCivilCa;
  }

  public void setEstadoCivilCa(String estadoCivilCa) {
    this.estadoCivilCa = estadoCivilCa;
  }

  public String getNacionalidadCa() {
    return nacionalidadCa;
  }

  public void setNacionalidadCa(String nacionalidadCa) {
    this.nacionalidadCa = nacionalidadCa;
  }
  
  @JsonIgnore
  public Blob getFotografiaCa() {
    return fotografiaCa;
  }

  public void setFotografiaCa(Blob fotografiaCa) {
    this.fotografiaCa = fotografiaCa;
  }

  public String getDuiCa() {
    return duiCa;
  }

  public void setDuiCa(String duiCa) {
    this.duiCa = duiCa;
  }

  public String getNitCa() {
    return nitCa;
  }

  public void setNitCa(String nitCa) {
    this.nitCa = nitCa;
  }

  public String getNupCa() {
    return nupCa;
  }

  public void setNupCa(String nupCa) {
    this.nupCa = nupCa;
  }

  public String getPasaporteCa() {
    return pasaporteCa;
  }

  public void setPasaporteCa(String pasaporteCa) {
    this.pasaporteCa = pasaporteCa;
  }

  public String getDocPersonalCa() {
    return docPersonalCa;
  }

  public void setDocPersonalCa(String docPersonalCa) {
    this.docPersonalCa = docPersonalCa;
  }

  public ListadoCorreo getIdListCorreo() {
    return idListCorreo;
  }

  public void setIdListCorreo(ListadoCorreo idListCorreo) {
    this.idListCorreo = idListCorreo;
  }

  public ListadoDocumentacionPersonal getIdListDp() {
    return idListDp;
  }

  public void setIdListDp(ListadoDocumentacionPersonal idListDp) {
    this.idListDp = idListDp;
  }

  public ListadoTelefono getIdListTelefono() {
    return idListTelefono;
  }

  public void setIdListTelefono(ListadoTelefono idListTelefono) {
    this.idListTelefono = idListTelefono;
  }

  public ListadoTitulosAcademicos getIdListTa() {
    return idListTa;
  }

  public void setIdListTa(ListadoTitulosAcademicos idListTa) {
    this.idListTa = idListTa;
  }

  public Pais getIdPais() {
    return idPais;
  }

  public void setIdPais(Pais idPais) {
    this.idPais = idPais;
  }

  public Usuario getIdusuario() {
    return idusuario;
  }

  public void setIdusuario(Usuario idusuario) {
    this.idusuario = idusuario;
  }

  public Collection<Maestria> getMaestriaCollection() {
    return maestriaCollection;
  }

  public void setMaestriaCollection(Collection<Maestria> maestriaCollection) {
    this.maestriaCollection = maestriaCollection;
  }*/

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idCoorAca != null ? idCoorAca.hashCode() : 0);
    return hash;
  }

  @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoordinadorAcademico)) {
            return false;
        }
        CoordinadorAcademico other = (CoordinadorAcademico) object;
        if ((this.idCoorAca == null && other.idCoorAca != null) || (this.idCoorAca != null && !this.idCoorAca.equals(other.idCoorAca))) {
            return false;
        }
           return true;
    }
 

  @Override
  public String toString() {
    return "com.gl05.bad.domain.CoordinadorAcademico[ idCoorAca=" + idCoorAca + " ]";
  }


  
}
