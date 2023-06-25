package com.gl05.bad.controller;

import com.gl05.bad.domain.Pais;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.PaisService;
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
public class PaisController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private PaisService paisService;
    
    @GetMapping("/pais/data")
    @ResponseBody
    public DataTablesOutput<Pais> getPais(@Valid DataTablesInput input) {
      return paisService.listarPais(input);
    }
            
    @GetMapping("/GestionarPaises")
    public String mostrarPaises(Model model) {
        model.addAttribute("pageTitle", "Gestionar Pais");
        return "/Pais/gestionarPais";
    }
    
    @PostMapping("/AgregarPais")
    public ResponseEntity<String> AgregarPais(
            @RequestParam("nombrePais") String nombrePais,
            @RequestParam("codigoPais") String codigoPais) {     
        try {
            Pais pais = new Pais();
            pais.setCodigoPais(codigoPais);
            pais.setNombrePais(nombrePais);
            paisService.agregarP(pais);
            String mensaje = "Se ha agregado un país correctamente.";
            bitacoraService.registrarAccion("Agregar país");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ya existe un país con ese nombre.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/ActualizarPais")
    public ResponseEntity ActualizarPais(Pais pais, RedirectAttributes redirectAttributes) {
        try {
            Pais paisExistente = paisService.encontrarP(pais);
            paisExistente.setCodigoPais(pais.getCodigoPais());
            paisExistente.setNombrePais(pais.getNombrePais());
            paisService.actualizarP(paisExistente);
            String mensaje = "Se ha actualizado el pais correctamente.";
            bitacoraService.registrarAccion("Actualizar país");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar lel pais.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/EliminarPais/{idPais}")
    public ResponseEntity EliminarAspiranteProfesor(Pais pais) {
        try {
            paisService.eliminarP(pais);
            String mensaje = "Se ha eliminado el pais correctamente.";
            bitacoraService.registrarAccion("Eliminar país");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el pais.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/ObtenerPais/{id}")
    public ResponseEntity<Pais> obtenerPais(@PathVariable Long id) {
        Pais paisPrueba = new Pais(id);
        Pais pais = paisService.encontrarP(paisPrueba);
        if (pais != null) {
            return ResponseEntity.ok(pais);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}