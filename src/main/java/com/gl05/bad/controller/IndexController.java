package com.gl05.bad.controller;

import com.gl05.bad.domain.AspiranteProfesor;
import com.gl05.bad.domain.CoordinadorAcademico;
import com.gl05.bad.domain.Maestria;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.AspiranteProfesorService;
import com.gl05.bad.servicio.CoordinadorAcademicoService;
import com.gl05.bad.servicio.MaestriaService;
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
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @Autowired
    private MaestriaService maestriaService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CoordinadorAcademicoService coordinadorAcademicoService;
    
    @Autowired
    private AspiranteProfesorService aspiranteProfesorService;
    
    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Inicio");
         String username = authentication.getName();
//         String usuarioCoordinador = coordinadorAcademicoService.buscarPerfil(username);
//         String usuarioAspirante=aspiranteProfesorService.buscarPerfil(username);
//         
//         model.addAttribute("usuarioCoordinador", usuarioCoordinador);
//         model.addAttribute("usuarioAspirante", usuarioAspirante);
//         model.addAttribute("username", username);
        Usuario usuario = userService.encontrarUsuarioPorUsername(username);
        Long idUsuarioLong = usuario.getIdUsuario();
        Integer idUsuario = idUsuarioLong.intValue();
        // Buscar las maestrías asociadas al ID del usuario
        Collection<Maestria> maestriasCoordinador = maestriaService.encontrarMaestrias(idUsuario);
        model.addAttribute("maestrias", maestriasCoordinador);
        
        if (authentication.getAuthorities().isEmpty()) {
            // En caso de que el usuario no tenga permisos
            model.addAttribute("mensaje", "Usuario autenticado pero sin permisos");
        }
        
        return "welcome";
    }
    
    @GetMapping("/ObtenerMaestriasCoordinador")
    public ResponseEntity<Collection<Maestria>> obtenerMaestriasCoordinador(Authentication authentication, HttpSession session) {
        // Obtener el nombre del usuario autenticado
        String username = authentication.getName();
        // Obtener el ID del usuario autenticado desde tu servicio de seguridad
        Usuario usuario = userService.encontrarUsuarioPorUsername(username);
        Long idUsuarioLong = usuario.getIdUsuario();
        Integer idUsuario = idUsuarioLong.intValue();
        // Buscar las maestrías asociadas al ID del usuario
        Collection<Maestria> maestriasCoordinador = maestriaService.encontrarMaestrias(idUsuario);
        return ResponseEntity.ok(maestriasCoordinador);
    }
    
//    //Esta pagina es cuando el usuario se equivoca 3 veces
//    @GetMapping("/errorpage")
//    public String error(Model model) {
//        model.addAttribute("pageTitle", "welcome2");
//        return "errorPage";
//    }
     
    @GetMapping("/ObtenerUsuarioMenu")
    public ResponseEntity<Map<String, Object>> obtenerUsuarioMenu(Authentication authentication, HttpSession session) {
        // Obtener el nombre del usuario autenticado
        String username = authentication.getName();
        // Obtener el ID del usuario autenticado desde tu servicio de seguridad
        Usuario usuario = userService.encontrarUsuarioPorUsername(username);
        Long idUsuarioLong = usuario.getIdUsuario();
        Integer idUsuario = idUsuarioLong.intValue();

        byte[] fotoBytes = null;
        String contentType = null;
        String tipoUsuario = "user"; // Valor predeterminado
        long idPerfil = 0;

        // Evaluar el tipo de usuario
        CoordinadorAcademico coordinador = coordinadorAcademicoService.encontrarPorIdUsuario(idUsuario);
        AspiranteProfesor aspiranteProfesor = aspiranteProfesorService.encontrarPorIdUsuario(idUsuario);

        if (coordinador != null) {
            // Usuario es Coordinador Académico
            tipoUsuario = "Coordinador Académico";
            Blob fotografiaCa = coordinador.getFotografiaCa();
            if (fotografiaCa != null) {
                // Obtener los bytes de la fotografía del coordinador
                try {
                    fotoBytes = fotografiaCa.getBytes(1, (int) fotografiaCa.length());
                    contentType = MediaType.IMAGE_PNG_VALUE; // Establecer el tipo de contenido como imagen PNG
                } catch (SQLException e) {
                    // Manejar la excepción si ocurre algún error al obtener los bytes de la imagen
                }
            }
            String nombresCa = coordinador.getNombresCa();
            String[] primerNombre = nombresCa.split(" ");
            username = primerNombre[0];
            idPerfil = coordinador.getIdCoorAca();
            
        } else if (aspiranteProfesor != null) {
            // Usuario es Aspirante Profesor
            tipoUsuario = "Aspirante a Profesor";
            Blob fotografiaAp = aspiranteProfesor.getFotografiaAp();
            if (fotografiaAp != null) {
                // Obtener los bytes de la fotografía del aspirante profesor
                try {
                    fotoBytes = fotografiaAp.getBytes(1, (int) fotografiaAp.length());
                    contentType = MediaType.IMAGE_PNG_VALUE; // Establecer el tipo de contenido como imagen PNG
                } catch (SQLException e) {
                    // Manejar la excepción si ocurre algún error al obtener los bytes de la imagen
                }
            }
            String nombresAp = aspiranteProfesor.getNombresAp();
            String[] primerNombre = nombresAp.split(" ");
            username = primerNombre[0];
            idPerfil = aspiranteProfesor.getIdAspiranteProfesor();
        }

        // Si no se encontró ninguna fotografía, cargar la imagen predeterminada
        if (fotoBytes == null || contentType == null) {
            try {
                // Leer la imagen predeterminada desde la carpeta static/images
                Resource resource = new ClassPathResource("static/images/usuario.png");
                fotoBytes = StreamUtils.copyToByteArray(resource.getInputStream());
                contentType = MediaType.IMAGE_PNG_VALUE; // Establecer el tipo de contenido como imagen PNG
            } catch (IOException e) {
                // Manejar la excepción si ocurre algún error al cargar la imagen predeterminada
            }
        }

        // Configurar las cabeceras de la respuesta
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));

        // Crear el mapa para almacenar los datos a devolver en el response
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("tipoUsuario", tipoUsuario);
        responseMap.put("username", username);
        responseMap.put("fotografia", fotoBytes);
        responseMap.put("idPerfil", idPerfil);

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
