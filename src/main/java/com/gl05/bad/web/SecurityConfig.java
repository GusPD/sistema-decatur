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
        mailSender.setUsername("gustavopineda400@gmail.com");
        mailSender.setPassword("Herminia22Gustavo");

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
                
                .antMatchers("/", "/logout")
                .authenticated()
    
                 //Reestrincion de vistas
                .antMatchers("/GestionarUsuarios")
                .hasAnyAuthority("GESTIONAR_USUARIO_PRIVILAGE")
                .antMatchers("/GestionarRoles")
                .hasAnyAuthority("GESTIONAR_ROL_PRIVILAGE")
                .antMatchers("/GestionarBitacora")
                .hasAnyAuthority("GESTIONAR_BITACORA_PRIVILAGE")
                
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
            usuario.setIntentos(0);
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
