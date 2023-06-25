package com.gl05.bad.servicio;

import com.gl05.bad.dao.BitacoraDao;
import com.gl05.bad.domain.Bitacora;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
public class BitacoraServiceImp {
  @Autowired
    private BitacoraDao bitacoraDao;
  
  @Autowired
    private HttpServletRequest request;

    public void registrarInicioSesion(String usuario) {
        Bitacora bitacora = new Bitacora();
        bitacora.setNombreUsuario(usuario);
        bitacora.setNombreEvento("Inicio de sesión");
        bitacora.setHoraEvento(LocalDateTime.now());
        
        String ip = obtenerDireccionIP();
        bitacora.setIpEquipo(ip);

        bitacoraDao.save(bitacora);
    }
    
    public void registrarCerrarSesion(String usuario) {
        Bitacora bitacora = new Bitacora();
        bitacora.setNombreUsuario(usuario);
        bitacora.setNombreEvento("Cierre de sesión");
        bitacora.setHoraEvento(LocalDateTime.now());
        
        String ip = obtenerDireccionIPCierre();
        bitacora.setIpEquipo(ip);

        bitacoraDao.save(bitacora);
    }

    public void registrarAccion(String accion) {
        Bitacora bitacora = new Bitacora();
        bitacora.setNombreUsuario(obtenerUsuarioActual());
        bitacora.setNombreEvento(accion);
        bitacora.setHoraEvento(LocalDateTime.now());
        
        
        String ip = obtenerDireccionIP();
        bitacora.setIpEquipo(ip);

        bitacoraDao.save(bitacora);
    }
    
    private String obtenerDireccionIP() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication != null) {
          Object details = authentication.getDetails();
          if (details instanceof WebAuthenticationDetails) {
              WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
              return webDetails.getRemoteAddress();
          }
      }
      return null;
    }
    
    private String obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }
    
    private String obtenerDireccionIPCierre() {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}