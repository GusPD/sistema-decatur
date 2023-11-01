package com.gl05.bad.controller;

import com.gl05.bad.domain.CuentaBancaria;
import com.gl05.bad.domain.Pago;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Venta;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CuentaBancariaService;
import com.gl05.bad.servicio.PagoService;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.VentaService;
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
public class PagoController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private PagoService pagoService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    @Autowired
    private CuentaBancariaService cuentaService;
    
    @Autowired
    private VentaService ventaService;
    
    //Función para redigir a la vista de pagos del proyecto
    @GetMapping("/Pagos/{idProyecto}")
    public String mostrarProyecto(Model model, @PathVariable("idProyecto") Long idProyecto) {
        model.addAttribute("pageTitle", "Pagos");
        Proyecto newProyecto = proyectoService.encontrar(idProyecto);
        List<CuentaBancaria> cuentas = cuentaService.encontrarProyecto(newProyecto);
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("proyecto", newProyecto);
        return "/Pago/GestionarPago";
    }
    
    //Función para obtener las lotes activos del proyecto de la base de datos con prima
    @GetMapping("/obtenerVentasPrima")
    public ResponseEntity<List<Venta>> obtenerVentasPrima(@RequestParam Long proyectoId) {
        Proyecto proyecto = proyectoService.encontrar(proyectoId);
        List<Venta> ventas = ventaService.encontrarProyectoPrima(proyecto);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
    
    //Función para obtener las lotes activos del proyecto de la base de datos con financiamiento
    @GetMapping("/obtenerVentasFinanciamiento")
    public ResponseEntity<List<Venta>> obtenerVentasFinanciamiento(@RequestParam Long proyectoId) {
        Proyecto proyecto = proyectoService.encontrar(proyectoId);
        List<Venta> ventas = ventaService.encontrarProyectoFinanciamiento(proyecto);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
    
    //Función para obtener las lotes activos del proyecto de la base de datos con mantenimiento
    @GetMapping("/obtenerVentasMantenimiento")
    public ResponseEntity<List<Venta>> obtenerVentasMantenimiento(@RequestParam Long proyectoId) {
        Proyecto proyecto = proyectoService.encontrar(proyectoId);
        List<Venta> ventas = ventaService.encontrarProyectoMantenimiento(proyecto);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
    
    //Función para obtener las pagos del proyecto de la base de datos
    @GetMapping("/pagos/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<Pago> GetPagos(@Valid DataTablesInput input,  @PathVariable Long idProyecto) {
        return pagoService.listarPagos(input, idProyecto);
    }
    
    //Función para agregar un pago en la base de datos
    @PostMapping("/AgregarPago")
    public ResponseEntity<String> AgregarPago(Pago pago, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            pagoService.agregar(pago);
            String mensaje = "Se ha agregado un pago.";
            bitacoraService.registrarAccion("Agregar pago "+pago.getTipo().toLowerCase());
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el pago.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para eliminar un pago de la base de datos
    @PostMapping("/EliminarPago/{idPago}")
    public ResponseEntity<String> EliminarPago(Pago pago) {
        try {
            Pago newPago = pagoService.encontrar(pago.getIdPago());
            pagoService.eliminar(pago);
            String mensaje = "Se ha eliminado un pago correctamente.";
            bitacoraService.registrarAccion("Eliminar pago "+newPago.getTipo().toLowerCase());
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el pago.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para obtener un pago de la base de datos
    @GetMapping("/ObtenerPago/{id}")
    public ResponseEntity<Pago> ObtenerPago(@PathVariable Long id) {
        Pago pago = pagoService.encontrar(id);
        if (pago != null) {
            return ResponseEntity.ok(pago);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Función para actualizar un pago de la base de datos
    @PostMapping("/ActualizarPago")
    public ResponseEntity<String> ActualizarPago(Pago pago, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            pagoService.actualizar(pago);
            String mensaje = "Se ha actualizado el pago correctamente.";
            bitacoraService.registrarAccion("Actualizar pago "+pago.getTipo().toLowerCase());
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el pago.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
