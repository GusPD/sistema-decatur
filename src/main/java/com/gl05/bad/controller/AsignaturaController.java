package com.gl05.bad.controller;

import com.gl05.bad.domain.Asignatura;
import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.EstudianteAsignatura;
import com.gl05.bad.domain.Maestria;
import com.gl05.bad.domain.MallaCurricular;
import com.gl05.bad.domain.PlanEstudio;
import com.gl05.bad.servicio.AreaConocimientoService;
import com.gl05.bad.servicio.AsignaturaService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CohorteService;
import com.gl05.bad.servicio.EstudianteAsignaturaService;
import com.gl05.bad.servicio.MaestriaService;
import com.gl05.bad.servicio.MallaCurricularService;
import com.gl05.bad.servicio.PlanEstudioService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AsignaturaController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private AsignaturaService asignaturaService;

    @Autowired
    private AreaConocimientoService areaConocimientoService;
    
    @Autowired
    private MaestriaService maestriaService;
    
    @Autowired
    private PlanEstudioService planEstudioService;
    
    @Autowired
    private MallaCurricularService mallaCurricularService;
    
    @Autowired
    private EstudianteAsignaturaService estudianteAsignaturaService;
    
    @Autowired
    private CohorteService cohorteService;
    

    @GetMapping("/DetallePlanEstudio/{idPlanEstudio}")
    public String Asignatura(Model model, RedirectAttributes redirectAttributes, @PathVariable("idPlanEstudio") Long idPlanEstudio) {
        model.addAttribute("pageTitle", "Detalle Plan Estudio");
        var areaC = areaConocimientoService.listarAreaConocimientos();
        Long idMallaC = asignaturaService.encontrarMalla(idPlanEstudio);
        model.addAttribute("areaC", areaC);
        model.addAttribute("idMallaC", idMallaC);
        model.addAttribute("idPlanEstudio", idPlanEstudio);
        bitacoraService.registrarAccion("Ver detalle de plan de estudio");
        return "Asignatura/index";
    }

    @GetMapping("/Asignatura/data")
    @ResponseBody
    public DataTablesOutput<Asignatura> getAsignatura(@Valid DataTablesInput input,
            @RequestParam("idMallaCurricular") Long idMallaCurricular) {
        return asignaturaService.listarAsignaturaFiltrado(input, idMallaCurricular);
    }

    @PostMapping("/AgregarAsignatura")
    public ResponseEntity<String> AgregarAsignatura(
            @RequestParam("codigoAsignatura") String codigoAsignatura,
            @RequestParam("nombreAsignatura") String nombreAsignatura,
            @RequestParam("uv") Integer uv,
            @RequestParam("numeroCorrelativo") Integer numeroCorrelativo,
            @RequestParam("ciclo") Integer ciclo,
            @RequestParam("idAreaC") long idAreaC,
            @RequestParam("idMalla") long idMalla,
            @RequestParam("duracion") Integer duracion,
            @RequestParam("horasT") Integer horasT,
            @RequestParam("horasP") Integer horasP,
            @RequestParam("horaCiclo") Integer horaCiclo,
            @RequestParam("introduccion") String introduccion,
            @RequestParam("descipcionPrograma") String descipcionPrograma,
            @RequestParam("objetivo") String objetivo,
            @RequestParam("metodologia") String metodologia,
            @RequestParam("sistemaEvaluacion") String sistemaEvaluacion,
            @RequestParam("bibliografia") String bibliografia,
            @RequestParam MultiValueMap<String, String> formData
    ) {
        try {
            String[] actividad = formData.get("actividad[]").toArray(new String[0]);
            int[] ponderacion = formData.get("ponderacion[]").stream().mapToInt(Integer::parseInt).toArray();
            String ponderacionString = Arrays.stream(ponderacion)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(","));
            String actividadString = String.join(",", actividad);

            asignaturaService.AgregarAsig(codigoAsignatura, nombreAsignatura, uv, numeroCorrelativo, ciclo, idAreaC, idMalla, duracion, horasT, horasP, horaCiclo, introduccion, descipcionPrograma, objetivo, metodologia, sistemaEvaluacion, bibliografia, actividadString, ponderacionString);
            String mensaje = "Se ha Agregado una Asignatura.";
            bitacoraService.registrarAccion("Agregar asignatura");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ya existe una Asignatura con ese nombre.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/EliminarAsignatura/{idAsignatura}")
    public ResponseEntity EliminarPlanEstudio(Asignatura asignatura) {
        try {
            asignaturaService.eliminarA(asignatura);
            String mensaje = "Se ha eliminado el plan de estudio correctamente.";
            bitacoraService.registrarAccion("Eliminar plan de estudio");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el plan de estudio.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/ObtenerMateriasMaestria/{idMaestria}")
    public ResponseEntity<?> obtenerMateriasMaestria(@PathVariable Long idMaestria) {
        short estadoPlanVigente = 1;
        try {
            Maestria maestriaId = new Maestria(idMaestria);
            Maestria maestria = maestriaService.encontrarMaestria(maestriaId);
            PlanEstudio planEstudio = planEstudioService.encontrarPlanEstudioPorIdMaestria(maestria, estadoPlanVigente);
            MallaCurricular mallaCurricular = mallaCurricularService.obtenerMallaCurricularPlan(planEstudio);
            List<Asignatura> asignaturas = asignaturaService.encontrarAsignaturasPorMalla(mallaCurricular );
            return ResponseEntity.ok().body(asignaturas);
        } catch (Exception e) {
            String mensajeError = "Error al obtener la malla curricular de la maestría.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }
    
    @GetMapping("/ObtenerMateriasMaestriaCohorte/{idMaestria}/{idCohorte}")
    public ResponseEntity<?> obtenerMateriasMaestriaCohorte(@PathVariable Long idMaestria, @PathVariable Long idCohorte) {
        short estadoPlanVigente = 1;
        try {
            
            Maestria maestriaId = new Maestria(idMaestria);
            Maestria maestria = maestriaService.encontrarMaestria(maestriaId);
            Cohorte cohorte = cohorteService.encontrarCohorte(new Cohorte(idCohorte));
            PlanEstudio planEstudio = planEstudioService.encontrarPlanEstudioPorIdMaestria(maestria, estadoPlanVigente);
            MallaCurricular mallaCurricular = mallaCurricularService.obtenerMallaCurricularPlan(planEstudio);
            List<Asignatura> asignaturas = asignaturaService.encontrarAsignaturasPorMalla(mallaCurricular );
            List<Asignatura> asinaturasDisponibleI = new ArrayList<>();
            for(Asignatura asignatura: asignaturas){
                if (!estudianteAsignaturaService.existeEstudianteAsignatura(cohorte, asignatura)) {
                    asinaturasDisponibleI.add(asignatura);
                }
            }
            return ResponseEntity.ok().body(asinaturasDisponibleI);
        } catch (Exception e) {
            String mensajeError = "Error al obtener la malla curricular de la maestría.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }

    @PostMapping("/ActualizarAsignatura")
    public ResponseEntity ActualizarAsignatura(Asignatura asignatura, RedirectAttributes redirectAttributes) {
        System.out.println(asignatura);
        try {
            
            Asignatura asignaturaExsistente = asignaturaService.encontrarA(asignatura);
            asignaturaExsistente.setCiclo(asignatura.getCiclo());
            asignaturaExsistente.setIdAreaConocimiento(asignatura.getIdAreaConocimiento());
            asignaturaExsistente.setNombreMateria(asignatura.getNombreMateria());
            asignaturaExsistente.setNumeroCorrelativo(asignatura.getNumeroCorrelativo());
            asignaturaExsistente.setUnidadesValorativas(asignatura.getUnidadesValorativas());
            asignaturaExsistente.setCodAsignatura(asignatura.getCodAsignatura());
            asignaturaService.actualizarA(asignaturaExsistente);
            String mensaje = "Se ha actualizado la Actividad correctamente.";
            bitacoraService.registrarAccion("Actualizar asignatura");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "No se puede Actualizar la actividad";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
        @GetMapping("/ObtenerAsignatura/{id}")
    public ResponseEntity<Asignatura> obtenerAsignatura(@PathVariable Long id) {
        Asignatura actividad = asignaturaService.encontrarAsig(id);
        if (actividad != null) {
            return ResponseEntity.ok(actividad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/AsignaturasInscripcionCohorte/{id}")
    public String GestionarAsignaturasInscripcionCohorte(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("pageTitle", "Gestionar Asignaturas Inscritas");
        Cohorte cohorte = cohorteService.encontrarCohorte(new Cohorte(id));
        model.addAttribute("cohorte", cohorte);
        short estadoPlanVigente = 1;
        try {
            Maestria maestria = cohorte.getIdMaestria();
            PlanEstudio planEstudio = planEstudioService.encontrarPlanEstudioPorIdMaestria(maestria, estadoPlanVigente);
            MallaCurricular mallaCurricular = mallaCurricularService.obtenerMallaCurricularPlan(planEstudio);
            List<Asignatura> asignaturas = asignaturaService.encontrarAsignaturasPorMalla(mallaCurricular );
            List<Asignatura> asinaturasInscritas = new ArrayList<>();
            for(Asignatura asignatura: asignaturas){
                if (estudianteAsignaturaService.existeEstudianteAsignatura(cohorte, asignatura)) {
                    asinaturasInscritas.add(asignatura);
                }
            }
            model.addAttribute("asignaturas", asinaturasInscritas);
            bitacoraService.registrarAccion("Ver malla curricular");
        } catch (Exception e) {
            String mensajeError = "Error al obtener la malla curricular de la maestría.";
            model.addAttribute("error", mensajeError);
        }
        model.addAttribute("cohorte", cohorte);
        return "/EstudianteAsignatura/GestionarAsignaturaCohorte";
    }
    
    @PostMapping("/EliminarInscripcionAsignatura/{idCohorte}/{idAsignatura}")
    public String eliminarInscripcionAsignatura(@PathVariable Long idCohorte, @PathVariable Long idAsignatura, RedirectAttributes redirectAttributes) {
        try{
            // Realiza las operaciones necesarias para eliminar la inscripción de la asignatura
            List<EstudianteAsignatura> estudiantesAsignatura = estudianteAsignaturaService.encontrarPorIdCohorteIdasignatura(idCohorte, idAsignatura);
            for (EstudianteAsignatura estudiante : estudiantesAsignatura) {
                estudianteAsignaturaService.eliminarEstudianteAsignatura(estudiante);
            }
            redirectAttributes.addFlashAttribute("mensaje", "La inscripción de la asignatura se eliminó exitosamente.");
            bitacoraService.registrarAccion("Eliminar inscripción de la asignatura");
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Se produjo un error al eliminar la inscripción de la asignatura.");
        }

        return "redirect:/AsignaturasInscripcionCohorte/" + idCohorte;
    }

}
