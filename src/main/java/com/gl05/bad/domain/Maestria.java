package com.gl05.bad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name = "Maestria.findAll", query = "SELECT m FROM Maestria m"),
    @NamedQuery(name = "Maestria.findByIdMaestria", query = "SELECT m FROM Maestria m WHERE m.idMaestria = :idMaestria"),
    @NamedQuery(name = "Maestria.findByNombreMaestria", query = "SELECT m FROM Maestria m WHERE m.nombreMaestria = :nombreMaestria")})
public class Maestria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "S_MAESTRIA", sequenceName = "S_MAESTRIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_MAESTRIA")
    @Column(name = "ID_MAESTRIA")
    private Long idMaestria;
    
    @Basic(optional = false)
    @Column(name = "NOMBRE_MAESTRIA")
    private String nombreMaestria;
    
    @Column(name = "PERFIL_ASP")
    private String perfilAsp;
    
    @Column(name = "PERFIL_COOR")
    private String perfilCoor;
    
    @JoinColumn(name = "ID_COOR_ACA", referencedColumnName = "ID_COOR_ACA")
    @ManyToOne
    private CoordinadorAcademico idCoorAca;

    @JoinColumn(name = "ID_POSTGRADO", referencedColumnName = "ID_POSTGRADO")
    @ManyToOne(optional = false)
    private EscuelaPostgrado idPostgrado;
    
//    @JoinColumn(name = "ID_LIST_COHORTE", referencedColumnName = "ID_LIST_COHORTE")
//    @ManyToOne
//    private ListadoCohorte idListCohorte;

    public Maestria() {
    }

    public Maestria(Long idMaestria) {
        this.idMaestria = idMaestria;
    }

    public Maestria(Long idMaestria, String nombreMaestria) {
        this.idMaestria = idMaestria;
        this.nombreMaestria = nombreMaestria;
    }

    public Long getIdMaestria() {
        return idMaestria;
    }

    public void setIdMaestria(Long idMaestria) {
        this.idMaestria = idMaestria;
    }

    public String getNombreMaestria() {
        return nombreMaestria;
    }

    public void setNombreMaestria(String nombreMaestria) {
        this.nombreMaestria = nombreMaestria;
    }

    public String getPerfilAsp() {
        return perfilAsp;
    }

    public void setPerfilAsp(String perfilAsp) {
        this.perfilAsp = perfilAsp;
    }

    public String getPerfilCoor() {
        return perfilCoor;
    }

    public void setPerfilCoor(String perfilCoor) {
        this.perfilCoor = perfilCoor;
    }
    
    public CoordinadorAcademico getIdCoorAca() {
        return idCoorAca;
    }

    public void setIdCoorAca(CoordinadorAcademico idCoorAca) {
        this.idCoorAca = idCoorAca;
    }

    public EscuelaPostgrado getIdPostgrado() {
        return idPostgrado;
    }

    public void setIdPostgrado(EscuelaPostgrado idPostgrado) {
        this.idPostgrado = idPostgrado;
    }

//    public ListadoCohorte getIdListCohorte() {
//        return idListCohorte;
//    }
//
//    public void setIdListCohorte(ListadoCohorte idListCohorte) {
//        this.idListCohorte = idListCohorte;
//    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMaestria != null ? idMaestria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maestria)) {
            return false;
        }
        Maestria other = (Maestria) object;
        if ((this.idMaestria == null && other.idMaestria != null) || (this.idMaestria != null && !this.idMaestria.equals(other.idMaestria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Maestria[ idMaestria=" + idMaestria + " ]";
    }
    
}
