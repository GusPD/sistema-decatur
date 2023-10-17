package com.gl05.bad.controller;

import com.gl05.bad.domain.AsignacionVisitante;
import com.gl05.bad.domain.Documento;
import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.domain.Visitante;
import com.gl05.bad.domain.VistaTrabajadoresProyecto;
import com.gl05.bad.servicio.AsignacionVisitanteService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.DocumentoService;
import com.gl05.bad.servicio.PersonaService;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.UserService;
import com.gl05.bad.servicio.VisitanteService;
import com.gl05.bad.servicio.VistaTrabajadoresProyectoService;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private AsignacionVisitanteService asigVisitanteService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    @Autowired
    private UserService usuarioService;
    
    //Función que redirige a la vista de los trabajadores del sistema
    @GetMapping("/TrabajadoresSistema")
    public String mostrarProyecto(Model model, Proyecto proyecto) {
        model.addAttribute("pageTitle", "Trabajadores");
        return "/Datos de Proyecto/TrabajadoresSistema";
    }
    
    //Función que obtiene los trabajadores del sistema
    @GetMapping("/trabajadores/data")
    @ResponseBody
    public DataTablesOutput<Visitante> GetTrabajadores(@Valid DataTablesInput input) {
        return visitanteService.listarTrabajadores(input);
    }
    
    //Función para redirigir a la vista de los trabajadores del proyecto
    @GetMapping("/Trabajadores/{idProyecto}")
    public String mostrarTrabajadoresProyecto(Model model, Proyecto proyecto) {
        model.addAttribute("pageTitle", "Trabajadores Proyecto");
        model.addAttribute("proyecto", proyecto);
        return "/Proyecto/TrabajadoresProyecto";
    }
    
    //Función que obtiene los trabajadores del proyecto
    @GetMapping("/trabajadoresProyecto/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<VistaTrabajadoresProyecto> GetTrabajadoreProyecto(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return vistaTrabajadoresProyectoService.listarTrabajadores(input, idProyecto);
    }
    
    //Función que redirige a la información del trabajador    
    @GetMapping("/InformacionTrabajador/{idProyecto}/{idPersona}")
    public String InformacionTrabajador(Model model, Persona persona, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Perfil Trabajador");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Visitante newTrabajador = visitanteService.encontrarPersona(newPersona);
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        System.out.println("Nuevo proyecto: " + newProyecto);
        Set<Proyecto> proyectosPropietario =  usuario.getProyectos();
        if (!proyectosPropietario.contains(newProyecto) && newProyecto!=null) {
            return "accesodenegado";
        }
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("trabajador", newTrabajador);
        model.addAttribute("persona", newPersona);
        return "/Trabajador/InformacionGeneral/trabajadorInformacion";
    }
    
    //Función que obtiene los documentos del trabajador
    @GetMapping("/documentosTrabajador/data/{idDocumento}")
    @ResponseBody
    public DataTablesOutput<Documento> GetDocumentos(@Valid DataTablesInput input, @PathVariable Integer idDocumento) {
        return documentoService.listarDocumentos(input, idDocumento);
    }
    
    //Función que redirige a la vista de los documentos del trabajador
    @GetMapping("/DocumentosTrabajador/{idProyecto}/{idPersona}")
    public String DocumentosTrabajador(Model model, Persona persona, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Perfil Trabajador");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Visitante newTrabajador = visitanteService.encontrarPersona(newPersona);
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        System.out.println("Nuevo proyecto: " + newProyecto);
        Set<Proyecto> proyectosPropietario =  usuario.getProyectos();
        if (!proyectosPropietario.contains(newProyecto) && newProyecto!=null) {
            return "accesodenegado";
        }
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("trabajador", newTrabajador);
        model.addAttribute("persona", newPersona);
        return "/Trabajador/InformacionGeneral/trabajadorDocumentos";
    }
    
    //Función que agrega un documento al trabajador
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
    
    //Función que elimina un documento del trabajador
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
    
    //Función para visualizar un documento del trabajador
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
    
    //Función que obtiene los terrenos del trabajador
    @GetMapping("/terrenosTrabajador/data/{idVisitante}")
    @ResponseBody
    public DataTablesOutput<AsignacionVisitante> GetTerrenos(@Valid DataTablesInput input, @PathVariable Long idVisitante) {
        return asigVisitanteService.listarTerrenosTrabajador(input, idVisitante);
    }
    
    //Función que que redirige a la vista de los terrenos del trabajador
    @GetMapping("/TerrenosTrabajador/{idProyecto}/{idPersona}")
    public String TerrenosTrabajador(Model model, Persona persona, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Perfil Trabajador");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Visitante newTrabajador = visitanteService.encontrarPersona(newPersona);
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        System.out.println("Nuevo proyecto: " + newProyecto);
        Set<Proyecto> proyectosPropietario =  usuario.getProyectos();
        if (!proyectosPropietario.contains(newProyecto) && newProyecto!=null) {
            return "accesodenegado";
        }
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("trabajador", newTrabajador);
        model.addAttribute("persona", newPersona);
        return "/Trabajador/InformacionGeneral/trabajadorTerrenos";
    }

    //Función que agrega un trabajador a la base de datos
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

    //Función que elimina un trabajador de la base de datos
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

    //Función que obtiene un trabajador de la base de datos
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

    //Función que actualiza un trabajador de la base de datos
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
}
