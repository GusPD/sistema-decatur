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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "TERRENO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terreno.findAll", query = "SELECT t FROM Terreno t"),
    @NamedQuery(name = "Terreno.findByIdTerreno", query = "SELECT t FROM Terreno t WHERE t.idTerreno = :idTerreno"),
    @NamedQuery(name = "Terreno.findByMatricula", query = "SELECT t FROM Terreno t WHERE t.matricula = :matricula"),
    @NamedQuery(name = "Terreno.findByNumero", query = "SELECT t FROM Terreno t WHERE t.numero = :numero"),
    @NamedQuery(name = "Terreno.findByPoligono", query = "SELECT t FROM Terreno t WHERE t.poligono = :poligono"),
    @NamedQuery(name = "Terreno.findBySeccion", query = "SELECT t FROM Terreno t WHERE t.seccion = :seccion"),
    @NamedQuery(name = "Terreno.findByAreaMetros", query = "SELECT t FROM Terreno t WHERE t.areaMetros = :areaMetros"),
    @NamedQuery(name = "Terreno.findByAreaVaras", query = "SELECT t FROM Terreno t WHERE t.areaVaras = :areaVaras")})
public class Terreno implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TERRENO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTerreno;
    @Size(max = 18)
    @Column(name = "MATRICULA")
    private String matricula;
    @Column(name = "NUMERO")
    private Long numero;
    @Column(name = "POLIGONO")
    private String poligono;
    @Column(name = "SECCION")
    private String seccion;
    @Column(name = "AREA_METROS")
    private Double areaMetros;
    @Column(name = "AREA_VARAS")
    private Double areaVaras;
    @ManyToOne
    @JoinColumn(name = "ID_PROYECTO")
    private Proyecto proyecto;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTerreno != null ? idTerreno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Terreno)) {
            return false;
        }
        Terreno other = (Terreno) object;
        if ((this.idTerreno == null && other.idTerreno != null) || (this.idTerreno != null && !this.idTerreno.equals(other.idTerreno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.Terreno[ idTerreno=" + idTerreno + " ]";
    }
    
}
