package com.gl05.bad.domain;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
//import lombok.Data;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Column(name="IDUSUARIO")
    @Id
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    private Long idUsuario;
 
    @Column(name="USERNAME")
    private String username;
    
    @Column(name="EMAIL")
    private String email;
    
    @Column(name="PASSWORD_USER")
    private String password;
    
    @Column(name="ENABLE_USER")
    private boolean enabled;
    
    @Column(name="NUMEROINTENTOS")
    private int numerointentos;
    
    @Column(name="BLOQUEADO")
    private int usuarioBloqueado;
    
   //Establezco la relación con la base de datos
    @ManyToMany//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinTable(
           name = "USUARIO_ROLES",
            joinColumns = @JoinColumn(name="IDUSUARIO"),
            inverseJoinColumns = @JoinColumn(name="IDROL")
    )
    private Set<Roles> roles = new HashSet<>();
    
////    @OneToMany(mappedBy = "idusuario")
//    private Collection<AspiranteProfesor> aspiranteProfesorCollection;
//    @OneToMany(mappedBy = "idusuario")
//    private Collection<CoordinadorAcademico> coordinadorAcademicoCollection;

    public Usuario(Long idUsuario, String username, String email, String password, boolean enabled, int numerointentos, int usuarioBloqueado) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.numerointentos = numerointentos;
        this.usuarioBloqueado = usuarioBloqueado;
    }

    public Usuario(String username, String email, String password, boolean enabled, int numerointentos, int usuarioBloqueado) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.numerointentos = numerointentos;
        this.usuarioBloqueado = usuarioBloqueado;
    }

    public Usuario() {
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public int getNumerointentos() {
        return numerointentos;
    }

    public void setNumerointentos(int numerointentos) {
        this.numerointentos = numerointentos;
    }

    public int getUsuarioBloqueado() {
        return usuarioBloqueado;
    }

    public void setUsuarioBloqueado(int usuarioBloqueado) {
        this.usuarioBloqueado = usuarioBloqueado;
    }
    
//    public Collection<AspiranteProfesor> getAspiranteProfesorCollection() {
//        return aspiranteProfesorCollection;
//    }
//
//    public void setAspiranteProfesorCollection(Collection<AspiranteProfesor> aspiranteProfesorCollection) {
//        this.aspiranteProfesorCollection = aspiranteProfesorCollection;
//    }
//
//    public Collection<CoordinadorAcademico> getCoordinadorAcademicoCollection() {
//        return coordinadorAcademicoCollection;
//    }
//
//    public void setCoordinadorAcademicoCollection(Collection<CoordinadorAcademico> coordinadorAcademicoCollection) {
//        this.coordinadorAcademicoCollection = coordinadorAcademicoCollection;
//    }

    //Añade roles al usuario
    public void añadirRol(Roles rol){
        this.roles.add(rol);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idUsuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.idUsuario, other.idUsuario);
    }

    @Override
    public String toString() {
        return username ;
    }
    
    
    
}
