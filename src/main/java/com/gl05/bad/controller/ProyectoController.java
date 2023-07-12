package com.gl05.bad.controller;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.ProyectoService;
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
public class ProyectoController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/GestionarProyecto")
    public String gestionarProyecto(Model model) {
        model.addAttribute("pageTitle", "Proyectos");

        var elemento = proyectoService.listaProyectos();
        model.addAttribute("proyectos", elemento);

        return "/Proyecto/GestionarProyecto";
    }
    
    @GetMapping("/proyectos/data")
    @ResponseBody
    public DataTablesOutput<Proyecto> getProyectos(@Valid DataTablesInput input) {
        return proyectoService.listarProyectos(input);
    }

    @PostMapping("/AgregarProyecto")
    public ResponseEntity AgregarUsuario(Proyecto proyecto, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            proyectoService.agregarProyecto(proyecto);
            String mensaje = "Se ha agregado un proyecto.";
            bitacoraService.registrarAccion("Agregar proyecto");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el proyecto.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/EliminarProyecto/{idProyecto}")
    public ResponseEntity EliminarUsuario(Proyecto proyecto) {
        try {
            proyectoService.eliminarProyecto(proyecto);
             String mensaje = "Se ha eliminado un proyecto correctamente.";
             bitacoraService.registrarAccion("Eliminar proyecto");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el proyecto";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/ObtenerProyecto/{id}")
    public ResponseEntity<Proyecto> obtenerProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.encontrarProyecto(id);
        if (proyecto != null) {
            return ResponseEntity.ok(proyecto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ActualizarProyecto")
    public ResponseEntity ActualizarUsuario(Proyecto proyecto, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            proyectoService.actualizarProyecto(proyecto);
            String mensaje = "Se ha actualizado el proyecto correctamente.";
            bitacoraService.registrarAccion("Actualizar proyecto");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el proyecto.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
