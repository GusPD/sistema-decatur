package com.gl05.bad.controller;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.ResetPassword;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.CorreoServiceImp;
import com.gl05.bad.servicio.EmpresaService;
import com.gl05.bad.servicio.ResetPasswordServiceImp;
import com.gl05.bad.servicio.UserServiceImp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {
    
    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private CorreoServiceImp correoService;
    
    @Autowired
    private UserServiceImp usuarioService;
    
    @Autowired
    private ResetPasswordServiceImp resetPasswordService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    //Función para redirigir a la vista de inicio
    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Inicio");
        if (authentication.getAuthorities().isEmpty()) {
            model.addAttribute("mensaje", "Usuario autenticado pero sin permisos");
        }
        var empresas = empresaService.listaEmpresas();    
        Proyecto proyecto=new Proyecto();
        proyecto.setIdProyecto(0L);
        model.addAttribute("empresas", empresas);
        model.addAttribute("proyecto", proyecto);
        return "welcome";
    }
    
    //Función para obtener el menú del usuario
    @GetMapping("/ObtenerUsuarioMenu")
    public ResponseEntity<Map<String, Object>> obtenerUsuarioMenu(Authentication authentication, HttpSession session) {
        String username = authentication.getName();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("username", username);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMap);
    }
    
    //Función para redirigir a la vista de acceso denegado
    @GetMapping("/accesoDenegado")
    public String acceso(Model model) {
        model.addAttribute("pageTitle", "Acceso Denegado");
        return "accesodenegado";
    }
    
    //Función para redirigir a la vista de solicitud de restablecimiento de contraseña
    @GetMapping("/password/reset")
    public String pageResetPassword(Model model) {
        model.addAttribute("pageTitle", "Restablecer Constraseña");
        return "solicitud-reset-password";
    }
    
    //Función para enviar la solicitud de restablecimiento de contraseña
    @PostMapping("/RestablecerPassword")
    public ResponseEntity RestablecerPassword(@RequestParam("username") String username, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            username=username.trim();
            Usuario usuarioRegistrado=usuarioService.encontrarUsername(username);
            if(usuarioRegistrado != null){
                String serverBaseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
                String uniqueToken = UUID.randomUUID().toString();
                String resetPasswordUrl = serverBaseUrl + "/reset-password?token=" + uniqueToken;
                String mensajeCorreo="Estimado.<br><br>Hemos recibido una solicitud para restablecer la contraseña de su cuenta. Para completar este proceso, haga clic en el siguiente enlace:<br><br><a href='"+resetPasswordUrl+"'>Restablecer Constraseña</a><br><br>Si no ha solicitado el restablecimiento de la contraseña, puede ignorar este correo electrónico, su contraseña actual seguirá siendo válida.";
                correoService.enviarCorreo(usuarioRegistrado.getEmail(), "Restablecer Contraseña", mensajeCorreo);
                ResetPassword resetPassword = new ResetPassword();
                resetPassword.setUsername(username);
                resetPassword.setToken(uniqueToken);
                LocalDate fechaActual = LocalDate.now();
                LocalDate fechaAumentada = fechaActual.plusDays(1);
                Date fecha = Date.from(fechaAumentada.atStartOfDay(ZoneId.systemDefault()).toInstant());
                resetPassword.setFecha(fecha);
                resetPasswordService.agregar(resetPassword);
                String mensaje = "Se ha enviado la solicitud para restablecer la contraseña.";
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "Ocurrió un error, el usuario no se encuentra registrado en el sistema.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            String error = "Ocurrió un error al enviar la solicitud para restablecer la contraseña.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función para redirigir a la vista de restablecer contraseña
    @GetMapping("/reset-password")
    public String Restablecer(Model model, @RequestParam("token") String token) {
        model.addAttribute("pageTitle", "Restablecer Constraseña");
        try {
            ResetPassword tokenRegistrado=resetPasswordService.encontrarToken(token);
            if(tokenRegistrado != null){
                if(!tokenRegistrado.getToken().equals(token)){
                    String error = "Ocurrió un error, link no válido.";
                    model.addAttribute("error", error);
                } 
            }else{
                String error = "Ocurrió un error, link no válido.";
                model.addAttribute("error", error);
            }
        } catch (Exception e) {
            String error = "Ocurrió un error, link no válido.";
            model.addAttribute("error", error);
        }
        model.addAttribute("token", token);
        return "reset-password";
    }
    
    //Función para el restablecimiento de la contraseña
    @PostMapping("/reset-password-new")
    public String RestablecerNuevaContraseña(Model model, @RequestParam("token") String token, @RequestParam("password") String password) {
        try {
            ResetPassword tokenRegistrado=resetPasswordService.encontrarToken(token);
            if(tokenRegistrado != null){
                if(tokenRegistrado.getToken().equals(token)){
                    Usuario usuario = usuarioService.encontrarUsername(tokenRegistrado.getUsername());
                    String encryptedPassword = passwordEncoder.encode(password);
                    usuario.setPassword(encryptedPassword);
                    usuarioService.actualizar(usuario);
                    List<ResetPassword> listaTokens = resetPasswordService.listaTokens();
                    for (var valor : listaTokens) {
                        if(valor.getUsername().equals(usuario.getUsername())){
                            resetPasswordService.eliminar(valor);
                        }
                    }
                    String mensaje="Se ha actualizado la contraseña.";
                    model.addAttribute("mensaje", mensaje);
                }else{
                    String error = "Ocurrió un error, no se puede restablecer la contraseña.";
                    model.addAttribute("error", error);
                } 
            }else{
                String error = "Ocurrió un error, no se puede restablecer la contraseña.";
                model.addAttribute("error", error);
            }
        } catch (Exception e) {
            String error = "Ocurrió un error, no se puede restablecer la contraseña.";
            model.addAttribute("error", error);
        }
        return "login";
    }
}
