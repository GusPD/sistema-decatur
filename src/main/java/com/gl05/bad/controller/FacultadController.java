package com.gl05.bad.controller;

import com.gl05.bad.domain.Facultad;
import com.gl05.bad.servicio.BitacoraServiceImp;
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
public class FacultadController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private FacultadService facultadService;

    @GetMapping("/viewFacultad")
    public String mostrarFacultades(Model model) {
        model.addAttribute("pageTitle", "Facultad");
        var elemento = facultadService.listarFacultad();
        model.addAttribute("facultad", elemento);
        return "Facultad/GestionarFacultad2";
    }
    
    @GetMapping("/facultades/data")
    @ResponseBody
    public DataTablesOutput<Facultad> getFacultades(@Valid DataTablesInput input) {
        return facultadService.listarFacultades(input);
    }

    @PostMapping("/AgregarFacultad")
    public ResponseEntity AgregarFacultad(Facultad facultad, RedirectAttributes redirectAttributes) {
        try {
            facultadService.agregarFacultad(facultad);
            String mensaje = "Se ha agregado una facultad.";
            bitacoraService.registrarAccion("Agregar facultad");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar una facultad.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

//        return "redirect:/viewFacultad";
    }

    @PostMapping("/EliminarFacultad/{idFacultad}")
    public ResponseEntity EliminarFacultad(Facultad facultad, RedirectAttributes redirectAttributes) {
        try {
            facultadService.eliminarFacultad(facultad);
            String mensaje = "Se ha eliminado la facultad correctamente.";
            bitacoraService.registrarAccion("Eliminar facultad");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar la facultad";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewFacultad";
    }

    @PostMapping("/ActualizarFacultad")
    public ResponseEntity ActualizarFacultad(Facultad facultad, RedirectAttributes redirectAttributes) {
        try {
            facultadService.actualizarFacultad(facultad);
            String mensaje = "Se ha actualizado la facultad correctamente.";
            bitacoraService.registrarAccion("Actualizar facultad");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar la facultad.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewFacultad";
    }

    @GetMapping("/ObtenerFacultad/{id}")
    public ResponseEntity<Facultad> obtenerFacultad(@PathVariable Long id) {
        Facultad facultad = facultadService.encontrarFacultad(id);
        System.out.println(facultad);
        if (facultad != null) {
            return ResponseEntity.ok(facultad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
