package com.gl05.bad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByIntentos", query = "SELECT u FROM Usuario u WHERE u.intentos = :intentos"),
    @NamedQuery(name = "Usuario.findByBloqueado", query = "SELECT u FROM Usuario u WHERE u.bloqueado = :bloqueado"),
    @NamedQuery(name = "Usuario.findByHabilitado", query = "SELECT u FROM Usuario u WHERE u.habilitado = :habilitado")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_USUARIO")
    @SequenceGenerator(name = "S_USUARIO", sequenceName = "S_USUARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_USUARIO")
    private Long idUsuario;
    
    @Size(max = 50)
    @Column(name = "USERNAME")
    private String username;
    
    @Size(max = 100)
    @Column(name = "PASSWORD")
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "INTENTOS")
    private int intentos;
    
    @Column(name = "BLOQUEADO")
    private int bloqueado;
    
    @Column(name = "HABILITADO")
    private boolean habilitado;

    //Establezco la relación con la base de datos
    @ManyToMany//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
           name = "USUARIO_ROLES",
            joinColumns = @JoinColumn(name="ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name="ID_ROL")
    )
    
    private Set<Rol> roles = new HashSet<>();
    
    //Añade roles al usuario
    public void añadirRol(Rol rol){
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
