package com.gl05.bad.controller;

import com.gl05.bad.domain.CuentaBancaria;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CuentaBancariaService;
import com.gl05.bad.servicio.ProyectoService;
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
public class CuentaBancariaController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private CuentaBancariaService cuentaService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    //Función para redigir a la vista de cuentas
    @GetMapping("/CuentasBancarias/{idProyecto}")
    public String mostrarProyecto(Model model, @PathVariable("idProyecto") Long idProyecto) {
        model.addAttribute("pageTitle", "Cuentas");
        Proyecto newProyecto = proyectoService.encontrar(idProyecto);
        model.addAttribute("proyecto", newProyecto);
        return "/Cuenta Bancaria/GestionarCuentaBancaria";
    }
    
    //Función para obtener las cuentas de la base de datos
    @GetMapping("/cuentas/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<CuentaBancaria> GetCuentas(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return cuentaService.listarCuentas(input, idProyecto );
    }

    //Función para agregar una cuenta en la base de datos
    @PostMapping("/AgregarCuenta")
    public ResponseEntity<String> AgregarCuenta(@RequestParam("idProyecto") Long idProyecto, CuentaBancaria cuenta, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Proyecto proyecto = proyectoService.encontrar(idProyecto);
            cuenta.setProyecto(proyecto);
            cuentaService.agregar(cuenta);
            String mensaje = "Se ha agregado una cuenta bancaria.";
            bitacoraService.registrarAccion("Agregar cuenta bancaria");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar la cuenta bancaria.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para eliminar una cuenta de la base de datos
    @PostMapping("/EliminarCuenta/{idCuenta}")
    public ResponseEntity<String> EliminarCuenta(CuentaBancaria cuenta) {
        try {
            cuentaService.eliminar(cuenta);
            String mensaje = "Se ha eliminado una cuenta bancaria correctamente.";
            bitacoraService.registrarAccion("Eliminar cuenta bancaria");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar la cuenta bancaria";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para obtener una cuenta de la base de datos
    @GetMapping("/ObtenerCuenta/{id}")
    public ResponseEntity<CuentaBancaria> ObtenerCuenta(@PathVariable Long id) {
        CuentaBancaria cuenta = cuentaService.encontrar(id);
        if (cuenta != null) {
            return ResponseEntity.ok(cuenta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Función para actualizar una cuenta de la base de datos
    @PostMapping("/ActualizarCuenta")
    public ResponseEntity<String> ActualizarCuenta(@RequestParam("idProyecto") Long idProyecto, CuentaBancaria cuenta, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Proyecto proyecto = proyectoService.encontrar(idProyecto);
            cuenta.setProyecto(proyecto);
            cuentaService.actualizar(cuenta);
            String mensaje = "Se ha actualizado la cuenta bancaria correctamente.";
            bitacoraService.registrarAccion("Actualizar cuenta bancaria");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar la cuenta bancaria.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
