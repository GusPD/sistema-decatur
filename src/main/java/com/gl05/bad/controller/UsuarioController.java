package com.gl05.bad.controller;

import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.RolesService;
import com.gl05.bad.servicio.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private PasswordEncoder passwordEncoder;

    //Obtener los usuarios y mostrarlos en tablas
    @GetMapping("/viewUsuarios")
    public String mostrarUsuarios(Model model) {
        model.addAttribute("pageTitle", "Usuarios");

        var elemento = userService.listaUsuarios();
        model.addAttribute("usuarios", elemento);

        var elementoRol = rolesService.listaRoles();
        model.addAttribute("roles", elementoRol);
        model.addAttribute("usuario", new Usuario());

        return "/Usuarios/GestionarUsuarios2";
    }
    
    @GetMapping("/usuarios/data")
    @ResponseBody
    public DataTablesOutput<Usuario> getUsuarios(@Valid DataTablesInput input) {
        return userService.listarUsuarios(input);
    }

    @PostMapping("/AgregarUsuario")
    public ResponseEntity AgregarUsuario(Usuario usuario, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            String password = usuario.getPassword();
            String encryptedPassword = passwordEncoder.encode(password);
            usuario.setPassword(encryptedPassword);
            userService.AgregarUsuarios(usuario);
            String mensaje = "Se ha agregado un usuario.";
            bitacoraService.registrarAccion("Agregar usuario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar al usuario.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

//        return "redirect:/viewUsuarios";
    }

    @PostMapping("/EliminarUsuario/{idUsuario}")
    public ResponseEntity EliminarUsuario(Usuario usuario) {
        try {
            userService.eliminarUsuario(usuario);
             String mensaje = "Se ha eliminado al usuario correctamente.";
             bitacoraService.registrarAccion("Eliminar usuario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el usuario";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewUsuarios";
    }

    @GetMapping("/ObtenerUsuario/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = userService.encontrarUsuario(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ActualizarUsuario")
    public ResponseEntity ActualizarUsuario(Usuario usuario, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            String password = usuario.getPassword();

            if (password != null && !password.isEmpty()) {
                // Se proporcionó una nueva contraseña, realizar validación y encriptación
                String encryptedPassword = passwordEncoder.encode(password);
                usuario.setPassword(encryptedPassword);
            } else {
                // No se proporcionó una nueva contraseña, mantener la existente en la base de datos
                Usuario existingUsuario = userService.encontrarUsuario(usuario.getIdUsuario());
                usuario.setPassword(existingUsuario.getPassword());
            }

            userService.actualizarUsuario(usuario);
            String mensaje = "Se ha actualizado el usuario correctamente.";
            bitacoraService.registrarAccion("Actualizar usuario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el usuario.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewUsuarios";
    }

}
