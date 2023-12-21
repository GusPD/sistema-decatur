package com.gl05.bad.servicio;

import com.gl05.bad.dao.BitacoraDao;
import com.gl05.bad.domain.Bitacora;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BitacoraServiceImp implements BitacoraService{
    @Autowired
    private BitacoraDao bitacoraDao;
  
    @Autowired
    private HttpServletRequest request;
  
    @Override
    @Transactional(readOnly = true)
    public List<Bitacora> listaBitacora() {
        return (List<Bitacora>) bitacoraDao.findAll();
    }
    
    @Override
    public void registrarInicioSesion(String usuario) {
        Bitacora bitacora = new Bitacora();
        bitacora.setUsername(usuario);
        bitacora.setEvento("Inicio de sesión");
        bitacora.setHora(LocalDateTime.now());
        
        String ip = obtenerDireccionIP();
        bitacora.setIpEquipo(ip);

        bitacoraDao.save(bitacora);
    }
    
    @Override
    public void registrarCerrarSesion(String usuario) {
        Bitacora bitacora = new Bitacora();
        bitacora.setUsername(usuario);
        bitacora.setEvento("Cierre de sesión");
        bitacora.setHora(LocalDateTime.now());
        
        String ip = obtenerDireccionIPCierre();
        bitacora.setIpEquipo(ip);

        bitacoraDao.save(bitacora);
    }

    @Override
    public void registrarAccion(String accion) {
        Bitacora bitacora = new Bitacora();
        bitacora.setUsername(obtenerUsuarioActual());
        bitacora.setEvento(accion);
        bitacora.setHora(LocalDateTime.now());
        
        String ip = obtenerDireccionIP();
        bitacora.setIpEquipo(ip);

        bitacoraDao.save(bitacora);
    }
    
    @Override
    @Transactional(readOnly=true)
    public DataTablesOutput<Bitacora> listarBitacora(DataTablesInput input, LocalDateTime fechaInicio, LocalDateTime fechaFin, String usuario) {
        Specification<Bitacora> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (fechaInicio != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("hora"), fechaInicio));
            }
            if (fechaFin != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("hora"), fechaFin));
            }
            if (!usuario.isEmpty()) {
                predicates.add(builder.equal(root.get("username"), usuario));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return (DataTablesOutput<Bitacora>)bitacoraDao.findAll(input, specification);
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