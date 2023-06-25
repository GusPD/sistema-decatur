package com.gl05.bad.controller;

import com.gl05.bad.domain.Maestria;
import com.gl05.bad.domain.PlanEstudio;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CoordinadorAcademicoService;
import com.gl05.bad.servicio.EscuelaPostgradoService;
import com.gl05.bad.servicio.MaestriaService;
import com.gl05.bad.servicio.PlanEstudioService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MaestriaController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private MaestriaService maestriaService;
    
    @Autowired
    private EscuelaPostgradoService escuelaPostgradoService;
    
    @Autowired
    private PlanEstudioService planEstudioService;
    
    @Autowired
    private CoordinadorAcademicoService coordinadorAcademicoService;
    
    @GetMapping("/GestionarMaestria")
    public String listarMaestrias(Model model) {
        model.addAttribute("pageTitle", "Gestionar Maestrias");
        var escuelas = escuelaPostgradoService.listarEscuelaPostgrado();
        model.addAttribute("escuelas", escuelas);
        return "maestria/index";
    }
    
    @GetMapping("/maestria/data")
    @ResponseBody
    public DataTablesOutput<Maestria> getMaestrias(@Valid DataTablesInput input) {
      return maestriaService.listarMaestrias(input);
    }
    
    @PostMapping("/AgregarMaestria")
    public ResponseEntity<String> AgregarMaestria(
            @RequestParam("nombreMaestria") String nombreMaestria,
            @RequestParam("idPostgrado") String escuelaPostgrado) {     
        try {
            maestriaService.proAgregar(nombreMaestria, escuelaPostgrado);
            String mensaje = "Se ha Agregado una Maestria.";
            bitacoraService.registrarAccion("Agregar maestría");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ya existe una Maestria con ese nombre.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/ActualizarMaestria")
    public ResponseEntity ActualizarMaestria(Maestria maestria, RedirectAttributes redirectAttributes) {
        try {
            Maestria maestriaExistente = maestriaService.encontrarMaestria(maestria);
            maestriaExistente.setNombreMaestria(maestria.getNombreMaestria());
            maestriaExistente.setIdPostgrado(maestria.getIdPostgrado());
            maestriaService.actualizar(maestriaExistente);
            String mensaje = "Se ha actualizado la maestria correctamente.";
            bitacoraService.registrarAccion("Actualizar maestría");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar la maestria.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/EliminarMaestria/{idMaestria}")
    public ResponseEntity EliminarMaestria(Maestria maestria) {
        try {
            maestriaService.eliminar(maestria);
            String mensaje = "Se ha eliminado la Maestria correctamente.";
            bitacoraService.registrarAccion("Eliminar maestría");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar la maestria.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/DetalleMaestria/{idMaestria}")
    public String DetalleMaestria(Model model, Maestria maestria, RedirectAttributes redirectAttributes) {
        model.addAttribute("pageTitle", "Detalle Maestria");
        short estadoPlanVigente = 1;
        Maestria maestriaEnviar = maestriaService.encontrarMaestria(maestria);
        PlanEstudio planEstudioVigente = planEstudioService.encontrarPlanEstudioPorIdMaestria(maestriaEnviar, estadoPlanVigente);
        var coordinadores = coordinadorAcademicoService.listarCoordinadores();
        model.addAttribute("maestria", maestriaEnviar);
        model.addAttribute("planEstudioVigente", planEstudioVigente);
        model.addAttribute("coordinadoresAcademicos", coordinadores);
        return "maestria/detalleMaestria";
    }
    
    @GetMapping("/ObtenerMaestria/{id}")
    public ResponseEntity<Maestria> obtenerMaestria(@PathVariable Long id) {
        Maestria maestriaPrueba = new Maestria(id);
        Maestria maestria = maestriaService.encontrarMaestria(maestriaPrueba);
        if (maestria != null) {
            return ResponseEntity.ok(maestria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/AgregarPerfilMaestriaCoordinador")
    @ResponseBody
    public ResponseEntity<Map<String, String>> agregarPerfilCoordinador(Maestria maestria) {
        try {
            Maestria maestriaExistente = maestriaService.encontrarMaestria(maestria);
            maestriaExistente.setPerfilCoor(maestria.getPerfilCoor());
            maestriaService.actualizar(maestriaExistente);

            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/DetalleMaestria/" + maestria.getIdMaestria());
            response.put("mensaje", "Se ha agregado el perfil del coordinador a la maestría.");
            bitacoraService.registrarAccion("Agregar perfil del coordinador a la maestría");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Manejo del error
            Map<String, String> response = new HashMap<>();
            response.put("error", "Ha ocurrido un error al agregar el perfil del coordinador.");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/AgregarPerfilMaestriaAspirante")
    @ResponseBody
    public ResponseEntity<Map<String, String>> agregarPerfilAspirante(Maestria maestria) {
        try {
            Maestria maestriaExistente = maestriaService.encontrarMaestria(maestria);
            maestriaExistente.setPerfilAsp(maestria.getPerfilAsp());
            maestriaService.actualizar(maestriaExistente);

            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/DetalleMaestria/" + maestria.getIdMaestria());
            response.put("mensaje", "Se ha agregado el perfil del aspirante a la maestría.");
            bitacoraService.registrarAccion("Agregar perfil del aspirante a la maestría");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Manejo del error
            Map<String, String> response = new HashMap<>();
            response.put("error", "Ha ocurrido un error al agregar el perfil del aspirante.");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PostMapping("/CoordinadorMaestria")
    @ResponseBody
    public ResponseEntity<Map<String, String>> agregarCoordinadorMaestria(Maestria maestria) {
        try {
            Maestria maestriaExistente = maestriaService.encontrarMaestria(maestria);
            maestriaExistente.setIdCoorAca(maestria.getIdCoorAca());
            maestriaService.actualizar(maestriaExistente);

            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/DetalleMaestria/" + maestria.getIdMaestria());
            response.put("mensaje", "Se ha actualizado el coordinador Académico.");
            bitacoraService.registrarAccion("Actualizar coordinador académico de la maestría");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Manejo del error
            Map<String, String> response = new HashMap<>();
            response.put("error", "Ha ocurrido un error al agregar el perfil del aspirante.");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/InscribirMaestria")
    public String listarMaestriasInscripcion(Model model) {
        model.addAttribute("pageTitle", "Inscripción");
        return "maestria/inscribirMaestria";
    }
}
