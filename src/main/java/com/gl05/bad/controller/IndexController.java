package com.gl05.bad.controller;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.UserService;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Inicio");
        if (authentication.getAuthorities().isEmpty()) {
            // En caso de que el usuario no tenga permisos
            model.addAttribute("mensaje", "Usuario autenticado pero sin permisos");
        }
        Proyecto proyecto=new Proyecto();
        proyecto.setIdProyecto(0L);
        model.addAttribute("proyecto", proyecto);
        return "welcome";
    }
    
    @GetMapping("/ObtenerUsuarioMenu")
    public ResponseEntity<Map<String, Object>> obtenerUsuarioMenu(Authentication authentication, HttpSession session) {
        // Obtener el nombre del usuario autenticado
        String username = authentication.getName();

        // Crear el mapa para almacenar los datos a devolver en el response
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("username", username);

        // Devolver el mapa como respuesta
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMap);
    }
    
     @GetMapping("/accesoDenegado")
    public String acceso(Model model) {
        model.addAttribute("pageTitle", "Acceso Denegado");
        return "accesodenegado";
    }
    
}
