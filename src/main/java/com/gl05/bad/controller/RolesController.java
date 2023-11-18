package com.gl05.bad.controller;

import com.gl05.bad.domain.Rol;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.PermisosService;
import com.gl05.bad.servicio.RolesService;
import com.gl05.bad.servicio.UserServiceImp;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RolesController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private PermisosService permisosService;

    @Autowired
    private UserServiceImp usuarioService;

    //Función que redirige a la vista de los roles
    @GetMapping("/Roles")
    public String mostrarRoles(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Roles");
        var elemento = rolesService.listaRoles();
        var elementoPermiso = permisosService.listaPermisos();
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        model.addAttribute("usuario", usuario);
        model.addAttribute("Roles", elemento);
        model.addAttribute("Permisos", elementoPermiso);
        return "/Roles/GestionarRoles";
    }
    
    //Función que obtiene los roles de la base de datos
    @GetMapping("/roles/data")
    @ResponseBody
    public DataTablesOutput<Rol> getRoles(@Valid DataTablesInput input) {
        return rolesService.listarRoles(input);
    }

    //Función que agrea un rol en la base de datos
    @PostMapping("/AgregarRol")
    public ResponseEntity<String> AgregarRol(Rol rol, HttpServletRequest request, RedirectAttributes redirectAttributes) {
         
        try {
            rolesService.agregar(rol);
            String mensaje = "Se ha agregado un rol.";
            bitacoraService.registrarAccion("Agregar rol");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el rol.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que elimina un rol de la base de datos
    @PostMapping("/EliminarRol/{idRol}")
    public ResponseEntity<String> EliminarRol(Rol rol, RedirectAttributes redirectAttributes) {
        try {
            rolesService.eliminar(rol);
            String mensaje = "Se ha eliminado el rol correctamente.";
            bitacoraService.registrarAccion("Eliminar rol");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el rol";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que obtiene un rol de la base de datos
    @GetMapping("/ObtenerRol/{id}")
    public ResponseEntity<Rol> obtenerRol(@PathVariable Long id) {
        Rol rol = rolesService.encontrar(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //Fucción que actualiza un rol de la base de datos
    @PostMapping("/ActualizarRol")
    public ResponseEntity<String> ActualizarRol(Rol rol, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            rolesService.actualizar(rol);
            String mensaje = "Se ha actualizado el rol correctamente.";
            bitacoraService.registrarAccion("Actualizar rol");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
           String error = "Ha ocurrido un error al actualizar el rol.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
