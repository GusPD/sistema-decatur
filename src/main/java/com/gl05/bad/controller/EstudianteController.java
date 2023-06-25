package com.gl05.bad.controller;

import com.gl05.bad.domain.Cohorte;
import com.gl05.bad.domain.Estudiante;
import com.gl05.bad.domain.EstudianteCohorte;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CohorteService;
import com.gl05.bad.servicio.EstudianteCohorteService;
import com.gl05.bad.servicio.EstudianteService;
import com.gl05.bad.servicio.PaisService;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EstudianteController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private PaisService paisService;
    
    @Autowired
    private CohorteService cohorteService;
    
    @Autowired
    private EstudianteCohorteService estudianteCohorteService;

    //Obtener los roles y mostrarlos en tablas
    @GetMapping("/viewEstudiantes")
    public String mostrarEstudiantes(Model model) {
        model.addAttribute("pageTitle", "Estudiantes");

        var elemento = estudianteService.listaEstudiantes();
        model.addAttribute("estudiantes", elemento);

        var elementoPais = paisService.listarPaises();
        model.addAttribute("pais", elementoPais);

        List<String> sexos = listarSexos();
        List<String> generos = listarGeneros();
        List<String> nacionalidades = listarNacionalidades();
        
        model.addAttribute("sexos", sexos);
        model.addAttribute("generos", generos);
        model.addAttribute("nacionalidades", nacionalidades);

        return "/Estudiante/GestionarEstudiante";
    }

    @GetMapping("/estudiantes/data")
    @ResponseBody
    public DataTablesOutput<Estudiante> getEstudiantes(@Valid DataTablesInput input) {
        return estudianteService.listaEstudiantes(input);
    }
    
    @PostMapping("/AgregarEstudiante")
    public ResponseEntity AgregarEstudiante(Estudiante estudiante, RedirectAttributes redirectAttributes) {
         
        try {
            estudianteService.agregarEstudiante(estudiante);
            String mensaje = "Se ha agregado un estudiante.";
            bitacoraService.registrarAccion("Agregar estudiante");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar al estudiante.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

//        return "redirect:/viewEstudiantes";
    }
    
    @PostMapping("/EliminarEstudiante/{idEstudiante}")
    public ResponseEntity EliminarEstudiante(Estudiante estudiante, RedirectAttributes redirectAttributes) {
        try {
            estudianteService.eliminarEstudiante(estudiante);
            String mensaje = "Se ha eliminado al estudiante correctamente.";
            bitacoraService.registrarAccion("Eliminar estudiante");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar al estudiante";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewEstudiantes";
    }

    @GetMapping("/ObtenerEstudiante/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiante(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.encontrarEstudiante(id);
        if (estudiante != null) {
            return ResponseEntity.ok(estudiante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/ActualizarEstudiante")
    public ResponseEntity ActualizarEstudiante(Estudiante estudiante, RedirectAttributes redirectAttributes) {
        try {
            estudianteService.actualizarEstudiante(estudiante);
            String mensaje = "Se ha actualizado al estudiante correctamente.";
            bitacoraService.registrarAccion("Actualizar estudiante");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar al estudiante.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
//        return "redirect:/viewRoles";
    }
    
    public List<String> listarGeneros() {
        List<String> generos = Arrays.asList("Masculino", "Femenino", "LGBTIQ+", "Prefiero no decirlo");
        return generos;
    }

    public List<String> listarSexos() {
        List<String> sexos = Arrays.asList("Hombre", "Mujer");
        return sexos;
    }

    public List<String> listarNacionalidades() {
        List<String> estados = Arrays.asList("Nacimiento", "Naturalización", "Residencia");
        return estados;
    }
    
    @GetMapping("/InscripcionEstudiantes/{idCohorte}")
    public String inscripcionEstudiantes(@PathVariable Long idCohorte, Model model) {
        model.addAttribute("pageTitle", "Inscripción Estudiantes");

        List<Estudiante> estudiantesSinCohorte = estudianteService.obtenerEstudiantesSinCohorte(idCohorte);
        Cohorte cohorte = cohorteService.encontrarCohorte(new Cohorte(idCohorte));
        model.addAttribute("estudiantes", estudiantesSinCohorte);
        model.addAttribute("cohorte", cohorte);
        return "/Estudiante/InscripcionEstudiante";
    }
    
    @PostMapping("/guardarInscripcion")
    public String inscripcionEstudiantes(@RequestParam("idCohorte") Long idCohorte, @RequestParam("estudiantes") List<Long> estudiantesIds,
        RedirectAttributes redirectAttributes) {
        try{
        Cohorte cohorte = cohorteService.encontrarCohorte(new Cohorte(idCohorte)); // Obtener el objeto Cohorte correspondiente al ID

        for (Long estudianteId : estudiantesIds) {
            Estudiante estudiante = estudianteService.encontrarEstudiante(estudianteId); // Obtener el objeto Estudiante correspondiente al ID

            EstudianteCohorte estudianteCohorte = new EstudianteCohorte();
            estudianteCohorte.setIdCohorte(cohorte);
            estudianteCohorte.setIdEstudiante(estudiante);

            estudianteCohorteService.guardarEstudianteCohorte(estudianteCohorte); // Guardar el objeto EstudianteCohorte en la base de datos
        }

        redirectAttributes.addFlashAttribute("mensaje", "Estudiantes inscritos exitosamente.");
        bitacoraService.registrarAccion("Inscribir estudiante en cohorte");
        
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error al inscribir a los estudiantes.");
        }
    

        return "redirect:/InscribirMaestria";
    }
    
    @GetMapping("/GestionarEstudiantesCohorte/{idCohorte}")
    public String GestionarEstudianteCohorte(@PathVariable Long idCohorte, Model model) {
        model.addAttribute("pageTitle", "Gestión de Estudiantes");
        Cohorte cohorteAux = new Cohorte(idCohorte);
        Cohorte cohorte = cohorteService.encontrarCohorte(cohorteAux);
        model.addAttribute("cohorte", cohorte);
        return "/Estudiante/GestionarEstudianteCohorte";
    }
    
    
    @GetMapping("/estudianteCohorte/list")
    @ResponseBody
    public DataTablesOutput<EstudianteCohorte> getEstudianteCohorteData(DataTablesInput input, @RequestParam("idCohorte") Long idCohorte) {
        Cohorte cohorte = cohorteService.encontrarCohorte(new Cohorte(idCohorte)); // Obtener el objeto Cohorte correspondiente al ID
        return estudianteCohorteService.econtrarPorCohorte(input, cohorte);
    }
    
    @PostMapping("/EliminarEstudianteCohorte/{idCohorte}/{idEstudiante}")
    public ResponseEntity EliminarEstudianteCohorte(Cohorte cohorte, Estudiante estudiante) {
        try {
            EstudianteCohorte estudianteEliminar = estudianteCohorteService.encontrarEstudianteCohorte(cohorte, estudiante);
            estudianteCohorteService.eliminar(estudianteEliminar);
            String mensaje = "Se ha eliminado el estudiante de la cohorte correctamente.";
            bitacoraService.registrarAccion("Eliminar estudiante de la cohorte");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el estudiante de la cohorte.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/DetalleEstudiante/{id}")
    public String detalleEstudiante(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Detalle Estudiante");
        Estudiante estudiante = estudianteService.encontrarEstudiante(id);
        model.addAttribute("estudiante", estudiante);
        bitacoraService.registrarAccion("Ver información del estudiante");
        return "/Estudiante/DetalleEstudiante";
    }

}
