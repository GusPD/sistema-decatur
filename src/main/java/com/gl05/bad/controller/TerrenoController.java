package com.gl05.bad.controller;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Terreno;
import com.gl05.bad.domain.VistaTerreno;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.TerrenoService;
import com.gl05.bad.servicio.VistaTerrenoService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TerrenoController {
    
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private TerrenoService terrenoService;
    
    @Autowired
    private VistaTerrenoService vistaTerrenoService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    //Función que redirige a la vista de los terrenos del proyecto
    @GetMapping("/Terrenos/{idProyecto}")
    public String mostrarTerrenoProyecto(Model model, Proyecto proyecto) {
        model.addAttribute("pageTitle", "Terrenos");
        Proyecto proyectoEncontrado = proyectoService.encontrar(proyecto.getIdProyecto());
        var listaProyectos = proyectoService.listaProyectos();
        model.addAttribute("proyectos", listaProyectos);
        model.addAttribute("proyecto", proyectoEncontrado);
        return "/Proyecto/TerrenosProyecto";
    }
    
    //Función que obtiene los terrenos del proyecto
    @GetMapping("/terrenos/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<VistaTerreno> GetTerrenos(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return vistaTerrenoService.listarTerrenos(input, idProyecto);
    }

    //Función que agrega un terreno a la base de datos
    @PostMapping("/AgregarTerreno/{idProyecto}")
    public ResponseEntity AgregarTerreno(@PathVariable("idProyecto") Long idProyecto,Terreno terreno, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            String valorSeccion = "";
            String valorPoligono = terreno.getPoligono().toUpperCase();
            if(terreno.getSeccion()!=null){
                valorSeccion = terreno.getSeccion().toLowerCase();
            }
            Boolean existeTerreno = true;
            List<Terreno> listaTerrenos = terrenoService.listaTerrenos();
            for (Terreno eTerreno : listaTerrenos) {
                if(eTerreno.getMatricula().equals(terreno.getMatricula()) || (eTerreno.getPoligono().equals(valorPoligono) && eTerreno.getNumero().equals(terreno.getNumero()) && eTerreno.getSeccion().equals(valorSeccion) && eTerreno.getProyecto().getIdProyecto().equals(idProyecto))){
                    existeTerreno = false;
                }
            }
            if(existeTerreno){
                Proyecto proyecto = proyectoService.encontrar(idProyecto);
                terreno.setProyecto(proyecto);
                terreno.setPoligono(valorPoligono);
                terreno.setSeccion(valorSeccion);
                terrenoService.agregar(terreno);
                String mensaje = "Se ha agregado un terreno.";
                bitacoraService.registrarAccion("Agregar terreno");
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "El terreno ya se encuentra registrado.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el terreno.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que elimina un terreno de la base de datos
    @PostMapping("/EliminarTerreno/{idTerreno}")
    public ResponseEntity EliminarTerreno(Terreno terreno) {
        try {
            terrenoService.eliminar(terreno);
             String mensaje = "Se ha eliminado un terreno correctamente.";
             bitacoraService.registrarAccion("Eliminar terreno");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el terreno.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que obtiene un terreno de la base de datos
    @GetMapping("/ObtenerTerreno/{id}")
    public ResponseEntity<Terreno> ObtenerTerreno(@PathVariable Long id) {
        Terreno terreno = terrenoService.encontrar(id);
        if (terreno != null) {
            return ResponseEntity.ok(terreno);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Función que actualiza un terreno de la base de datos
    @PostMapping("/ActualizarTerreno/{idProyecto}")
    public ResponseEntity ActualizarTerreno(@PathVariable("idProyecto") Long idProyecto, Terreno terreno, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Proyecto proyecto = proyectoService.encontrar(idProyecto);
            terreno.setProyecto(proyecto);
            terreno.setPoligono(terreno.getPoligono().toUpperCase());
            terreno.setSeccion(terreno.getSeccion().toLowerCase());
            terrenoService.actualizar(terreno);
            String mensaje = "Se ha actualizado el terreno correctamente.";
            bitacoraService.registrarAccion("Actualizar terreno");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el terreno.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
