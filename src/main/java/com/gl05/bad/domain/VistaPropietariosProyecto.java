package com.gl05.bad.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "VISTA_PROPIETARIOS_PROYECTO")
public class VistaPropietariosProyecto implements Serializable{
    private static final long serialVersionUID = 1L;
        
    @Id
    @Column(name = "ID_PERSONA")
    private Long idPersona;
    
    @Column(name = "ID_PROYECTO")
    private Long idProyecto;
    
    @Column(name = "DUI")
    private String dui;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "LOTES")
    private String lotes;
    
    @Column(name = "CORREOS")
    private String correos;
    
    @Column(name = "TELEFONOS")
    private String telefonos;
}
