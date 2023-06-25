package com.gl05.bad.controller;

import com.gl05.bad.domain.PlanEstudio;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.PlanEstudioService;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PlanEstudioController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private PlanEstudioService planEstudioService;
    
    
    @GetMapping("/GestionarPlanEstudio/{idMaestria}")
    public String listarPlanesEstudio(Model model,@PathVariable("idMaestria") Long idMaestria) {
        model.addAttribute("pageTitle", "Gestionar Planes de Estudio");
        model.addAttribute("idMaestria", idMaestria);
        return "PlanEstudio/GestionarPlanEstudio";
    }
    
    @GetMapping("/planEstudio/data")
    @ResponseBody
    public DataTablesOutput<PlanEstudio> getPlanes(@Valid DataTablesInput input,
                                                   @RequestParam("idMaestria") Long idMaestria) {
        return planEstudioService.listarPlanEstudioFiltrado(input, idMaestria);
    }
    
    @PostMapping("/AgregarPlanEstudio")
    public ResponseEntity<String> agregarPlanEstudio(
            @RequestParam("idMaestria") Long idMaestria,
            @RequestParam("codPlan") String codPlan,
            @RequestParam("modalidad") String modalidad,
            @RequestParam("cumMinimo") BigDecimal cumMinimo,
            @RequestParam("notaMinimaAprobacion") BigDecimal notaMinimaAprobacion,
            @RequestParam("totalAsignaturas") short totalAsignaturas,
            @RequestParam("totalUv") short totalUv,
            @RequestParam("duracionCarrera") short duracionCarrera,
            @RequestParam("tituloOrtorgar") String tituloOrtorgar,
            @RequestParam("anio") short anio) {
        try {
            planEstudioService.proAgregar(idMaestria, codPlan, modalidad, cumMinimo, notaMinimaAprobacion, totalAsignaturas, totalUv, duracionCarrera, tituloOrtorgar, anio);
            String mensaje = "Se ha agregado un Plan de Estudio.";
            bitacoraService.registrarAccion("Agregar plan de estudio");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el Plan de Estudio.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/ActualizarPlanEstudio")
    public ResponseEntity ActualizarPlanEstudio(PlanEstudio planEstudio, RedirectAttributes redirectAttributes) {
        try {
            PlanEstudio planEstudioExistente = planEstudioService.encontrarPlanEstudio(planEstudio);
            planEstudioExistente.setCodPlan(planEstudio.getCodPlan());
            planEstudioExistente.setModalidad(planEstudio.getModalidad());
            planEstudioExistente.setCumMinimo(planEstudio.getCumMinimo());
            planEstudioExistente.setNotaMinimaAprobacion(planEstudio.getNotaMinimaAprobacion());
            planEstudioExistente.setTotalAsignaturas(planEstudio.getTotalAsignaturas());
            planEstudioExistente.setTotalUv(planEstudio.getTotalUv());
            planEstudioExistente.setDuracionCarrera(planEstudio.getDuracionCarrera());
            planEstudioExistente.setTituloOrtorgar(planEstudio.getTituloOrtorgar());
            planEstudioExistente.setAnio(planEstudio.getAnio());
            planEstudioService.actualizar(planEstudioExistente);
            String mensaje = "Se ha actualizado el plan de estudio correctamente.";
            bitacoraService.registrarAccion("Actualizar plan de estudio");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            System.out.println(e);
            String error = "Ha ocurrido un error al actualizar el plan de estudio.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/EliminarPlanEstudio/{idPlanEstudio}")
    public ResponseEntity EliminarPlanEstudio(PlanEstudio planEstudio) {
        try {
            planEstudioService.eliminar(planEstudio);
            String mensaje = "Se ha eliminado el plan de estudio correctamente.";
            bitacoraService.registrarAccion("Eliminar plan de estudio");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el plan de estudio.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/ObtenerPlanEstudio/{id}")
    public ResponseEntity<PlanEstudio> obtenerPlanEstudio(@PathVariable Long id) {
        PlanEstudio planEstudioPrueba = new PlanEstudio(id);
        PlanEstudio planEstudio = planEstudioService.encontrarPlanEstudio(planEstudioPrueba);
        if (planEstudio != null) {
            return ResponseEntity.ok(planEstudio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
