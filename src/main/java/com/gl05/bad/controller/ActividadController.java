package com.gl05.bad.controller;

import com.gl05.bad.domain.Actividad;
import com.gl05.bad.domain.ListadoActividadEvaluada;
import com.gl05.bad.servicio.ActividadService;
import com.gl05.bad.servicio.AspiranteProfesorService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CoordinadorAcademicoService;
import com.gl05.bad.servicio.ProgramaAsignaturaService;
import com.gl05.bad.servicio.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ActividadController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private ActividadService actividadService;

    @Autowired
    private ProgramaAsignaturaService programaAsignaturaService;
    
    @Autowired
    private CoordinadorAcademicoService coordinadorAcademicoService;
    
    @Autowired
    private AspiranteProfesorService aspiranteProfesorService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/viewActividad/{idProgramaAsignatura}")
    public String mostrarActividad(Model model, RedirectAttributes redirectAttributes, @PathVariable("idProgramaAsignatura") Long idProgramaAsignatura, Authentication authentication) {
        Long listProgramaAsignaturaId = programaAsignaturaService.encontrarPrograma(idProgramaAsignatura);
        var elemento = actividadService.listaActividades(listProgramaAsignaturaId);
        String username = authentication.getName();
        String usuarioCoordinador = coordinadorAcademicoService.buscarPerfil(username);
        String usuarioAspirante=aspiranteProfesorService.buscarPerfil(username);
         
        model.addAttribute("usuarioCoordinador", usuarioCoordinador);
        model.addAttribute("usuarioAspirante", usuarioAspirante);
        model.addAttribute("actividad", elemento);
        model.addAttribute("idPrograma", idProgramaAsignatura);
        model.addAttribute("pageTitle", "Actividades Asignatura");
        return "/Actividades/index";
    }

    @GetMapping("/ObtenerActividad/{id}")
    public ResponseEntity<Actividad> obtenerActividad(@PathVariable Long id) {
        Actividad actividad = actividadService.encontrarA(id);
        if (actividad != null) {
            return ResponseEntity.ok(actividad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ActualizarActividad")
    public ResponseEntity ActualizarActividad(Actividad actividad, RedirectAttributes redirectAttributes) {
        try {
            Actividad actividadExsistente = actividadService.encontrarActividad(actividad);
            actividadExsistente.setNombreActividad(actividad.getNombreActividad());
            actividadExsistente.setPonderacion(actividad.getPonderacion());
            actividadService.actualizarA(actividadExsistente);
            String mensaje = "Se ha actualizado la Actividad correctamente.";
            bitacoraService.registrarAccion("Agregar actividad evaluada");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "No se puede Actualizar la actividad";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/EliminarActividad/{idActividad}")
    public String EliminarActividad(Actividad actividad, @RequestParam("idPrograma") Long idPrograma, RedirectAttributes redirectAttributes) {
        try {
            actividadService.eliminarA(actividad);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado la actividad correctamente.");
            bitacoraService.registrarAccion("Eliminar actividad evaluada");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar la actividad");
        }
        return "redirect:/viewActividad/" + idPrograma;
    }

    @PostMapping("/AgregarActividad")
    public ResponseEntity AgregarActividad(Actividad actividad, @RequestParam("idListAe") Long idListAe, RedirectAttributes redirectAttributes) {
        try {
            actividadService.agregarA(actividad);      
            String mensaje = "Se ha Agregado una Nueva Actividad.";
            bitacoraService.registrarAccion("Actualizar actividad evaluada");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ya existe una Actividad con ese Nombre.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
