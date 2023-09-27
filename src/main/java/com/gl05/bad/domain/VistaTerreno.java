package com.gl05.bad.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "VISTA_TERRENO")
public class VistaTerreno implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_TERRENO")
    private Long idTerreno;
    
    @Column(name = "MATRICULA")
    private String matricula;
    
    @Column(name = "LOTE")
    private String lote;
    
    @Column(name = "POLIGONO")
    private Character poligono;
    
    @Column(name = "AREA_METROS")
    private Double areaMetros;
    
    @Column(name = "AREA_VARAS")
    private Double areaVaras;
    
    @Column(name = "ID_PROYECTO")
    private Long idProyecto;
}
