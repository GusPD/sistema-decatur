package com.gl05.bad.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
@Table(name = "CONFIGURACION_CORREO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfiguracionCorreo.findAll", query = "SELECT c FROM ConfiguracionCorreo c"),
    @NamedQuery(name = "ConfiguracionCorreo.findByIdConfiguracion", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.idConfiguracion = :idConfiguracion"),
    @NamedQuery(name = "ConfiguracionCorreo.findByName", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.name = :name"),
    @NamedQuery(name = "ConfiguracionCorreo.findByHost", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.host = :host"),
    @NamedQuery(name = "ConfiguracionCorreo.findByPort", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.port = :port"),
    @NamedQuery(name = "ConfiguracionCorreo.findByProtocol", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.protocol = :protocol"),
    @NamedQuery(name = "ConfiguracionCorreo.findByUsername", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.username = :username"),
    @NamedQuery(name = "ConfiguracionCorreo.findByPassword", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.password = :password"),
    @NamedQuery(name = "ConfiguracionCorreo.findBySmtp_auth", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.smtp_auth = :smtp_auth"),
    @NamedQuery(name = "ConfiguracionCorreo.findByStar_tls", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.start_tls = :start_tls"),
    @NamedQuery(name = "ConfiguracionCorreo.findByServidor", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.servidor = :servidor"),
    @NamedQuery(name = "ConfiguracionCorreo.findByVerificado", query = "SELECT c FROM ConfiguracionCorreo c WHERE c.verificado = :verificado")})
public class ConfiguracionCorreo implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CONFIGURACION")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConfiguracion;
    @Size(max = 100)
    @Column(name = "NAME")
    private String name;
    @Size(max = 50)
    @Column(name = "HOST")
    private String host;
    @Column(name = "PORT")
    private String port;
    @Size(max = 50)
    @Column(name = "PROTOCOL")
    private String protocol;
    @Size(max = 100)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "SMTP_AUTH")
    private boolean smtp_auth;
    @Column(name = "START_TLS")
    private boolean start_tls;
    @Column(name = "SERVIDOR")
    private boolean servidor;
    @Column(name = "VERIFICADO")
    private boolean verificado;

    public boolean getSmtp_auth(){
        return smtp_auth;
    }
    
    public boolean getStart_tls(){
        return start_tls;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracion != null ? idConfiguracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConfiguracionCorreo)) {
            return false;
        }
        ConfiguracionCorreo other = (ConfiguracionCorreo) object;
        if ((this.idConfiguracion == null && other.idConfiguracion != null) || (this.idConfiguracion != null && !this.idConfiguracion.equals(other.idConfiguracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gl05.bad.domain.ConfiguracionCorreo[ idConfiguracion=" + idConfiguracion + " ]";
    }
    
}
