package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "VISTA_ESTUDIANTES_POR_COHORTE")
public class VistaEstudiantesPorCohorte implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID_COHORTE")
    private Long idCohorte;

    @Column(name = "ID_MAESTRIA")
    private Long idMaestria;

    @Column(name = "NOMBRE_MAESTRIA")
    private String nombreMaestria;

    @Column(name = "NOMBRE_COHORTE")
    private String nombreCohorte;

    @Column(name = "FECHA_APERTURA")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yy")
    private Date fechaApertura;

    @Column(name = "ESTADO_COHORTE")
    private Integer estadoCohorte;

    @Column(name = "NUMERO_ESTUDIANTES")
    private Long numeroEstudiantes;

}

