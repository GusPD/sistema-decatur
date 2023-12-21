package com.gl05.bad.controller;

import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.Correo;
import com.gl05.bad.domain.Documento;
import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Propietario;
import com.gl05.bad.domain.Referencia;
import com.gl05.bad.domain.Telefono;
import com.gl05.bad.domain.TipoDocumento;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.domain.VistaPropietariosProyecto;
import com.gl05.bad.servicio.AsignacionPropietarioService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CorreoService;
import com.gl05.bad.servicio.DocumentoService;
import com.gl05.bad.servicio.PersonaService;
import com.gl05.bad.servicio.PropietarioService;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.ReferenciaService;
import com.gl05.bad.servicio.TelefonoService;
import com.gl05.bad.servicio.TipoDocumentoService;
import com.gl05.bad.servicio.UserService;
import com.gl05.bad.servicio.VistaPropietariosProyectoService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
public class PropietarioController {
    
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private PropietarioService propietarioService;
    
    @Autowired
    private VistaPropietariosProyectoService vistaPropietariosService;
    
    @Autowired
    private PersonaService personaService;
    
    @Autowired
    private TelefonoService telefonoService;
    
    @Autowired
    private CorreoService correoService;
    
    @Autowired
    private ReferenciaService referenciaService;
    
    @Autowired
    private DocumentoService documentoService;
    
    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private AsignacionPropietarioService asigPropietarioService;
    
    @Autowired
    private UserService usuarioService;

    @Autowired
    private TipoDocumentoService tipoDocumentoService;
    
    //Función que redirige a la visa de los propietarios del sistema
    @GetMapping("/PropietariosSistema")
    public String mostrarProyecto(Model model, Proyecto proyecto) {
        model.addAttribute("pageTitle", "Propietarios");
        List<TipoDocumento> listaTipoDocumentos = tipoDocumentoService.listaTipoDocumentos();
        model.addAttribute("tiposDocumento", listaTipoDocumentos);
        return "/Datos de Proyecto/PropietariosSistema";
    }
    
