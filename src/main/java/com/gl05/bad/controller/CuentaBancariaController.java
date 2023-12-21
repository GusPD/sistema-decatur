package com.gl05.bad.controller;

import com.gl05.bad.domain.CuentaBancaria;
import com.gl05.bad.domain.Empresa;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CuentaBancariaService;
import com.gl05.bad.servicio.EmpresaService;
import com.gl05.bad.servicio.UserServiceImp;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private EmpresaService empresaService;

    @Autowired
    private UserServiceImp usuarioService;
    
    //Función para redigir a la vista de cuentas
    @GetMapping("/CuentasBancarias/{idEmpresa}")
    public String mostrarCuentas(Model model, @PathVariable("idEmpresa") Long idEmpresa, Authentication authentication) {
        model.addAttribute("pageTitle", "Cuentas");
        Empresa newEmpresa = empresaService.encontrar(idEmpresa);
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Empresa> listaEmpresasAsignadas = usuario.getEmpresas();
        if(!listaEmpresasAsignadas.contains(newEmpresa)){
            return "accesodenegado";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("empresa", newEmpresa);
        return "/Cuenta Bancaria/GestionarCuentaBancaria";
    }
    
    //Función para obtener las cuentas de la base de datos
    @GetMapping(value="/cuentas/data/{idEmpresa}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<CuentaBancaria> GetCuentas(@Valid DataTablesInput input, @PathVariable Long idEmpresa) {
        return cuentaService.listarCuentas(input, idEmpresa );
    }

    //Función para agregar una cuenta en la base de datos
    @PostMapping("/AgregarCuenta")
    public ResponseEntity<String> AgregarCuenta(@RequestParam("idEmpresa") Long idEmpresa, CuentaBancaria cuenta, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Empresa empresa = empresaService.encontrar(idEmpresa);
            cuenta.setEmpresa(empresa);
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
    @GetMapping(value="/ObtenerCuenta/{id}", produces = "application/json; charset=UTF-8")
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
    public ResponseEntity<String> ActualizarCuenta(@RequestParam("idEmpresa") Long idEmpresa, CuentaBancaria cuenta, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Empresa empresa = empresaService.encontrar(idEmpresa);
            cuenta.setEmpresa(empresa);
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
