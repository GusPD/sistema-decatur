package com.gl05.bad.web;

import com.gl05.bad.dao.UsuarioDao;
import com.gl05.bad.domain.ConfiguracionCorreo;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.ConfiguracionCorreoServiceImp;
import com.gl05.bad.servicio.CorreoServiceImp;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class FalloAutenticacion implements AuthenticationFailureHandler {

    private JavaMailSender javaMailSender;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private UserDetailsService usuarioService;
    
    @Autowired
    private ConfiguracionCorreoServiceImp configuracionCorreoService;
    
    @Autowired
    private CorreoServiceImp correoService;

    public FalloAutenticacion(JavaMailSender javaMailSender, UserDetailsService usuarioService) {
        this.javaMailSender = javaMailSender;
        this.usuarioService = usuarioService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = null;
        
        try{
        String username = request.getParameter("username");
        
        Usuario usuario = usuarioDao.findByUsername(username);
        
        ConfiguracionCorreo configuracionCorreo = configuracionCorreoService.obtenerConfiguracionCorreo();

        if (username.equals(usuario.getUsername()) && usuario.isBloqueado() == true && usuario.isHabilitado() == false) {
           errorMessage = "Usuario Bloqueado e Inhabilitado! , Contacte al administrador del sistema para realizar las acciones pertinentes";
           HttpSession session = request.getSession();
           session.setAttribute("errorMessage", errorMessage);
           response.sendRedirect(request.getContextPath() + "/login");            
        } else if (username.equals(usuario.getUsername()) && usuario.isBloqueado() == true ) {
            //Cuando el usuario esta bloqueado por los 3 intentos
            errorMessage = "Usuario Bloqueado! , Contacte al administrador del sistema para realizar las acciones pertinentes";
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", errorMessage);
            response.sendRedirect(request.getContextPath() + "/login");
        } else if (username.equals(usuario.getUsername()) && usuario.isHabilitado() == false) {
            errorMessage = "Usuario Inhabilitado! , Contacte al administrador del sistema para realizar las acciones pertinentes";
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", errorMessage);
            response.sendRedirect(request.getContextPath() + "/login");       
         } else if (username.equals(usuario.getUsername()) || usuario.isBloqueado() == false) {
            //Incrementamos el contador de intentos fallidos
            int intentos = usuario.getIntentos() + 1;
            usuario.setIntentos(intentos);
            usuarioDao.save(usuario);

            //Verificar si el usuario ha sido bloqueado
            if (intentos >= 3) {
                usuario.setBloqueado(true);
                usuarioDao.save(usuario);
                // Enviar correo electrónico al administrador 
                String usuarioEmail = usuario.getEmail();
                String subject = "Usuario bloqueado";
                String message = "El usuario " + username + " con correo " + usuarioEmail + " ha sido bloqueado después de 3 intentos fallidos de inicio de sesión.";
                correoService.enviarCorreo(configuracionCorreo.getUsername(), subject, message);

                //response.sendRedirect("/errorpage");
                errorMessage = "Se ha bloqueado su usuario despues de 3 intentos, se ha enviado un correo al administrador para su desbloqueo";
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", errorMessage);
                response.sendRedirect(request.getContextPath() + "/login");
                
            } else {
                // Redirigir al usuario a la página de inicio de sesión
                errorMessage = "Credenciales Incorrectas, Ingresarlas nuevamente";
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", errorMessage);
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }
        }catch (NullPointerException e){       
            errorMessage = "Credenciales de inicio de sesión NO EXISTENTES, Usuario no encontrado.";
            // Almacenar el mensaje de error en la sesión
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", errorMessage);

            // Redireccionar a la página de inicio de sesión
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
