package com.gl05.bad.controller;

import com.gl05.bad.domain.ProgramaAsignatura;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.ProgramaAsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProgramaAsignaturaController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private ProgramaAsignaturaService programaAsignaturaService;

    @GetMapping("/viewPrograma/{idProgramaAsignatura}")
    public String mostrarActividad(Model model, RedirectAttributes redirectAttributes, @PathVariable("idProgramaAsignatura") Long idProgramaAsignatura) {
        ProgramaAsignatura listProgramaAsignaturaId = programaAsignaturaService.encontrarP(idProgramaAsignatura);
        model.addAttribute("programa", listProgramaAsignaturaId);
        model.addAttribute("idPrograma", idProgramaAsignatura);
        model.addAttribute("pageTitle", "Programa Asignatura");
        return "/ProgramaAsignatura/index";
    }

    @GetMapping("/ObtenerPrograma/{id}")
    public ResponseEntity<ProgramaAsignatura> obtenerActividad(@PathVariable Long id) {
        ProgramaAsignatura programa = programaAsignaturaService.encontrarP(id);
        if (programa != null) {
            return ResponseEntity.ok(programa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ActualizarPrograma")
    public ResponseEntity ActualizarPrograma(ProgramaAsignatura programa, RedirectAttributes redirectAttributes) {
        try {
            System.out.println(programa);
            ProgramaAsignatura programaExsistente = programaAsignaturaService.encontrarPrograma(programa);
            
            System.out.println(programaExsistente);
            programaExsistente.setBibliografia(programa.getBibliografia());
            programaExsistente.setDescripcionPrograma(programa.getDescripcionPrograma());
            programaExsistente.setDuracionSemanas(programa.getDuracionSemanas());
            programaExsistente.setHorasCiclo(programa.getHorasCiclo());
            programaExsistente.setHorasPracticaSemana(programa.getHorasPracticaSemana());
            programaExsistente.setHorasTeoricoSemana(programa.getHorasTeoricoSemana());
            programaExsistente.setIntroduccion(programa.getIntroduccion());
            programaExsistente.setMetodologiaEnsenanza(programa.getMetodologiaEnsenanza());
            programaExsistente.setObjetivos(programa.getObjetivos());
            programaExsistente.setSistemaEvaluacion(programa.getSistemaEvaluacion());
            programaAsignaturaService.actualizarP(programaExsistente);
            bitacoraService.registrarAccion("Actualizar programa de asignatura");
            String mensaje = "Se ha actualizado el programa correctamente.";
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "No se puede Actualizar el Programa";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

}
