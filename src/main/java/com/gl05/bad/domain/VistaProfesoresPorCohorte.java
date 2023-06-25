package com.gl05.bad.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "VISTA_PROFESORES_POR_COHORTE ")
public class VistaProfesoresPorCohorte implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "NOMBRES")
    private String nombres;

    @Column(name = "APELLIDOS")
    private String apellidos;

    @Column(name = "MAESTRIA")
    private String nombreMaestria;

    @Column(name = "COHORTE")
    private String nombreCohorte;

    @Column(name = "MATERIA")
    private String nombreMateria;
}
