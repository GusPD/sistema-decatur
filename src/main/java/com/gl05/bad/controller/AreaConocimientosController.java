package com.gl05.bad.controller;

import com.gl05.bad.domain.AreaConocimiento;
import com.gl05.bad.servicio.AreaConocimientoService;
import com.gl05.bad.servicio.BitacoraServiceImp;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AreaConocimientosController {
      
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private AreaConocimientoService areaConocimientoService;
    
    @GetMapping("/GestionarAreaConocimiento")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Àrea de Conocimientos");
        var elemento = areaConocimientoService.listarAreaConocimientos();
        model.addAttribute("AreaConocimiento", elemento);
        return "AreaConocimiento/index";
    }
    @GetMapping("/areaConocimiento/data")
    @ResponseBody
    public DataTablesOutput<AreaConocimiento> getAreaConocimiento(@Valid DataTablesInput input) {
      System.out.println(areaConocimientoService.listarAreaConocimientos(input));
        return areaConocimientoService.listarAreaConocimientos(input);
    }
    @PostMapping("/AgregarAreaConocimiento")
    public ResponseEntity AgregarAreaConocimiento(AreaConocimiento area, RedirectAttributes redirectAttributes) {     
        try {
            areaConocimientoService.agregarAC(area);
            String mensaje = "Se ha Agregado una Nueva Àrea de Conocimiento.";
            bitacoraService.registrarAccion("Agregar área de conocimiento");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String mensaje ="Ya existe una Àrea de Conocimiento con ese Nombre.";
            return ResponseEntity.ok(mensaje);
        }
    }
    //ObtenerAreaConocimiento
    
    @PostMapping("/EliminarAreaConocimiento/{idAreaConocimiento}")
    public ResponseEntity EliminarAreaConocimiento(AreaConocimiento area) {
        try {
            areaConocimientoService.eliminarAC(area);
            String mensaje = "Se ha eliminado la Maestria correctamente.";
            bitacoraService.registrarAccion("Eliminar área de conocimiento");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar la maestria.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    @PostMapping("/ActualizarAreaConocimiento")
    public ResponseEntity ActualizarAreaConocimiento(AreaConocimiento area, RedirectAttributes redirectAttributes) {
        try {
            areaConocimientoService.actualizarAC(area);
            String mensaje = "Se ha actualizado la Area de Conocimiento correctamente.";
            bitacoraService.registrarAccion("Actualizar área de conocimiento");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar la Area de Conocimiento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/ObtenerAreaConocimiento/{id}")
    public ResponseEntity<AreaConocimiento> obtenerAreaConocimiento(@PathVariable Long id) {
        AreaConocimiento area = areaConocimientoService.encontrarAC(id);
        if (area != null) {
            return ResponseEntity.ok(area);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
