package com.gl05.bad.controller;

import com.gl05.bad.domain.InformacionMantenimiento;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import com.gl05.bad.servicio.BitacoraService;
import com.gl05.bad.servicio.InformacionMantenimientoService;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.VentaService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ConfiguracionProyectoController {
  
    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private InformacionMantenimientoService informacionMantenimientoService;

    //Función que redirige a la vista de la configuración de la parte financiera del proyecto
    @GetMapping("/Financiero/{idProyecto}")
    public String MostrarBackupLocal(@PathVariable Long idProyecto, Model model, Authentication authentication) throws Exception {
        model.addAttribute("pageTitle", "Configuración financiera");
        Proyecto proyecto = proyectoService.encontrar(idProyecto);
        model.addAttribute("proyecto", proyecto);
        return "/ConfiguracionProyecto/Financiero";
    }

    //Función que agrega la información de mantenimiento de las ventas activas del proyecto
    @PostMapping("/AgregarMantenimientoProyecto")
    public ResponseEntity<String> AgregarMantenimientoProyecto(@RequestParam("idProyecto") Long idProyecto, InformacionMantenimiento mantenimiento, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            List<InformacionMantenimiento> listaInformacion = informacionMantenimientoService.encontrarFechaAplicacion(mantenimiento.getFechaAplicacion());
            if(listaInformacion.size()==0 && listaInformacion.isEmpty()){
                Proyecto proyecto = proyectoService.encontrar(idProyecto);
                List<Venta> ventasActivasProyecto = ventaService.listarVentas("Activo",proyecto);
                for (Venta venta : ventasActivasProyecto) {
                    InformacionMantenimiento informacion = new InformacionMantenimiento();
                    informacion.setVenta(venta);
                    informacion.setFechaAplicacion(mantenimiento.getFechaAplicacion());
                    informacion.setCuota(mantenimiento.getCuota());
                    informacion.setMulta(mantenimiento.getMulta());
                    informacionMantenimientoService.agregar(informacion);
                }
                String mensaje = "Se ha agregado el mantenimiento a las ventas activas del proyecto correctamente.";
                bitacoraService.registrarAccion("Agregar información mantenimiento de las ventas del proyecto");
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "Ha ocurrido un error al agregar el mantenimiento de las ventas activas del proyecto, ya se encuentra registrado para esta fecha aplicación.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            String error = "Ha ocurrido un error al agregar el mantenimiento de las ventas activas del proyecto.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
