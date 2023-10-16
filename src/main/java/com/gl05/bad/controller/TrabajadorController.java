package com.gl05.bad.controller;

import com.gl05.bad.domain.AsignacionVisitante;
import com.gl05.bad.domain.Documento;
import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Terreno;
import com.gl05.bad.domain.Visitante;
import com.gl05.bad.domain.VistaTrabajadoresProyecto;
import com.gl05.bad.servicio.AsignacionVisitanteService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.DocumentoService;
import com.gl05.bad.servicio.PersonaService;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.TerrenoService;
import com.gl05.bad.servicio.VisitanteService;
import com.gl05.bad.servicio.VistaTrabajadoresProyectoService;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TrabajadorController {
    
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private VisitanteService visitanteService;
    
    @Autowired
    private VistaTrabajadoresProyectoService vistaTrabajadoresProyectoService;
    
    @Autowired
    private PersonaService personaService;
    
    @Autowired
    private DocumentoService documentoService;
    
    @Autowired
    private TerrenoService terrenoService;
    
    @Autowired
    private AsignacionVisitanteService asigVisitanteService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    @GetMapping("/TrabajadoresSistema")
    public String mostrarProyecto(Model model, Proyecto proyecto) {
        model.addAttribute("pageTitle", "Trabajadores");
        return "/Datos de Proyecto/TrabajadoresSistema";
    }
    
    @GetMapping("/TrabajadorSistema/{idPersona}")
    public String MostrarTrabajador(Model model, Persona persona) {
        model.addAttribute("pageTitle", "Perfil Trabajador");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Visitante newTrabajador = visitanteService.encontrarPersona(newPersona);
        
        //Manejo de documentos
        List<Documento> listaDocumentos = documentoService.listarDocumentos();
        List<Documento> documentosTrabajador = new ArrayList();
        for (var documento : listaDocumentos) {
            if(documento.getIdListDocumento() == (int) newTrabajador.getIdDocumento()){
                documentosTrabajador.add(documento);
            }
        }
        
        //Manejo de terrenos
        List<Terreno> listaTerrenos = terrenoService.listaTerrenos();
        List<AsignacionVisitante> listaAsignaciones = asigVisitanteService.listaAsignacionVisitantes();
        List<Terreno> terrenosTrabajador = new ArrayList();
        boolean existeAsignacion = false;
        for (var terreno : listaTerrenos) {
            for (var asignacion : listaAsignaciones) {
                if(Objects.equals(terreno.getIdTerreno(), asignacion.getVenta().getTerreno().getIdTerreno())){
                    existeAsignacion = true;
                }
            }
            if(existeAsignacion==true){
                terrenosTrabajador.add(terreno);
                existeAsignacion = false;
            }
        }
        
        model.addAttribute("terrenos", terrenosTrabajador);
        model.addAttribute("documentos", documentosTrabajador);
        model.addAttribute("trabajador", newTrabajador);
        model.addAttribute("persona", newPersona);
        return "/Trabajador/MostrarTrabajadorAdministracion";
    }
    
    @GetMapping("/Trabajador/{idProyecto}/{idPersona}")
    public String VerTrabajadorVenta(Model model, Persona persona, Proyecto proyecto) {
        model.addAttribute("pageTitle", "Perfil Trabajador");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Visitante newVisitante = visitanteService.encontrarPersona(newPersona);
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        
        //Manejo de documentos
        List<Documento> listaDocumentos = documentoService.listarDocumentos();
        List<Documento> documentosTrabajador = new ArrayList();
        for (var documento : listaDocumentos) {
            if(documento.getIdListDocumento() == (int) newVisitante.getIdDocumento()){
                documentosTrabajador.add(documento);
            }
        }
        
        //Manejo de terrenos
        List<Terreno> listaTerrenos = terrenoService.listaTerrenos();
        List<AsignacionVisitante> listaAsignaciones = asigVisitanteService.listaAsignacionVisitantes();
        List<Terreno> terrenosTrabajador = new ArrayList();
        boolean existeAsignacion = false;
        for (var terreno : listaTerrenos) {
            for (var asignacion : listaAsignaciones) {
                if(Objects.equals(terreno.getIdTerreno(), asignacion.getVenta().getTerreno().getIdTerreno()) && Objects.equals(newVisitante.getIdVisitante(), asignacion.getVisitante().getIdVisitante()) && Objects.equals(asignacion.getVenta().getTerreno().getProyecto().getIdProyecto(),newProyecto.getIdProyecto())){
                    existeAsignacion = true;
                }
            }
            if(existeAsignacion==true){
                terrenosTrabajador.add(terreno);
                existeAsignacion = false;
            }
        }
        
        model.addAttribute("terrenos", terrenosTrabajador);
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("documentos", documentosTrabajador);
        model.addAttribute("trabajador", newVisitante);
        model.addAttribute("persona", newPersona);
        return "/Trabajador/MostrarTrabajadorProyecto";
    }
    
    @GetMapping("/InformacionTrabajador/{idPersona}")
    public String InformacionTrabajador(Model model, Persona persona) {
        model.addAttribute("pageTitle", "Perfil Trabajador");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Visitante newTrabajador = visitanteService.encontrarPersona(newPersona);
        
        model.addAttribute("trabajador", newTrabajador);
        model.addAttribute("persona", newPersona);
        return "/Trabajador/InformacionGeneral/trabajadorInformacion";
    }
    
    @GetMapping("/DocumentosTrabajador/{idPersona}")
    public String DocumentosTrabajador(Model model, Persona persona) {
        model.addAttribute("pageTitle", "Perfil Trabajador");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Visitante newTrabajador = visitanteService.encontrarPersona(newPersona);
        
        model.addAttribute("trabajador", newTrabajador);
        model.addAttribute("persona", newPersona);
        return "/Trabajador/InformacionGeneral/trabajadorDocumentos";
    }
    
    @GetMapping("/TerrenosTrabajador/{idPersona}")
    public String TerrenosTrabajador(Model model, Persona persona) {
        model.addAttribute("pageTitle", "Perfil Trabajador");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Visitante newTrabajador = visitanteService.encontrarPersona(newPersona);
        
        model.addAttribute("trabajador", newTrabajador);
        model.addAttribute("persona", newPersona);
        return "/Trabajador/InformacionGeneral/trabajadorTerrenos";
    }
    
    @GetMapping("/trabajadores/data")
    @ResponseBody
    public DataTablesOutput<Visitante> GetTrabajadores(@Valid DataTablesInput input) {
        return visitanteService.listarTrabajadores(input);
    }
    
    @GetMapping("/trabajadoresProyecto/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<VistaTrabajadoresProyecto> GetTrabajadoreProyecto(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return vistaTrabajadoresProyectoService.listarTrabajadores(input, idProyecto);
    }

    @PostMapping("/AgregarTrabajador")
    public ResponseEntity AgregarTrabajador(Visitante visitante, Persona persona, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            if(personaService.encontrarDui(persona.getDui())== null){
                personaService.agregar(persona);
                Persona newPersona = personaService.encontrarDui(persona.getDui());
                visitante.setPersona(newPersona);
                visitanteService.agregar(visitante);
                String mensaje = "Se ha agregado un trabajador.";
                bitacoraService.registrarAccion("Agregar trabajador");
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "Ya se encuentra registrado el trabajador.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el trabajador.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/EliminarTrabajador/{idPersona}")
    public ResponseEntity EliminarTrabajador(Persona persona) {
        try {
             Visitante newTrabajador = visitanteService.encontrarPersona(persona);
             visitanteService.eliminar(newTrabajador);
             personaService.eliminar(persona);
             String mensaje = "Se ha eliminado un trabajador correctamente.";
             bitacoraService.registrarAccion("Eliminar trabajador");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el trabajador.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/ObtenerTrabajador/{id}")
    public ResponseEntity<Object> ObtenerTrabajador(@PathVariable Long id) {
        Persona persona = personaService.encontrar(id);
        Visitante trabajador = visitanteService.encontrarPersona(persona);
        if (trabajador != null && persona != null) {
            Map<String, Object> entidadesMap = new HashMap<>();
            entidadesMap.put("trabajador", trabajador);
            entidadesMap.put("persona", persona);
            return ResponseEntity.ok(entidadesMap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ActualizarTrabajador")
    public ResponseEntity ActualizarTrabajador( Visitante visitante, Persona persona, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            visitante.setPersona(persona);
            visitanteService.actualizar(visitante);
            personaService.actualizar(persona);
            String mensaje = "Se ha actualizado el trabajador correctamente.";
            bitacoraService.registrarAccion("Actualizar trabajador");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el trabajador.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/AgregarDocumentoTrabajador")
    public ResponseEntity agregarDocumentoTrabajador(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("nombre") String nombre,
            @RequestParam("documento") MultipartFile documento,
            @RequestParam("idVisitante") Long idVisitante) {
        try {
            Documento newDocumento = new Documento();
            
            byte[] fileBytes = documento.getBytes();
            Blob documentoConvertido = new javax.sql.rowset.serial.SerialBlob(fileBytes);
            
            Visitante newVisitante = visitanteService.encontrar(idVisitante);
            
            newDocumento.setNombre(nombre);
            newDocumento.setDocumento(documentoConvertido);
            newDocumento.setIdListDocumento(newVisitante.getIdDocumento());
            documentoService.agregar(newDocumento);
            String mensaje = "Se ha agregado el documento correctamente.";
            bitacoraService.registrarAccion("Agregar documento del trabajador");
            return ResponseEntity.ok(mensaje);
        } catch(Exception e) {
            String error = "Ha ocurrido un error al agregar el documento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/EliminarDocumentoTrabajador/{idDocumento}")
    public ResponseEntity eliminarDocumentoTrabajador(Documento documento, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            documentoService.eliminar(documento);
            String mensaje = "Se ha eliminado el documento correctamente.";
            bitacoraService.registrarAccion("Eliminar documento del trabajador");
            return ResponseEntity.ok(mensaje);
        }catch(Exception e) {
            String error = "Ha ocurrido un error al eliminar el documento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/DocumentoTrabajador/{IdDocumento}")
    public ResponseEntity <byte[]> mostrarDocumentoPDFTrabajador(@PathVariable("IdDocumento") Long id) {
        Documento archivo = new Documento();
        archivo.setIdDocumento(id);
        Documento archivoExistente = documentoService.encontrar(archivo);

        Blob pdfBlob = archivoExistente.getDocumento();
        byte[] pdfBytes;

        try {
            if (pdfBlob != null && pdfBlob.length() > 0) {
                pdfBytes = pdfBlob.getBytes(1, (int) pdfBlob.length());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.add("Content-Disposition", "inline; filename=" + archivoExistente.getNombre().replace(" ","_") + ".pdf");
                bitacoraService.registrarAccion("Ver documento trabajador");
                return new ResponseEntity <>(pdfBytes, headers, HttpStatus.OK);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
}
