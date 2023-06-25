package com.gl05.bad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "ESCUELA_POSTGRADO")
@NamedQueries({
    @NamedQuery(name = "EscuelaPostgrado.findAll", query = "SELECT e FROM EscuelaPostgrado e"),
    @NamedQuery(name = "EscuelaPostgrado.findByIdPostgrado", query = "SELECT e FROM EscuelaPostgrado e WHERE e.idPostgrado = :idPostgrado"),
    @NamedQuery(name = "EscuelaPostgrado.findByNombrePostgrado", query = "SELECT e FROM EscuelaPostgrado e WHERE e.nombrePostgrado = :nombrePostgrado")})
public class EscuelaPostgrado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "S_ESCUELA_POSTGRADO", sequenceName = "S_ESCUELA_POSTGRADO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ESCUELA_POSTGRADO")
    @Column(name = "ID_POSTGRADO")
    private Long idPostgrado;
    @Basic(optional = false)
    @Column(name = "NOMBRE_POSTGRADO")
    private String nombrePostgrado;
    @JoinColumn(name = "ID_FACULTAD", referencedColumnName = "ID_FACULTAD")
    @ManyToOne(optional = false)
    private Facultad idFacultad;
   /*@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPostgrado")
    private Collection<Maestria> maestriaCollection; */

    public EscuelaPostgrado() {
    }

    public EscuelaPostgrado(Long idPostgrado) {
        this.idPostgrado = idPostgrado;
    }

    public EscuelaPostgrado(Long idPostgrado, String nombrePostgrado) {
        this.idPostgrado = idPostgrado;
        this.nombrePostgrado = nombrePostgrado;
    }

    public Long getIdPostgrado() {
        return idPostgrado;
    }

    public void setIdPostgrado(Long idPostgrado) {
        this.idPostgrado = idPostgrado;
    }

    public String getNombrePostgrado() {
        return nombrePostgrado;
    }

    public void setNombrePostgrado(String nombrePostgrado) {
        this.nombrePostgrado = nombrePostgrado;
    }

    public Facultad getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(Facultad idFacultad) {
        this.idFacultad = idFacultad;
    }

    /*public Collection<Maestria> getMaestriaCollection() {
        return maestriaCollection;
    }*/

    /*public void setMaestriaCollection(Collection<Maestria> maestriaCollection) {
        this.maestriaCollection = maestriaCollection;
    }*/
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPostgrado != null ? idPostgrado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EscuelaPostgrado)) {
            return false;
        }
        EscuelaPostgrado other = (EscuelaPostgrado) object;
        if ((this.idPostgrado == null && other.idPostgrado != null) || (this.idPostgrado != null && !this.idPostgrado.equals(other.idPostgrado))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "EscuelaPostgrado[ idPostgrado=" + idPostgrado + " ]";
    }
    
}
