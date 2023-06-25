package com.gl05.bad.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "VISTA_ESTUDIANTES_PROFESORES")
public class VistaEstudiantesProfesores implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "MAESTRIA")
    private String nombreMaestria;

    @Column(name = "COHORTE")
    private String nombreCohorte;
    
    @Column(name = "AREA_CONOCIMIENTO")
    private String areaConocimiento;

    @Column(name = "CANTIDAD_ESTUDIANTES")
    private Long numeroEstudiantes;
    
    @Column(name = "CANTIDAD_PROFESORES")
    private Long numeroProfesores;
    
}