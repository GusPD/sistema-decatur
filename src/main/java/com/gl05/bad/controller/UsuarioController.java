package com.gl05.bad.controller;

import com.gl05.bad.domain.Empresa;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Rol;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.EmpresaService;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.RolesService;
import com.gl05.bad.servicio.UserService;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Función que redirige a la vista de los usuarios
    @GetMapping("/Usuarios")
    public String mostrarUsuarios(Model model) {
        model.addAttribute("pageTitle", "Usuarios");

        var elemento = userService.listaUsuarios();
        var elementoRol = rolesService.listaRoles();
        var proyectos = proyectoService.listaProyectos();
        var empresas = empresaService.listaEmpresas();
        
        model.addAttribute("empresas", empresas);
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("usuarios", elemento);
        model.addAttribute("roles", elementoRol);
        model.addAttribute("usuario", new Usuario());
        return "/Usuario/GestionarUsuarios";
    }
    
    //Función que obtiene los usuarios de la base de datos
    @GetMapping(value="/usuarios/data", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Usuario> getUsuarios(@Valid DataTablesInput input) {
        return userService.listarUsuarios(input);
    }

    //Función que agrega un usuario de la base de datos
    @PostMapping("/AgregarUsuario")
    public ResponseEntity<String> AgregarUsuario(Usuario usuario, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            String password = usuario.getPassword();
            String encryptedPassword = passwordEncoder.encode(password);
            usuario.setPassword(encryptedPassword);
            userService.agregar(usuario);
            String mensaje = "Se ha agregado un usuario.";
            bitacoraService.registrarAccion("Agregar usuario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar al usuario.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que elimina un usuario de la base de datos
    @PostMapping("/EliminarUsuario/{idUsuario}")
    public ResponseEntity<String> EliminarUsuario(Usuario usuario) {
        try {
            userService.eliminar(usuario);
            String mensaje = "Se ha eliminado al usuario correctamente.";
            bitacoraService.registrarAccion("Eliminar usuario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el usuario";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que obtiene un usuario de la base de datos
    @GetMapping(value="/ObtenerUsuario/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = userService.encontrar(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Función que actualiza un usuario de la base de datos
    @PostMapping("/ActualizarUsuario")
    public ResponseEntity<String> ActualizarUsuario(Usuario usuario, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            String password = usuario.getPassword();

            if (password != null && !password.isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(password);
                usuario.setPassword(encryptedPassword);
            } else {
                Usuario existingUsuario = userService.encontrar(usuario.getIdUsuario());
                usuario.setPassword(existingUsuario.getPassword());
            }

            userService.actualizar(usuario);
            String mensaje = "Se ha actualizado el usuario correctamente.";
            bitacoraService.registrarAccion("Actualizar usuario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el usuario.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que redirige a la vista de los usuarios
    @GetMapping("/PerfilUsuario")
    public String mostrarPerfilUsuario(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Perfil Usuario");
        String username = authentication.getName();
        Proyecto newProyecto = proyectoService.encontrar(0L);
        Usuario newUsuario = userService.encontrarUsername(username);
        Set<Empresa> listaEmpresas = newUsuario.getEmpresas();
        String empresas = "";
        for (Empresa empresa : listaEmpresas) {
            empresas += empresa.getNombre() + "<br>";
        }
        Set<Proyecto> listaProyectos = newUsuario.getProyectos();
        String proyectos = "";
        for (Proyecto proyecto : listaProyectos) {
            proyectos += proyecto.getNombre() + "<br>";
        }
        Set<Rol> roles = newUsuario.getRoles();
        String rolesUsuario = "";
        for (Rol rol : roles) {
            String nombreRol = rol.getNombre();
            if (nombreRol != null && !nombreRol.isEmpty()) {
                rolesUsuario += Character.toUpperCase(nombreRol.charAt(0)) + nombreRol.substring(1).toLowerCase();
            }
        }
        model.addAttribute("proyecto", newProyecto);       
        model.addAttribute("empresas", empresas);
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("roles", rolesUsuario);
        model.addAttribute("usuario", newUsuario);
        return "/Usuario/PerfilUsuario";
    }

    //Función que actualiza un usuario de la base de datos
    @PostMapping("/ActualizarPerfilUsuario")
    public ResponseEntity<String> ActualizarPerfilUsuario(HttpServletRequest request, RedirectAttributes redirectAttributes,
        @RequestParam("idUsuario") String idUsuario, 
        @RequestParam("nombre") String nombre,
        @RequestParam("username") String username,
        @RequestParam("email") String email,
        @RequestParam("password") String password) {
        try {
            Usuario usuario = userService.encontrar(Long.parseLong(idUsuario));
            usuario.setNombre(nombre);
            usuario.setUsername(username);
            usuario.setEmail(email);
            if (password != null && !password.isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(password);
                usuario.setPassword(encryptedPassword);
            }
            userService.actualizar(usuario);
            String mensaje = "Se ha actualizado el peril del usuario correctamente.";
            bitacoraService.registrarAccion("Actualizar perfil de usuario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el perfil del usuario.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
