package com.gl05.bad.controller;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Terreno;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.TerrenoService;
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
public class TerrenoController {
    
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private TerrenoService terrenoService;
    
    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/Proyecto/Proyecto/{idProyecto}")
    public String TerrenosProyecto(Model model, Proyecto proyecto) {
        Proyecto proyectoEncontrado = proyectoService.encontrarProyecto(proyecto.getIdProyecto());
        model.addAttribute("proyecto", proyectoEncontrado);
        return "/Proyecto/TerrenosProyecto";
    }
    
    @GetMapping("/Proyecto/{idProyecto}/Terreno/{idTerreno}")
    public String MostrarTerrenoProyecto(Model model, Terreno terreno) {
        model.addAttribute("pageTitle", "Proyectos");
        Terreno terrenoEncontrado = terrenoService.encontrarTerreno(terreno.getIdTerreno());
        model.addAttribute("terreno", terrenoEncontrado);
        return "/Proyecto/MostrarTerrenoProyecto";
    }
    
    @GetMapping("/terrenos/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<Terreno> GetTerrenos(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return terrenoService.listarTerrenos(input, idProyecto);
    }


    @PostMapping("/AgregarTerreno")
    public ResponseEntity AgregarTerreno(Terreno terreno, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            terrenoService.agregarTerreno(terreno);
            String mensaje = "Se ha agregado un terreno.";
            bitacoraService.registrarAccion("Agregar terreno");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el terreno.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/EliminarTerreno/{idTerreno}")
    public ResponseEntity EliminarTerreno(Terreno terreno) {
        try {
            terrenoService.eliminarTerreno(terreno);
             String mensaje = "Se ha eliminado un terreno correctamente.";
             bitacoraService.registrarAccion("Eliminar terreno");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el terreno";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/ObtenerTerreno/{id}")
    public ResponseEntity<Terreno> ObtenerTerreno(@PathVariable Long id) {
        Terreno terreno = terrenoService.encontrarTerreno(id);
        if (terreno != null) {
            return ResponseEntity.ok(terreno);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ActualizarTerreno")
    public ResponseEntity ActualizarTerreno(Terreno terreno, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            terrenoService.actualizarTerreno(terreno);
            String mensaje = "Se ha actualizado el terreno correctamente.";
            bitacoraService.registrarAccion("Actualizar terreno");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el proyecto.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
