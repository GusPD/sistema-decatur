package com.gl05.bad.controller;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.EmpresaService;
import com.gl05.bad.servicio.ProyectoService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProyectoController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private ProyectoService proyectoService;
    
    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private UserService userService;
    
    //Función para redirigir a la vista de proyectos
    @GetMapping("/Proyectos")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Inicio");
        if (authentication.getAuthorities().isEmpty()) {
            model.addAttribute("mensaje", "Usuario autenticado pero sin permisos");
        }
        var empresas = empresaService.listaEmpresas();
        Proyecto proyecto=new Proyecto();
        proyecto.setIdProyecto(0L);
        String username = authentication.getName();
        Usuario usuario = userService.encontrarUsername(username);
        model.addAttribute("usuario", usuario);
        model.addAttribute("empresas", empresas);
        model.addAttribute("proyecto", proyecto);
        return "Proyecto/GestionarProyecto";
    }
    
    //Función para obtener los proyectos de la base de datos
    @GetMapping(value="/proyectos/data", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Proyecto> GetProyectos(@Valid DataTablesInput input) {
        return proyectoService.listarProyectos(input);
    }

    //Función para redirigir a la vista del proyecto
    @GetMapping("/Proyecto/{idProyecto}")
    public String MostrarProyecto(Model model, @PathVariable Long idProyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Inicio");
        Proyecto proyecto= proyectoService.encontrar(idProyecto);
        String username = authentication.getName();
        Usuario usuario = userService.encontrarUsername(username);
        Set<Proyecto> listaProyectosAsignados = usuario.getProyectos();
        if(!listaProyectosAsignados.contains(proyecto)){
            return "accesodenegado";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("proyecto", proyecto);
        return "Proyecto/Proyecto";
    }

    //Función para agregar proyectos a la base de datos
    @PostMapping("/AgregarProyecto")
    public ResponseEntity<String> AgregarProyecto(Proyecto proyecto, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            proyectoService.agregar(proyecto);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario=userService.encontrarUsername(authentication.getName());
            usuario.añadirProyecto(proyecto);
            String mensaje = "Se ha agregado un proyecto.";
            bitacoraService.registrarAccion("Agregar proyecto");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el proyecto.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para eliminar proyectos de la base de datos
    @PostMapping("/EliminarProyecto/{idProyecto}")
    public ResponseEntity<String> EliminarProyecto(Proyecto proyecto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario=userService.encontrarUsername(authentication.getName());
            usuario.eliminarProyecto(proyecto);
            proyectoService.eliminar(proyecto);
            String mensaje = "Se ha eliminado un proyecto correctamente.";
            bitacoraService.registrarAccion("Eliminar proyecto");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario=userService.encontrarUsername(authentication.getName());
            usuario.añadirProyecto(proyecto);
            String error = "Ha ocurrido un error al eliminar el proyecto";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para obtener un proyecto de la base de datos
    @GetMapping(value="/ObtenerProyecto/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Proyecto> ObtenerProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.encontrar(id);
        if (proyecto != null) {
            return ResponseEntity.ok(proyecto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Función para actualizar un proyecto de la base de datos
    @PostMapping("/ActualizarProyecto")
    public ResponseEntity<String> ActualizarProyecto(Proyecto proyecto, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            proyectoService.actualizar(proyecto);
            String mensaje = "Se ha actualizado el proyecto correctamente.";
            bitacoraService.registrarAccion("Actualizar proyecto");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el proyecto.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
