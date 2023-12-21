package com.gl05.bad.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "VISTA_VENTAS_ACTIVA")
public class VistaVentasActiva implements Serializable{
    
    @Id
    @Column(name = "ID_VENTA")
    private Long idVenta;
    
    @Column(name = "ID_PROYECTO")
    private Long idProyecto;
    
    @Column(name = "POLIGONO")
    private Character poligono;
    
    @Column(name = "LOTE")
    private String lote;
    
    @Column(name = "MATRICULA")
    private String matricula;
    
    @Column(name = "AREA_METROS")
    private Double areaMetros;
    
    @Column(name = "AREA_VARAS")
    private Double areaVaras;
}