    //Función que obtiene los propietarios de la base de datos
    @GetMapping(value="/propietarios/data", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Propietario> GetPropietarios(@Valid DataTablesInput input) {
        return propietarioService.listarPropietarios(input);
    }
    
    //Función que redirigir a la vista de los propietarios del proyecto
    @GetMapping("/Propietarios/{idProyecto}")
    public String mostrarPropietariosProyecto(Model model, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Propietarios Proyecto");
        Proyecto proyectoEncontrado = proyectoService.encontrar(proyecto.getIdProyecto());
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Proyecto> listaProyectosAsignados = usuario.getProyectos();
        if(!listaProyectosAsignados.contains(proyectoEncontrado)){
            return "accesodenegado";
        }
        model.addAttribute("proyecto", proyectoEncontrado);
        return "/Proyecto/PropietariosProyecto";
    }
    
    //Función que obtiene los propietarios del proyecto
    @GetMapping(value="/propietariosProyecto/data/{idProyecto}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<VistaPropietariosProyecto> GetPropietariosProyecto(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return vistaPropietariosService.listarPropietarios(input, idProyecto);
    }
    
    //Función que redirige a la información del propietario    
    @GetMapping("/InformacionPropietario/{idProyecto}/{idPersona}")
    public String InformacionPropietario(Model model, Persona persona, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Perfil Propietario");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Propietario newPropietario = propietarioService.encontrarPersona(newPersona);
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Proyecto> proyectosPropietario =  usuario.getProyectos();
        if (!proyectosPropietario.contains(newProyecto) && proyecto.getIdProyecto()>0) {
            return "accesodenegado";
        }
        List<TipoDocumento> listaTipoDocumentos = tipoDocumentoService.listaTipoDocumentos();
        model.addAttribute("tiposDocumento", listaTipoDocumentos);
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("propietario", newPropietario);
        model.addAttribute("persona", newPersona);
        return "/Propietario/InformacionGeneral/Informacion";
    }
    
    //Función que obtiene los correos del propietario
    @GetMapping(value="/correoPropietario/data/{idPropietario}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Correo> GetCorreos(@Valid DataTablesInput input, @PathVariable Long idPropietario) {
        return correoService.listarCorreos(input, idPropietario);
    }
    
    //Función que redirige a la vista de los correos del propietario
    @GetMapping("/CorreosPropietario/{idProyecto}/{idPersona}")
    public String CorreosPropietarioVenta(Model model, Persona persona, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Perfil Propietario");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Propietario newPropietario = propietarioService.encontrarPersona(newPersona);
        List<String> tiposCorreo = listarTiposCorreos();
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Proyecto> proyectosPropietario =  usuario.getProyectos();
        if (!proyectosPropietario.contains(newProyecto) && proyecto.getIdProyecto()>0) {
            return "accesodenegado";
        }
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("tiposCorreo", tiposCorreo);
        model.addAttribute("propietario", newPropietario);
        model.addAttribute("persona", newPersona);
        return "/Propietario/InformacionGeneral/Correos";
    }
    
    //Función que agrega un correo al propietario
    @PostMapping("/AgregarCorreo")
    public ResponseEntity<String> agregarCorreoPropietario(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("tipo") String tipoCorreo,
            @RequestParam("correo") String correo,
            @RequestParam("idPropietario") Long idPropietario) {
        try {
            if(correoService.encontrarCorreo(correo)== null){
                Propietario newPropietario = propietarioService.encontrar(idPropietario);
                Correo newCorreo = new Correo();
                newCorreo.setTipo(tipoCorreo);
                newCorreo.setCorreo(correo);
                newCorreo.setPropietario(newPropietario);
                correoService.agregar(newCorreo);
                String mensaje = "Se ha agregado el correo correctamente.";
                bitacoraService.registrarAccion("Agregar correo del propietario");
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "Ya se encuentra registrado el correo.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);        
            }
        } catch(Exception e) {
            String error = "Ha ocurrido un error al agregar el correo.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función que elimina un correo del propietario
    @PostMapping("/EliminarCorreo/{idCorreo}")
    public ResponseEntity<String> eliminarCorreoPropietario(Correo correo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            correoService.eliminar(correo);
            String mensaje = "Se ha eliminado el correo correctamente.";
            bitacoraService.registrarAccion("Eliminar correo del propietario");
            return ResponseEntity.ok(mensaje);
        }catch(Exception e) {
            String error = "Ha ocurrido un error al eliminar el correo.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función que obtiene los telefonos del propietario
    @GetMapping(value="/telefonoPropietario/data/{idPropietario}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Telefono> GetTelefonos(@Valid DataTablesInput input, @PathVariable Long idPropietario) {
        return telefonoService.listarTelefonos(input, idPropietario);
    }
    
    //Función que redirige a la vista de los telefonos del propietario
    @GetMapping("/TelefonosPropietario/{idProyecto}/{idPersona}")
    public String TelefonosPropietarioVenta(Model model, Persona persona, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Perfil Propietario");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Propietario newPropietario = propietarioService.encontrarPersona(newPersona);
        List<String> tiposTelefono = listarTipoTelefono();    
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Proyecto> proyectosPropietario =  usuario.getProyectos();
        if (!proyectosPropietario.contains(newProyecto) && proyecto.getIdProyecto()>0) {
            return "accesodenegado";
        }
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("tiposTelefonos", tiposTelefono);
        model.addAttribute("propietario", newPropietario);
        model.addAttribute("persona", newPersona);
        return "/Propietario/InformacionGeneral/Telefonos";
    }
    
    //Función que agrega un telefono al propietario
    @PostMapping("/AgregarTelefono")
    public ResponseEntity<String> agregarTelefonoPropietario(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("tipo") String tipoTelefono,
            @RequestParam("telefono") String telefono,
            @RequestParam("idPropietario") Long idPropietario) {
        try {
            if(telefonoService.encontrarTelefono(telefono)== null){
                Telefono newTelefono = new Telefono();
                newTelefono.setTipo(tipoTelefono);
                newTelefono.setTelefono(telefono);
                newTelefono.setIdPropietario(idPropietario);
                telefonoService.agregar(newTelefono);
                String mensaje = "Se ha agregado el teléfono correctamente.";
                bitacoraService.registrarAccion("Agregar teléfono del propietario");
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "Ya se encuentra registrado el teléfono.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch(Exception e) {
            String error = "Ha ocurrido un error al agregar el teléfono.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función que elimina un telefono del propietario
    @PostMapping("/EliminarTelefono/{idTelefono}")
    public ResponseEntity<String> eliminarTelefonoPropietario(Telefono telefono, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            telefonoService.eliminar(telefono);
            String mensaje = "Se ha eliminado el teléfono correctamente.";
            bitacoraService.registrarAccion("Eliminar teléfono del propietario");
            return ResponseEntity.ok(mensaje);
        } catch(Exception e) {
            String error = "Ha ocurrido un error al eliminar el teléfono.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función que obtiene las referencias del propietario
    @GetMapping(value="/referenciaPropietario/data/{idPropietario}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Referencia> GetReferencias(@Valid DataTablesInput input, @PathVariable Long idPropietario) {
        return referenciaService.listarReferencias(input, idPropietario);
    }
    
    //Función que redirige a la vista de las referencias del propietario
    @GetMapping("/ReferenciasPropietario/{idProyecto}/{idPersona}")
    public String ReferenciasPropietarioVenta(Model model, Persona persona, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Perfil Propietario");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Propietario newPropietario = propietarioService.encontrarPersona(newPersona);
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Proyecto> proyectosPropietario =  usuario.getProyectos();
        if (!proyectosPropietario.contains(newProyecto) && proyecto.getIdProyecto()>0) {
            return "accesodenegado";
        }
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("propietario", newPropietario);
        model.addAttribute("persona", newPersona);
        return "/Propietario/InformacionGeneral/Referencias";
    }
    
    //Función que agrega una referencia al propietario
    @PostMapping("/AgregarReferencia")
    public ResponseEntity<String> agregarReferenciaPropietario(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("telefono") String telefono,
            @RequestParam("correo") String correo,
            @RequestParam("idPropietario") Long idPropietario) {
        try {
            Referencia newReferencia = new Referencia();
            newReferencia.setNombre(nombre);
            newReferencia.setApellido(apellido);
            newReferencia.setTelefono(telefono);
            newReferencia.setCorreo(correo);
            newReferencia.setIdPropietario(idPropietario);
            referenciaService.agregar(newReferencia);
            String mensaje = "Se ha agregado la referencia correctamente.";
            bitacoraService.registrarAccion("Agregar referencia del propietario");
            return ResponseEntity.ok(mensaje);
        } catch(Exception e) {
            String error = "Ha ocurrido un error al agregar la referencia.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función que elimina una referencia al propietario
    @PostMapping("/EliminarReferencia/{idReferencia}")
    public ResponseEntity<String> eliminarCorreoPropietario(Referencia referencia, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            referenciaService.eliminar(referencia);
            String mensaje = "Se ha eliminado la referencia correctamente.";
            bitacoraService.registrarAccion("Eliminar referencia del propietario");
            return ResponseEntity.ok(mensaje);
        }catch(Exception e) {
            String error = "Ha ocurrido un error al eliminar la referencia.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función que obtiene los documentos del propietario
    @GetMapping(value="/documentoPropietario/data/{idDocumento}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Documento> GetDocumentos(@Valid DataTablesInput input, @PathVariable Integer idDocumento) {
        return documentoService.listarDocumentos(input, idDocumento);
    }
    
    //Función que redirige a la vista de los documentos del propietario
    @GetMapping("/DocumentosPropietario/{idProyecto}/{idPersona}")
    public String DocumentosPropietarioVenta(Model model, Persona persona, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Perfil Propietario");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Propietario newPropietario = propietarioService.encontrarPersona(newPersona);
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Proyecto> proyectosPropietario =  usuario.getProyectos();
        if (!proyectosPropietario.contains(newProyecto) && proyecto.getIdProyecto()>0) {
            return "accesodenegado";
        }
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("propietario", newPropietario);
        model.addAttribute("persona", newPersona);
        return "/Propietario/InformacionGeneral/Documentos";
    }
    
    //Función que agrega un documento al propietario
    @PostMapping("/AgregarDocumentoPropietario")
    public ResponseEntity<String> agregarDocumentoPropietario(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("nombre") String nombre,
            @RequestParam("documento") MultipartFile documento,
            @RequestParam("idPropietario") Long idPropietario) {
        try {
            Documento newDocumento = new Documento();
            
            byte[] fileBytes = documento.getBytes();
            if (fileBytes != null && fileBytes.length > 0) {
                Propietario newPropietario = propietarioService.encontrar(idPropietario);
                newDocumento.setNombre(nombre);
                newDocumento.setDocumento(fileBytes);
                newDocumento.setIdListDocumento(newPropietario.getIdDocumento());
                documentoService.agregar(newDocumento);
                String mensaje = "Se ha agregado el documento correctamente.";
                bitacoraService.registrarAccion("Agregar documento del propietario");
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "Ha ocurrido un error al agregar el documento, documento vacío.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch(Exception e) {
            String error = "Ha ocurrido un error al agregar el documento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función que elimina un documento del propietario
    @PostMapping("/EliminarDocumentoPropietario/{idDocumento}")
    public ResponseEntity<String> eliminarDocumentoPropietario(Documento documento, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            documentoService.eliminar(documento);
            String mensaje = "Se ha eliminado el documento correctamente.";
            bitacoraService.registrarAccion("Eliminar documento del propietario");
            return ResponseEntity.ok(mensaje);
        }catch(Exception e) {
            String error = "Ha ocurrido un error al eliminar el documento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función para ver el documento de un propietario
    @GetMapping("/DocumentoPropietario/{idDocumento}")
    public ResponseEntity <byte[]> mostrarDocumentoPDFPropietario(@PathVariable("idDocumento") Long id, Authentication authentication) {
        Documento archivo = new Documento();
        archivo.setIdDocumento(id);
        Documento archivoExistente = documentoService.encontrar(archivo);
        byte[] pdfBytes = archivoExistente.getDocumento();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("Content-Disposition", "inline; filename=" + archivoExistente.getNombre().replace(" ","_") + ".pdf");
        bitacoraService.registrarAccion("Ver documento propietario");
        return new ResponseEntity <>(pdfBytes, headers, HttpStatus.OK);
    }

    //Función que obtiene los terrenos del propietario
    @GetMapping(value="/terrenosPropietario/data/{idPropietario}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<AsignacionPropietario> GetTerrenos(@Valid DataTablesInput input, @PathVariable Long idPropietario) {
        return asigPropietarioService.listarTerrenosPropietario(input, idPropietario);
    }
    
    //Función que redirige a la vista de los terrenos del propietario
    @GetMapping("/TerrenosPropietario/{idProyecto}/{idPersona}")
    public String TerrenosPropietarioVenta(Model model, Persona persona, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Perfil Propietario");
        Persona newPersona = personaService.encontrar(persona.getIdPersona());
        Propietario newPropietario = propietarioService.encontrarPersona(newPersona);
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Proyecto> proyectosPropietario =  usuario.getProyectos();
        if (!proyectosPropietario.contains(newProyecto) && proyecto.getIdProyecto()>0) {
            return "accesodenegado";
        }
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("propietario", newPropietario);
        model.addAttribute("persona", newPersona);
        return "/Propietario/InformacionGeneral/Terrenos";
    }

    //Función que agrega un propietario a la base de datos
    @PostMapping("/AgregarPropietario")
    public ResponseEntity<String> AgregarPropietario(Propietario propietario, Persona persona, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            if(personaService.encontrarNumero(persona.getNumero())== null){
                personaService.agregar(persona);
                Persona newPersona = personaService.encontrarNumero(persona.getNumero());
                propietario.setPersona(newPersona);
                propietarioService.agregar(propietario);
                String mensaje = "Se ha agregado un propietario.";
                bitacoraService.registrarAccion("Agregar propietario");
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "Ya se encuentra registrado el propietario.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el propietario.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que elimina un propietario de la base de datos
    @PostMapping("/EliminarPropietario/{idPersona}")
    public ResponseEntity<String> EliminarPropietario(Persona persona) {
        try {
             Propietario newPropietario = propietarioService.encontrarPersona(persona);
             propietarioService.eliminar(newPropietario);
             personaService.eliminar(persona);
             String mensaje = "Se ha eliminado un propietario correctamente.";
             bitacoraService.registrarAccion("Eliminar propietario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el propietario.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que obtiene un propietario de la base de datos
    @GetMapping(value="/ObtenerPropietario/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> ObtenerPropietario(@PathVariable Long id) {
        Persona persona = personaService.encontrar(id);
        Propietario propietario = propietarioService.encontrarPersona(persona);
        if (propietario != null && persona != null) {
            Map<String, Object> entidadesMap = new HashMap<>();
            entidadesMap.put("propietario", propietario);
            entidadesMap.put("persona", persona);
            return ResponseEntity.ok(entidadesMap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //Función que obtiene un propietario del proyecto
    @GetMapping(value="/ObtenerPropietarioProyecto/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> ObtenerPropietarioProyecto(@PathVariable Long id) {
        AsignacionPropietario asignacion = asigPropietarioService.encontrar(id);
        if (asignacion != null) {
            Map<String, Object> entidadesMap = new HashMap<>();
            entidadesMap.put("asignacion", asignacion);
            return ResponseEntity.ok(entidadesMap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Función que actualiza un propietario de la base de datos
    @PostMapping("/ActualizarPropietario")
    public ResponseEntity<String> ActualizarPropietario( Propietario propietario, Persona persona, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            propietario.setPersona(persona);
            propietarioService.actualizar(propietario);
            personaService.actualizar(persona);
            String mensaje = "Se ha actualizado el propietario correctamente.";
            bitacoraService.registrarAccion("Actualizar propietario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el propietario.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función para listar los tipos de correos
    public List<String> listarTiposCorreos() {
        List<String> tiposCorreos = Arrays.asList("Trabajo", "Privado","Personal","Institucional");
        return tiposCorreos;
    }
    
    //Función para listar los tipos de telefonos
    public List<String> listarTipoTelefono() {
        List<String> tipoTelefono = Arrays.asList("Casa", "Oficina","Fijo", "Móvil");
        return tipoTelefono;
    }
}
