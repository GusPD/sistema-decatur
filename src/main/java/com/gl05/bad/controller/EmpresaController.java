package com.gl05.bad.controller;

import com.gl05.bad.domain.Empresa;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.EmpresaService;
import com.gl05.bad.servicio.UserService;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmpresaController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private UserService userService;
    
    //Función para redigir a la vista de empresas
    @GetMapping("/Empresas")
    public String mostrarEmpresas(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Empresas");
        String username = authentication.getName();
        Usuario usuario = userService.encontrarUsername(username);
        model.addAttribute("usuario", usuario);
        return "/Empresa/GestionarEmpresa";
    }
    
    //Función para obtener las empresas de la base de datos
    @GetMapping(value="/empresas/data", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Empresa> GetEmpresas(@Valid DataTablesInput input) {
        return empresaService.listarEmpresas(input);
    }

    //Función para redigir a la vista de la empresa
    @GetMapping("/Empresa/{idEmpresa}")
    public String mostrarEmpresa(Model model, @PathVariable Long idEmpresa, Authentication authentication) {
        model.addAttribute("pageTitle", "Empresas");
        String username = authentication.getName();
        Usuario usuario = userService.encontrarUsername(username);
        Empresa empresa = empresaService.encontrar(idEmpresa);
        Set<Empresa> listaEmpresasAsignadas = usuario.getEmpresas();
        if(!listaEmpresasAsignadas.contains(empresa)){
            return "accesodenegado";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("empresa", empresa);
        return "/Empresa/Empresa";
    }

    //Función para agregar una empresa en la base de datos
    @PostMapping("/AgregarEmpresa")
    public ResponseEntity<String> AgregarEmpresa(Empresa empresa, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            empresaService.agregar(empresa);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario=userService.encontrarUsername(authentication.getName());
            usuario.añadirEmpresa(empresa);
            String mensaje = "Se ha agregado una empresa.";
            bitacoraService.registrarAccion("Agregar empresa");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar la empresa.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para eliminar una empresa de la base de datos
    @PostMapping("/EliminarEmpresa/{idEmpresa}")
    public ResponseEntity<String> EliminarEmpresa(Empresa empresa) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario=userService.encontrarUsername(authentication.getName());
            usuario.eliminarEmpresa(empresa);
            empresaService.eliminar(empresa);
            String mensaje = "Se ha eliminado una empresa correctamente.";
            bitacoraService.registrarAccion("Eliminar empresa");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario=userService.encontrarUsername(authentication.getName());
            usuario.añadirEmpresa(empresa);
            String error = "Ha ocurrido un error al eliminar la empresa.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para obtener una empresa de la base de datos
    @GetMapping(value="/ObtenerEmpresa/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Empresa> ObtenerEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaService.encontrar(id);
        if (empresa != null) {
            return ResponseEntity.ok(empresa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Función para actualizar una empresa de la base de datos
    @PostMapping("/ActualizarEmpresa")
    public ResponseEntity<String> ActualizarEmpresa(Empresa empresa, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            empresaService.actualizar(empresa);
            String mensaje = "Se ha actualizado la empresa correctamente.";
            bitacoraService.registrarAccion("Actualizar empresa");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar la empresa.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
