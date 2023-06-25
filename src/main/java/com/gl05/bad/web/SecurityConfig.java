package com.gl05.bad.web;

import com.gl05.bad.dao.UsuarioDao;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.BitacoraServiceImp;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import java.util.Properties;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final UserDetailsService userDetailsService;    
    
    @Autowired
    private final BitacoraServiceImp bitacoraService;
    
//    @Autowired
//    private JavaMailSender javaMailSender;
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("badmaestria08@gmail.com");
        mailSender.setPassword("wdayaatpxrhlpvri");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        Session session = Session.getDefaultInstance(props);
        mailSender.setSession(session);

        return mailSender;
    }

    @Bean
    public AuthenticationFailureHandler falloAutenticacionHandler() {
        return new FalloAutenticacion(javaMailSender(), userDetailsService);
    }

    @Bean
    protected AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    //Metodo que se utiliza para la reestricción de urls
    @Override
    protected void configure(HttpSecurity http) throws Exception {
          
        http.authorizeRequests()          
                //Aqui debo de poner todos los permisos de ver privilage para que haga el bloqueo al estar
                //deshabilitado/bloqueado o ambos
                
                //Esto generaba malas redirecciones si no se tiene un permiso al usuario
                //por eso lo quite
//              .antMatchers("/login", "/logout", "/")
//              .hasAnyAuthority("VIEW_INDEX")
                
                .antMatchers("/", "/logout")
                .authenticated()
    
                 //Reestrincion de vistas
                .antMatchers("/viewUsuarios")
                .hasAnyAuthority("VIEW_USUARIO_PRIVILAGE")
                .antMatchers("/viewRoles")
                .hasAnyAuthority("VIEW_ROLES_PRIVILAGE")
                .antMatchers("/viewFacultad")
                .hasAnyAuthority("VIEW_FACULTAD_PRIVILAGE")
                .antMatchers("/viewEscuelaPosgrado")
                .hasAnyAuthority("VIEW_ESCUELA_PRIVILAGE")
                .antMatchers("/GestionarAreaConocimiento")
                .hasAnyAuthority("VIEW_AREA_CONOCIMIENTO_PRIVILAGE")
                .antMatchers("/viewEstudiantes")
                .hasAnyAuthority("VIEW_ESTUDIANTE_PRIVILAGE")
                .antMatchers("/gestionarCoordinadorAcademico")
                .hasAnyAuthority("VIEW_COORDINADORES_PRIVILAGE")
                .antMatchers("/perfilCoordinadorAcademico/**")
                .hasAnyAuthority("VIEW_PERFIL_COORDINADOR_PRIVILAGE")
                .antMatchers("/GestionarAspiranteProfesor")
                .hasAnyAuthority("VIEW_ASPIRANTES_PRIVILAGE")
                .antMatchers("/PerfilAspiranteProfesor/**")
                .hasAnyAuthority("VIEW_PERFIL_ASPIRANTE_PRIVILAGE")
                .antMatchers("/GestionarMaestria")
                .hasAnyAuthority("VIEW_MAESTRIA_PRIVILAGE")
                .antMatchers("/DetalleMaestria/**")
                .hasAnyAuthority("VIEW_DETALLE_MAESTRIA_PRIVILAGE")
                .antMatchers("/InscribirMaestria")
                .hasAnyAuthority("VIEW_INSCRIBIR_MAESTRIA_PRIVILAGE")
                .antMatchers("/InscripcionEstudiantes/**")
                .hasAnyAuthority("VIEW_INSCRIBIR_ESTUDIANTES_PRIVILAGE")
                .antMatchers("/GestionarEstudiantesCohorte/**")
                .hasAnyAuthority("VIEW_ESTUDIANTES_COHORTE_PRIVILAGE")
                .antMatchers("/PostularProfesor")
                .hasAnyAuthority("VIEW_POSTULAR_PROFESOR_COHORTE_PRIVILAGE")
                .antMatchers("/GestionarPlanEstudio/**")
                .hasAnyAuthority("VIEW_PLANES_ESTUDIO_PRIVILAGE")
                .antMatchers("/DetallePlanEstudio/**")
                .hasAnyAuthority("VIEW_DETALLE_PLAN_PRIVILAGE")
                .antMatchers("/viewPrograma/**")
                .hasAnyAuthority("VIEW_PROGRAMA_ASIGNATURA_PRIVILAGE")
                .antMatchers("/viewActividad/**")
                .hasAnyAuthority("VIEW_ACTIVIDADES_PRIVILAGE")
                .antMatchers("/GestionarCohorte/**")
                .hasAnyAuthority("VIEW_COHORTE_PRIVILAGE")
                .antMatchers("/PostuladosCohorte/**")
                .hasAnyAuthority("VIEW_POSTULADOS_COHORTE_PRIVILAGE")
      
                //Agregados 08-junio
                .antMatchers("/GestionarPaises")
                .hasAnyAuthority("VIEW_PAISES_PRIVILAGE")
                .antMatchers("/ProfesorCohorte/**")
                .hasAnyAuthority("VIEW_PROFESOR_COHORTE_PRIVILAGE")
                .antMatchers("/EstudiantesInscritos")
                .hasAnyAuthority("VIEW_REPORTE_INSCRIPCION_ESTUDIANTES_PRIVILAGE")
                .antMatchers("/AreasAcademicas")
                .hasAnyAuthority("VIEW_REPORTE_DISTRIBUCION_CATEGORIZADA_PRIVILAGE")
                .antMatchers("/DetalleEstudiante/**")
                .hasAnyAuthority("VIEW_DETALLE_ESTUDIANTE_PRIVILAGE")
                .antMatchers("/DetalleCohorte/**")
                .hasAnyAuthority("VIEW_DETALLE_COHORTE_PRIVILAGE")
                .antMatchers("/AsignaturasInscripcionCohorte/**")
                .hasAnyAuthority("VIEW_INSCRIPCIONES_MATERIAS_PRIVILAGE")
                //Aqui falta poner el permiso de ruta de Docentes contratados
                
                .and()
                .formLogin() 
                .loginPage("/login")
                .loginProcessingUrl("/authenticate") // Ruta para procesar la autenticación
                .usernameParameter("username") // Nombre del campo de nombre de usuario en el formulario
                .passwordParameter("password") // Nombre del campo de contraseña en el formulario
                .defaultSuccessUrl("/welcome") // Ruta de redirección después de un inicio de sesión exitoso
                .failureUrl("/login?error=true") // Ruta de redirección después de un inicio de sesión fallido
                .failureHandler(falloAutenticacionHandler())
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accesoDenegado");

        //.failureUrl("/login?error=true");
        http.logout()
        .logoutUrl("/logout") // Ruta para cerrar sesión
        .logoutSuccessHandler(logoutSuccessHandler()) // Manejador de cierre de sesión personalizado
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID");
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (AuthenticationSuccessHandler) new CustomAuthenticationSuccessHandler();
    }

    @Autowired
    private UsuarioDao usuarioDao;
    
    private class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            String username = authentication.getName();
            
            Usuario usuario = usuarioDao.findByUsername(username);
            usuario.setNumerointentos(0);
            usuarioDao.save(usuario);
            bitacoraService.registrarInicioSesion(username);
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
    
    private class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

      @Override
      public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
          String username = authentication.getName();
          bitacoraService.registrarCerrarSesion(username);
          response.sendRedirect("/");
      }
    }
}
