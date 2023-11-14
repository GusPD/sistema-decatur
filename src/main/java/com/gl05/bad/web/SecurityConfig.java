package com.gl05.bad.web;

import com.gl05.bad.dao.UsuarioDao;
import com.gl05.bad.domain.ConfiguracionCorreo;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.ConfiguracionCorreoServiceImp;
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
    
    @Autowired
    private final ConfiguracionCorreoServiceImp configuracionCorreoService;
    
    @Autowired
    private UsuarioDao usuarioDao;
    
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        ConfiguracionCorreo configuracionCorreo = configuracionCorreoService.obtenerConfiguracionCorreo();
        if (configuracionCorreo!=null) {
            mailSender.setHost(configuracionCorreo.getHost());
            mailSender.setPort(Integer.parseInt(configuracionCorreo.getPort()));
            mailSender.setUsername(configuracionCorreo.getUsername());
            mailSender.setPassword(configuracionCorreo.getPassword());
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", configuracionCorreo.getProtocol());
            props.put("mail.smtp.auth", String.valueOf(configuracionCorreo.getSmtp_auth()));
            props.put("mail.smtp.starttls.enable", String.valueOf(configuracionCorreo.getStart_tls()));
            props.put("mail.debug", "true");
            Session session = Session.getDefaultInstance(props);
            mailSender.setSession(session);
        }
        return mailSender;
    }

    @Bean
    public AuthenticationFailureHandler falloAutenticacionHandler() {
        return new FalloAutenticacion();
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
        http
        .authorizeRequests(authorizeRequests -> authorizeRequests
            // Aquí se deben definir todas las restricciones de acceso
            .antMatchers("/", "/logout").authenticated()
            .antMatchers("/GestionarUsuarios").hasAuthority("GESTIONAR_USUARIO_PRIVILAGE")
            .antMatchers("/GestionarRoles").hasAuthority("GESTIONAR_ROL_PRIVILAGE")
            .antMatchers("/GestionarBitacora").hasAuthority("GESTIONAR_BITACORA_PRIVILAGE")
        )
        .formLogin(formLogin -> formLogin
            .loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/welcome")
            .failureUrl("/login?error=true")
            .failureHandler(falloAutenticacionHandler())
            .successHandler(authenticationSuccessHandler())
            .permitAll()
        )
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .accessDeniedPage("/accesoDenegado")
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessHandler(logoutSuccessHandler())
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        );
    }


    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (AuthenticationSuccessHandler) new CustomAuthenticationSuccessHandler();
    }
    
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
