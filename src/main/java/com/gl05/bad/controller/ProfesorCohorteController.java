package com.gl05.bad.controller;

import com.gl05.bad.domain.Asignatura;
import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.Maestria;
import com.gl05.bad.domain.MallaCurricular;
import com.gl05.bad.domain.PlanEstudio;
import com.gl05.bad.domain.ProfesorAsignatura;
import com.gl05.bad.domain.ProfesorCohorte;
import com.gl05.bad.servicio.AsignaturaService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CohorteService;
import com.gl05.bad.servicio.MaestriaService;
import com.gl05.bad.servicio.MallaCurricularService;
import com.gl05.bad.servicio.PlanEstudioService;
import com.gl05.bad.servicio.PostuladoCohorteService;
import com.gl05.bad.servicio.ProfesorAsignaturaService;
import com.gl05.bad.servicio.ProfesorCohorteService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfesorCohorteController {

    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private PostuladoCohorteService postuladoCohorteService;

    @Autowired
    private ProfesorCohorteService profesorCohorteService;

    @Autowired
    private CohorteService cohorteService;

    @Autowired
    private MaestriaService maestriaService;

    @Autowired
    private PlanEstudioService planEstudioService;

    @Autowired
    private MallaCurricularService mallaCurricularService;

    @Autowired
    private AsignaturaService asignaturaService;

    @Autowired
    private ProfesorAsignaturaService profesorAsignaturaService;

    @GetMapping("/PostuladosCohorte/{idCohorte}")
    public String PostuladosCohorte(Model model, RedirectAttributes redirectAttributes, @PathVariable("idCohorte") Long idCohorte) {
        var postulados = postuladoCohorteService.listarAspiranteProfesor(idCohorte);
        Cohorte cohorteObj = new Cohorte(idCohorte);
        Cohorte cohorte = cohorteService.encontrarCohorte(cohorteObj);
        Maestria idMaestria = cohorte.getIdMaestria();
        Maestria maestria = maestriaService.encontrarMaestria(idMaestria);
        short estadoPlanVigente = 1;
        PlanEstudio planEstudio = planEstudioService.encontrarPlanEstudioPorIdMaestria(maestria, estadoPlanVigente);
        MallaCurricular mallaCurricular = mallaCurricularService.obtenerMallaCurricularPlan(planEstudio);
        List<Asignatura> asignaturas = asignaturaService.encontrarAsignaturasPorMalla(mallaCurricular);
        model.addAttribute("asignaturas", asignaturas);
        model.addAttribute("postulados", postulados);
        model.addAttribute("idCohorte", idCohorte);
        model.addAttribute("pageTitle", "Contratar Postulados Cohorte");
        return "ProfesorCohorte/index";
    }

    @PostMapping("/ContratarAspirante")
    public ResponseEntity ContratarAspirante(ProfesorCohorte profesorCohorte,
            RedirectAttributes redirectAttributes,
            @RequestParam("idAsignatura") List<Long> idAsignaturas) {

        try {
            ProfesorCohorte profesor = profesorCohorteService.contratarP(profesorCohorte, idAsignaturas);
            ProfesorAsignatura asignaturaProfesor;

            for (Long idAsignatura : idAsignaturas) {
                asignaturaProfesor = new ProfesorAsignatura();
                Asignatura asignatura = new Asignatura(idAsignatura);
                asignaturaProfesor.setIdAsignatura(asignatura);
                asignaturaProfesor.setIdProfesor(profesor);
                profesorAsignaturaService.agregarA(asignaturaProfesor);
            }
            bitacoraService.registrarAccion("Contratar aspirante a profesor");
            String mensaje = "Se ha Contratado con exito.";
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrio un error al intentar contratar.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/ProfesorCohorte/{idCohorte}")
    public String ProfesorCohorte(Model model, RedirectAttributes redirectAttributes, @PathVariable("idCohorte") Long idCohorte) {
        var profesorCohorte = profesorCohorteService.listaProfesoresC(idCohorte);
        model.addAttribute("profesorCohorte", profesorCohorte);
        model.addAttribute("pageTitle", "Profesores Contratados");
        return "ProfesorCohorte/gestionarProfesores";
    }

    @PostMapping("/EliminarProfesor/{idProfesor}")
    public ResponseEntity EliminarProfesor(ProfesorCohorte profesorCohorte) {
        try {
            profesorCohorteService.eliminarPC(profesorCohorte);
            bitacoraService.registrarAccion("Eliminar contratación de profesor");
            String mensaje = "Se ha eliminado al profesor correctamente.";
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el profesor";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/ActualizarProfesorCohorte")
    public ResponseEntity ActualizarProfesor(ProfesorCohorte profesor, RedirectAttributes redirectAttributes) {
        try {
            System.out.println(profesor);
            profesorCohorteService.actualizarProfesor(profesor);
            String mensaje = "Se ha actualizado el contrato del profesor correctamente.";
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el contrato del profesor.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/ObtenerProfesor/{id}")
    public ResponseEntity<ProfesorCohorte> obtenerProfesorCohorte(@PathVariable Long id) {
        ProfesorCohorte profesor = profesorCohorteService.encontrarProfesor(id);
        System.out.println(profesor);
        if (profesor != null) {
            return ResponseEntity.ok(profesor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ObtenerProfesorAsignatura/{idProfesor}")
    public ResponseEntity obtenerAsignaturaProfesor(@PathVariable("idProfesor") Long idProfesor) {
        Collection<ProfesorAsignatura> profesorAsignatura = profesorAsignaturaService.listarAsignaturas(idProfesor);
       if (!profesorAsignatura.isEmpty()) {
            return ResponseEntity.ok(profesorAsignatura);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
