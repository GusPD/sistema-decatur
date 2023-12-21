package com.gl05.bad.controller;

import com.gl05.bad.domain.ConfiguracionCorreo;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.ConfiguracionCorreoService;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ConfiguracionCorreoController {
    
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private ConfiguracionCorreoService configuracionCorreoService;
    
    //Función que redirige a la vista de los configuración de correos
    @GetMapping("/ConfiguracionCorreos")
    public String mostrarConfiguracionCorreoProyecto(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Configuración Correos");
        return "/ConfiguracionCorreo/ConfiguracionCorreo";
    }
    
    //Función que obtiene las configuraciones de correos
    @GetMapping(value="/configuracionCorreo/data", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<ConfiguracionCorreo> GetConfiguracionCorreos(@Valid DataTablesInput input) {
        return configuracionCorreoService.listarConfiguracionesCorreo(input);
    }

    //Función que agrega una configuración de correo a la base de datos
    @PostMapping("/AgregarConfiguracionCorreo")
    public ResponseEntity<String> AgregarConfiguracionCorreo(ConfiguracionCorreo configuracionCorreo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            if(configuracionCorreo.isServidor()){
                List<ConfiguracionCorreo> listaConfiguraciones = configuracionCorreoService.listarConfiguracionesCorreo();
                for (ConfiguracionCorreo configuracion : listaConfiguraciones) {
                    configuracion.setServidor(false);
                    configuracionCorreoService.actualizar(configuracion);
                }
            }
            configuracionCorreoService.agregar(configuracionCorreo);
            String mensaje = "Se ha agregado una configuración correo.";
            bitacoraService.registrarAccion("Agregar configuración correo");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar la configuración de correo.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que elimina una configuración de correo de la base de datos
    @PostMapping("/EliminarConfiguracionCorreo/{idConfiguracion}")
    public ResponseEntity<String> EliminarConfiguracionCorreo(ConfiguracionCorreo configuracionCorreo) {
        try {
            configuracionCorreoService.eliminar(configuracionCorreo);
            String mensaje = "Se ha eliminado una configuración de correo correctamente.";
            bitacoraService.registrarAccion("Eliminar configuración de correo");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar la configuración de correo.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que obtiene una configuración de correo de la base de datos
    @GetMapping(value = "/ObtenerConfiguracionCorreo/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ConfiguracionCorreo> ObtenerConfiguracionCorreo(@PathVariable Long id) {
        ConfiguracionCorreo configuracionCorreo = configuracionCorreoService.encontrar(id);
        if (configuracionCorreo != null) {
            return ResponseEntity.ok(configuracionCorreo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Función que actualiza un configuración de correo de la base de datos
    @PostMapping("/ActualizarConfiguracionCorreo")
    public ResponseEntity<String> ActualizarConfiguracionCorreo(ConfiguracionCorreo configuracionCorreo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            if(configuracionCorreo.isServidor()){
                List<ConfiguracionCorreo> listaConfiguraciones = configuracionCorreoService.listarConfiguracionesCorreo();
                for (ConfiguracionCorreo configuracion : listaConfiguraciones) {
                    configuracion.setServidor(false);
                    configuracionCorreoService.actualizar(configuracion);
                }
            }
            configuracionCorreoService.actualizar(configuracionCorreo);
            String mensaje = "Se ha actualizado la configuración de correo correctamente.";
            bitacoraService.registrarAccion("Actualizar configuración de correo");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar la configuración de correo.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para verificar configuración de correo
     @GetMapping("/VerificarConfiguracionCorreo")
    public ResponseEntity<String> VerificarConfiguracionCorreo(@RequestParam Long idConfiguracion) {
        try {
            ConfiguracionCorreo configuracionCorreo = configuracionCorreoService.encontrar(idConfiguracion);
            // Configuración del servidor SMTP
            Properties properties = new Properties();
            properties.put("mail.smtp.host", configuracionCorreo.getHost());
            properties.put("mail.smtp.port", configuracionCorreo.getPort());
            properties.put("mail.smtp.auth", configuracionCorreo.getSmtp_auth());
            properties.put("mail.smtp.starttls.enable", configuracionCorreo.getStart_tls());
            // Crear sesión de correo
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(configuracionCorreo.getUsername(), configuracionCorreo.getPassword());
                }
            });
            // Intentar enviar un mensaje de prueba
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(configuracionCorreo.getUsername()));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("destinatario@example.com"));
                message.setSubject("Prueba de conexión");
                message.setText("Este es un mensaje de prueba para verificar la configuración del correo.");
                Transport.send(message);
                configuracionCorreo.setVerificado(true);
                configuracionCorreoService.actualizar(configuracionCorreo);
                String mensaje = "Se ha verificado la configuración del correo correctamente.";
                bitacoraService.registrarAccion("Verificar configuración de correo");
                return ResponseEntity.ok(mensaje);
            } catch (MessagingException e) {
                String error = "Error al enviar el mensaje de prueba: " + e.getMessage();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            String error = "Ha ocurrido un error al verificar la configuración del correo.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
