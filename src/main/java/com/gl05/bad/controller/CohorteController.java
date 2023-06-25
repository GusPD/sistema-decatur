package com.gl05.bad.controller;

import com.gl05.bad.domain.Asignatura;
import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.EstudianteAsignatura;
import com.gl05.bad.domain.EstudianteCohorte;
import com.gl05.bad.domain.Maestria;
import com.gl05.bad.domain.VistaEstudiantesPorCohorte;
import com.gl05.bad.servicio.AsignaturaService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CohorteService;
import com.gl05.bad.servicio.EstudianteAsignaturaService;
import com.gl05.bad.servicio.EstudianteCohorteService;
import com.gl05.bad.servicio.MaestriaService;
import com.gl05.bad.servicio.UserService;
import com.gl05.bad.servicio.VistaEstudiantesPorCohorteService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CohorteController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private MaestriaService maestriaService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CohorteService cohorteService;
    
    @Autowired
    private AsignaturaService asignaturaService;
    
    @Autowired
    private EstudianteCohorteService estudianteCohorteService;
    
    @Autowired
    private EstudianteAsignaturaService estudianteAsignaturaService;
    
    @Autowired
    private VistaEstudiantesPorCohorteService vEstudiantesPorCohorteService;
    
    
     @GetMapping("/GestionarCohorte/{idMaestria}")
    public String listarCohortes(Model model,@PathVariable("idMaestria") Long idMaestria) {
        model.addAttribute("pageTitle", "Cohortes");
        model.addAttribute("idMaestria", idMaestria);
        return "Cohorte/GestionarCohorte";
    }
    
    @GetMapping("/cohorte/data")
    @ResponseBody
    public DataTablesOutput<Cohorte> getCohortes(@Valid DataTablesInput input, @RequestParam("idMaestria") Long idMaestria) {
        return cohorteService.listarCohorteFiltrado(input, idMaestria);
    }
    
    @PostMapping("/AgregarCohorte")
    public ResponseEntity<String> agregarCohorte(
            @RequestParam("idMaestria") Long idMaestria,
            @RequestParam("nombreCohorte") String nombreCohorte,
            @RequestParam("fechaApertura") String fechaAperturaString,
            @RequestParam("estadoCohorte") short estadoCohorte) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
            Date fechaApertura = dateFormat.parse(fechaAperturaString);
            cohorteService.proAgregar(idMaestria, nombreCohorte, fechaApertura, estadoCohorte);
            System.out.println(fechaApertura);
            String mensaje = "Se ha agregado una cohorte a la maestria.";
            bitacoraService.registrarAccion("Agregar cohorte");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar la cohorte.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/ActualizarCohorte")
    public ResponseEntity ActualizarCohorte(Cohorte cohorte, RedirectAttributes redirectAttributes) {
        try {
            Cohorte cohorteExistente = cohorteService.encontrarCohorte(cohorte);
            cohorteExistente.setNombreCohorte(cohorte.getNombreCohorte());
            cohorteExistente.setFechaApertura(cohorte.getFechaApertura());
            cohorteExistente.setEstadoCohorte(cohorte.getEstadoCohorte());
            
            cohorteService.actualizarCohorte(cohorte);
            String mensaje = "Se ha actualizado la cohorte correctamente.";
            bitacoraService.registrarAccion("Actualizar cohorte");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            System.out.println(e);
            String error = "Ha ocurrido un error al actualizar la cohorte.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
     @PostMapping("/EliminarCohorte/{idCohorte}")
    public ResponseEntity EliminarCohorte(Cohorte cohorte) {
        try {
            cohorteService.eliminarCohorte(cohorte);
            String mensaje = "Se ha eliminado la cohorte correctamente.";
            bitacoraService.registrarAccion("Eliminar cohorte");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar la cohorte";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/ObtenerCoherte/{id}")
    public ResponseEntity<Cohorte> obtenerCohorte(@PathVariable Long id) {
        Cohorte cohorteID = new Cohorte(id);
        Cohorte cohorte = cohorteService.encontrarCohorte(cohorteID);
        if (cohorte != null) {
            return ResponseEntity.ok(cohorte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/ObtenerCohortesActivas/{idMaestria}")
    public ResponseEntity<?> obtenerCohortesActivasMaestria(@PathVariable Long idMaestria) {
        try {
            short estadoCohorteActivo = 1;
            Maestria maestriaId = new Maestria(idMaestria);
            Maestria maestria = maestriaService.encontrarMaestria(maestriaId);
            List<Cohorte> cohortes = cohorteService.encontrarCohortesActivasPorIdMaestria(maestria, estadoCohorteActivo);

            return ResponseEntity.ok().body(cohortes);
        } catch (Exception e) {
            String mensajeError = "Error al obtener las cohortes activas";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }
    
    @GetMapping("/ObtenerCohortes/{idMaestria}")
    public ResponseEntity<?> obtenerCohortesMaestria(@PathVariable Long idMaestria) {
        try {
            Maestria maestriaId = new Maestria(idMaestria);
            Maestria maestria = maestriaService.encontrarMaestria(maestriaId);
            List<Cohorte> cohortes = cohorteService.encontrarCohortesPorIdMaestria(maestria );

            return ResponseEntity.ok().body(cohortes);
        } catch (Exception e) {
            String mensajeError = "Error al obtener las cohortes activas";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }
    
    @PostMapping("/InscribirMateria/{idCohorte}")
    public ResponseEntity InscribirMateria(Cohorte cohorte, @RequestParam("materias[]") List<Long> materiasIds,
        RedirectAttributes redirectAttributes) {
        try {
            Cohorte cohorteEncontrada = cohorteService.encontrarCohorte(cohorte);
            List<EstudianteCohorte> estudiantesCohorte = estudianteCohorteService.encontrarEstudianteIdCohorte(cohorteEncontrada);
            // Verificar si la lista de estudiantesCohorte está vacía
            if (estudiantesCohorte.isEmpty()) {
                String error = "La cohorte no tiene estudiantes inscritos";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            for (Long materiaId : materiasIds) {
                Asignatura asignatura = asignaturaService.encontrarAsig(materiaId); // Obtener el objeto Estudiante correspondiente al ID
                for (EstudianteCohorte estudianteCohorte:estudiantesCohorte) {
                    EstudianteAsignatura estudianteAsignatura = new EstudianteAsignatura();
                    estudianteAsignatura.setIdAsignatura(asignatura);
                    estudianteAsignatura.setIdEstudCo(estudianteCohorte);
                    estudianteAsignaturaService.guardarEstudianteAsignatura(estudianteAsignatura);
                }
            }     
            String mensaje = "Se ha inscrito la cohorte a las asignaturas correctamente.";
            bitacoraService.registrarAccion("Inscribir cohorte en asignaturas");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Error al inscribir la cohorte a las asignaturas";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/DetalleCohorte/{id}")
    public String detalleCohorte(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Detalle Cohorte");
        Cohorte cohorte = cohorteService.encontrarCohorte(new Cohorte(id));
        model.addAttribute("cohorte", cohorte);
        return "/Cohorte/DetalleCohorte";
    }
    
    
    //Reporte estudiantes inscritos
    @GetMapping("/EstudiantesInscritos")
    public String estudiantesInscritos(Model model) {
        model.addAttribute("pageTitle", "Estudiantes Inscritos");
        bitacoraService.registrarAccion("Ver estadísticas sobre el número de estudiantes inscritos por cohorte en cada maestría");
        return "/Reportes/estudiantesInscritos";
    }
    
    @GetMapping("/EstudiantesInscritos/data")
    @ResponseBody
    public DataTablesOutput<VistaEstudiantesPorCohorte> getCohortes(@Valid DataTablesInput input) {
        return vEstudiantesPorCohorteService.obtenerEstudiantesPorCohorte(input);
    }
}
