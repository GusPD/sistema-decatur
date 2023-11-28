package com.gl05.bad.controller;

import com.gl05.bad.domain.ConfiguracionCorreo;
import com.gl05.bad.domain.Correo;
import com.gl05.bad.domain.Propietario;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.ConfiguracionCorreoService;
import com.gl05.bad.servicio.CorreoService;
import com.gl05.bad.servicio.PropietarioService;
import com.gl05.bad.servicio.ProyectoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NotificacionController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private ConfiguracionCorreoService configuracionCorreoService;

    @Autowired
    private CorreoService correoService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private PropietarioService propietarioService;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    //Función para redigir a la vista de notificacion
    @GetMapping("/EnviarNotificacion/{idProyecto}")
    public String mostrarEnviarCorreo(Model model, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Enviar Notificación");
        List<ConfiguracionCorreo> listaRemitentes = configuracionCorreoService.listarCorreosEnvio();
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        List<Correo> listaCorreosActivos = correoService.listarCorreosVentaActiva(newProyecto);
        List<Correo> listaCorreosProyecto = correoService.listarCorreosProyecto(newProyecto);
        model.addAttribute("listaCorreosActivos", listaCorreosActivos);
        model.addAttribute("listaCorreosProyecto", listaCorreosProyecto);
        model.addAttribute("remitentes", listaRemitentes);
        model.addAttribute("proyecto", newProyecto);
        return "/Notificaciones/Notificacion";
    }

    //Función para redigir a la vista de notificacion
    @GetMapping("/Contactos/{idProyecto}")
    public String mostrarContactos(Model model, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Contactos");
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        List<Propietario> listaPropietarios = propietarioService.listaPropietariosProyecto(newProyecto);
        List<String> tiposCorreo = listarTiposCorreos();
        model.addAttribute("tiposCorreo", tiposCorreo);
        model.addAttribute("propietarios", listaPropietarios);
        model.addAttribute("proyecto", newProyecto);
        return "/Notificaciones/Contactos";
    }

    //Función que obtiene los correos del proyecto
    @GetMapping("/correosProyecto/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<Correo> GetCorreosProyecto(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return correoService.listarCorreosProyecto(input, idProyecto);
    }

    //Función para enviar correos
    @PostMapping("/EnviarCorreo")
    public ResponseEntity<String> EnviarCorreo(HttpServletRequest request, RedirectAttributes redirectAttributes,
    @RequestParam("remitente") Long idRemitente,
    //@RequestParam("destinatarios") List<Long> destinatarios,
    @RequestParam("idProyecto") Long idProyecto,
    @RequestParam(value ="propietarioIndividual", required = false) List<String> propietarioIndividual,
    @RequestParam(value ="destinatario", required = false) String destinatario,
    @RequestParam("destino") String destino,
    @RequestParam("asunto") String asunto,
    @RequestParam(value ="cc", required = false) String cc,
    @RequestParam(value ="cco", required = false) String bcc,
    @RequestParam(value ="mensaje", required = false) String msj,
    @RequestParam(value = "adjuntos", required = false) MultipartFile[] adjuntos) {
        try {
            // Verificar la suma de tamaños de todos los archivos adjuntos
            long totalSize = 0;
            if (adjuntos != null && adjuntos.length > 0) {
                for (MultipartFile adjunto : adjuntos) {
                    if (adjunto != null && !adjunto.isEmpty()) {
                        totalSize += adjunto.getSize();
                    }
                }
            }
            // Verificar si la suma supera el límite
            long maxSizeBytes = 20 * 1024 * 1024; // 20 MB
            if (totalSize > maxSizeBytes) {
                String error = "La archivos adjuntos son demasiado grande.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }else{
                //Lista de correos de envío
                List<String> listaEnvios = new ArrayList<>();
                if (destino.equals("Individual")) {
                    listaEnvios = Arrays.asList(destinatario.split(","));
                } else if(destino.equals("Propietarios")){
                    Proyecto proyecto = proyectoService.encontrar(idProyecto);
                    List<Correo> listaPropietarios = correoService.listarCorreosVentaActiva(proyecto);
                    System.out.println(listaPropietarios+"SE HA INGRESADO" +proyecto);
                    for (Correo correo : listaPropietarios) {
                        listaEnvios.add(correo.getCorreo());
                    }
                } else if(destino.equals("SeleccionarPropietarios")){
                    listaEnvios = propietarioIndividual;
                } else if(destino.equals("PropietariosIndividual")){
                    List<String> listaDestinatarios = Arrays.asList(destinatario.split(","));
                    for (String destinatarioString : listaDestinatarios) {
                        listaEnvios.add(destinatarioString);
                    }
                    Proyecto proyecto = proyectoService.encontrar(idProyecto);
                    List<Correo> listaPropietarios = correoService.listarCorreosVentaActiva(proyecto);
                    for (Correo correo : listaPropietarios) {
                        listaEnvios.add(correo.getCorreo());
                    }
                } else if(destino.equals("SeleccionarPropietariosIndividual")){
                    listaEnvios = propietarioIndividual;
                    Proyecto proyecto = proyectoService.encontrar(idProyecto);
                    List<Correo> listaPropietarios = correoService.listarCorreosVentaActiva(proyecto);
                    for (Correo correo : listaPropietarios) {
                        listaEnvios.add(correo.getCorreo());
                    }
                }
                //Envío del correo a los destinatarios
                for (String correo : listaEnvios) {
                    JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
                    // Obtener la configuración de correo desde la base de datos
                    ConfiguracionCorreo configuracionCorreo = configuracionCorreoService.encontrar(idRemitente);
                    String usernameString = configuracionCorreo.getUsername();
                    // Verificar si se pudo obtener la configuración de correo
                    if (configuracionCorreo != null) {
                        mailSender.setHost(configuracionCorreo.getHost());
                        mailSender.setPort(Integer.parseInt(configuracionCorreo.getPort()));
                        mailSender.setUsername(configuracionCorreo.getUsername());
                        mailSender.setPassword(configuracionCorreo.getPassword());
                        Properties javaMailProperties = mailSender.getJavaMailProperties();
                        javaMailProperties.put("mail.transport.protocol", configuracionCorreo.getProtocol());
                        javaMailProperties.put("mail.smtp.auth", String.valueOf(configuracionCorreo.getSmtp_auth()));
                        javaMailProperties.put("mail.smtp.starttls.enable", String.valueOf(configuracionCorreo.getStart_tls()));
                    }
                    // Crear un mensaje de correo con soporte para archivos adjuntos
                    MimeMessage mensajeCorreo = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mensajeCorreo, true);
                    // Configurar el remitente, destinatario, asunto y cuerpo del mensaje
                    helper.setFrom(usernameString, configuracionCorreo.getName());
                    helper.setTo(correo);
                    helper.setSubject(asunto);
                    helper.setText(msj, true);
                    // Agregar destinatarios en copia (CC) si se proporcionan
                    List<String> listaCorreosCC =  null;
                    if (cc != null && !cc.isEmpty()) {
                        listaCorreosCC =  Arrays.asList(cc.split(","));
                        for (String correoCC : listaCorreosCC) {
                            helper.addCc(correoCC);
                        }
                    }
                    // Agregar destinatarios en copia oculta (BCC) si se proporcionan
                    List<String> listaCorreosBCC =  null;
                    if (bcc != null && !bcc.isEmpty()) {
                        listaCorreosBCC =  Arrays.asList(bcc.split(","));
                        for (String correoBCC : listaCorreosBCC) {
                            helper.addBcc(correoBCC);
                        }
                    }
                    // Adjuntar archivos al correo
                    if (adjuntos != null && adjuntos.length > 0) {
                        for (MultipartFile adjunto : adjuntos) {
                            helper.addAttachment(adjunto.getOriginalFilename(), adjunto);
                        }
                    }
                    // Enviar el correo
                    javaMailSender.send(mensajeCorreo);
                }

                String mensaje = "Se ha enviado el correo correctamente.";
                bitacoraService.registrarAccion("Enviar correo");
                return ResponseEntity.ok(mensaje);
            }   
        } catch (Exception e) {
            String error = "Ocurrió un error al enviar el correo.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para listar los tipos de correos
    public List<String> listarTiposCorreos() {
        List<String> tiposCorreos = Arrays.asList("Trabajo", "Privado","Personal","Institucional");
        return tiposCorreos;
    }
}
