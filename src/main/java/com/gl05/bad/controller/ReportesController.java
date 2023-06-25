package com.gl05.bad.controller;

import com.gl05.bad.domain.VistaEstudiantesProfesores;
import com.gl05.bad.domain.VistaProfesoresPorCohorte;
import com.gl05.bad.servicio.AspiranteProfesorService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CoordinadorAcademicoService;
import com.gl05.bad.servicio.EstudianteService;
import com.gl05.bad.servicio.VistaEstudiantesProfesoresService;
import com.gl05.bad.servicio.VistaProfesoresPorCohorteService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReportesController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private VistaEstudiantesProfesoresService vEstudiantesProfesoresService;
    
    @Autowired
    private VistaProfesoresPorCohorteService vistaProfesoresPorCohorteService;
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private CoordinadorAcademicoService coordinadorService;
    
    @Autowired
    private AspiranteProfesorService aspiranteService;
    
    @Autowired
    private JavaMailSender mailSender;
            
    @GetMapping("/AreasAcademicas")
    public String mostrarAreaConocimiento(Model model) {
        model.addAttribute("pageTitle", "Reporte Estadístico");
        return "/Reportes/areaConocimiento";
    }
    
    @GetMapping("/AreasConocimiento/data")
    @ResponseBody
    public DataTablesOutput<VistaEstudiantesProfesores> getCohortes(@Valid DataTablesInput input) {
        return vEstudiantesProfesoresService.obtenerEstudiantesProfesores(input);
    }
    
        @GetMapping("/ProfesoresContratadosAsignatura")
    public String mostrarProfesoresContratadosAsignatura(Model model) {
        model.addAttribute("pageTitle", "Reporte Contratación");
        return "/Reportes/profesoresAsignatura";
    }
    
    @GetMapping("/ProfesoresContratadosAsignatura/data")
    @ResponseBody
    public DataTablesOutput<VistaProfesoresPorCohorte> getProfesores(@Valid DataTablesInput input) {
        return vistaProfesoresPorCohorteService.obtenerProfesoresContratados(input);
    }
    
    @PostMapping("/enviarNotificacion")
    public String enviarNotificacion(
          @RequestParam("destino") String destino,
          @RequestParam("asunto") String asunto,
          @RequestParam("mensaje") String mensaje,
          RedirectAttributes redirectAttributes,
          HttpServletRequest request, 
          Model model) throws IOException {
      
        try{
          // Lógica para enviar la notificación
          enviarCorreo(destino, asunto, mensaje);
          bitacoraService.registrarAccion("Enviar notificación a "+destino);
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", "No se ha podido notificar a "+destino);
        }
        // Obtener la URL anterior desde el encabezado Referer
        String urlAnterior = request.getHeader("Referer");
        if (urlAnterior != null) {
            // Redirigir a la URL anterior
            redirectAttributes.addFlashAttribute("mensaje", "Se han enviado las notificaciones a "+destino);
            return "redirect:"+urlAnterior; 
        } else {
            // En caso de que no haya URL anterior, redirigir a una URL predeterminada
            redirectAttributes.addFlashAttribute("mensaje", "Se han enviado las notificaciones a "+destino);
            return "redirect:/";
        }
    }

    public void enviarCorreo(String destinatarios, String asunto, String mensaje){
      List<String> correosDestino = new ArrayList<>();      
      
      switch(destinatarios){
        case "Estudiantes":
          correosDestino.addAll(estudianteService.obtenerCorreosEstudiantes());
          break;
        case "Coordinadores Académicos":
          correosDestino.addAll(coordinadorService.obtenerCorreosCordinadores());
          break;
        case "Aspirantes a Profesores":
          correosDestino.addAll(aspiranteService.obtenerCorreosAspiranteProfesor());
          break;
        case "Profesores":
          correosDestino.addAll(aspiranteService.obtenerCorreosProfesor());
          break;
        case "Todos":
          correosDestino.addAll(estudianteService.obtenerCorreosEstudiantes());
          correosDestino.addAll(coordinadorService.obtenerCorreosCordinadores());
          correosDestino.addAll(aspiranteService.obtenerCorreosAspiranteProfesor());
          correosDestino.addAll(aspiranteService.obtenerCorreosProfesor());
          break;
      }
      
      for (String correo : correosDestino) {
          SimpleMailMessage mailMessage = new SimpleMailMessage();
          mailMessage.setTo(correo);
          mailMessage.setSubject(asunto);
          mailMessage.setText(mensaje);
          System.out.println(correo);
          try {
              mailSender.send(mailMessage);
          } catch (Exception e) {
              
          }
      }
    };
}