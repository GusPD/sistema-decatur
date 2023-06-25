package com.gl05.bad.controller;

import com.gl05.bad.domain.EscuelaPostgrado;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.EscuelaPostgradoService;
import com.gl05.bad.servicio.FacultadService;
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
public class EscuelaPostgradoController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private EscuelaPostgradoService escuelaPostgradoService;
    
    @Autowired
    private FacultadService facultadService;
    
    @GetMapping("/viewEscuelaPosgrado")
    public String mostrarEscuelas(Model model) {
        model.addAttribute("pageTitle", "Escuelas de Posgrado");
        
        var elemento = escuelaPostgradoService.listarEscuelaPostgrado();
        model.addAttribute("escuela", elemento);
        
        var elementoFacultad = facultadService.listarFacultad();
        model.addAttribute("facultad", elementoFacultad);
        
        return "EscuelaPostgrado/GestionarEscuela2";
    }
    
    @GetMapping("/escuelas/data")
    @ResponseBody
    public DataTablesOutput<EscuelaPostgrado> getEscuelas(@Valid DataTablesInput input) {
        return escuelaPostgradoService.listarEscuelasDePosgrados(input);
    }
    
    @PostMapping("/AgregarEscuelaPosgrado")
    public ResponseEntity AgregarEscuela(EscuelaPostgrado escuela, RedirectAttributes redirectAttributes) {
        try {
            escuelaPostgradoService.agregarEscuela(escuela);
            String mensaje = "Se ha agregado una escuela.";
            bitacoraService.registrarAccion("Agregar escuela de postgrados");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar la escuela.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error); 
        }

//        return "redirect:/viewEscuelaPosgrado";
    }
    
    @PostMapping("/EliminarEscuelaPosgrado/{idPostgrado}")
    public ResponseEntity EliminarEscuela(EscuelaPostgrado escuela, RedirectAttributes redirectAttributes) {
        try {
            escuelaPostgradoService.eliminarEscuela(escuela);
            String mensaje = "Se ha eliminado la escuela correctamente.";
            bitacoraService.registrarAccion("Eliminar escuela de postgrados");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar la escuela";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewEscuelaPosgrado";
    }
    
    @PostMapping("/ActualizarEscuelaPosgrado")
    public ResponseEntity ActualizarFacultad(EscuelaPostgrado escuela, RedirectAttributes redirectAttributes) {
        try {
            escuelaPostgradoService.actualizarEscuela(escuela);
            String mensaje = "Se ha actualizado la escuela correctamente.";
            bitacoraService.registrarAccion("Actualizar escuela de postgrados");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
           String error = "Ha ocurrido un error al actualizar la escuela.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewEscuelaPosgrado";
    }
    
    @GetMapping("/ObtenerEscuelaPosgrado/{id}")
    public ResponseEntity<EscuelaPostgrado> obtenerEscuela(@PathVariable Long id) {
        EscuelaPostgrado escuela = escuelaPostgradoService.encontrarEscuela(id);
        if (escuela != null) {
            return ResponseEntity.ok(escuela);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
