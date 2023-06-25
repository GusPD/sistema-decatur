package com.gl05.bad.controller;

import com.gl05.bad.dao.RolesDao;
import com.gl05.bad.domain.Roles;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.PermisosService;
import com.gl05.bad.servicio.RolesService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private RolesDao rolDao;

    //Obtener los roles y mostrarlos en tablas
    @GetMapping("/viewRoles")
    public String mostrarRoles(Model model) {
        model.addAttribute("pageTitle", "Roles");

        var elemento = rolesService.listaRoles();
        model.addAttribute("Roles", elemento);

        var elementoPermiso = permisosService.listaPermisos();
        model.addAttribute("Permisos", elementoPermiso);
        //model.addAttribute("rol", new Roles());

        return "/Roles/GestionarRoles2";
    }
    
    @GetMapping("/roles/data")
    @ResponseBody
    public DataTablesOutput<Roles> getRoles(@Valid DataTablesInput input) {
        return rolesService.listarRoles(input);
    }
    

    @PostMapping("/AgregarRol")
    public ResponseEntity AgregarRol(Roles rol, HttpServletRequest request, RedirectAttributes redirectAttributes) {
         
        try {
            rolesService.AgregarRol(rol);
            String mensaje = "Se ha agregado un rol.";
            bitacoraService.registrarAccion("Agregar rol");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el rol.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewRoles";
    }

    @PostMapping("/EliminarRol/{idRol}")
    public ResponseEntity EliminarRol(Roles rol, RedirectAttributes redirectAttributes) {
        try {
            rolesService.eliminarRol(rol);
            String mensaje = "Se ha eliminado el rol correctamente.";
            bitacoraService.registrarAccion("Eliminar rol");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el rol";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewRoles";
    }

    @GetMapping("/ObtenerRol/{id}")
    public ResponseEntity<Roles> obtenerRol(@PathVariable Long id) {
        Roles rol = rolesService.encontrarRol(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/ActualizarRol")
    public ResponseEntity ActualizarRol(Roles rol, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            rolesService.actualizarRol(rol);
            String mensaje = "Se ha actualizado el rol correctamente.";
            bitacoraService.registrarAccion("Actualizar rol");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
           String error = "Ha ocurrido un error al actualizar el rol.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewRoles";
    }
    

}
