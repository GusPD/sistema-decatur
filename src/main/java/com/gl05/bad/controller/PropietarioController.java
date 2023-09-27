package com.gl05.bad.controller;

import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.Correo;
import com.gl05.bad.domain.Documento;
import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Propietario;
import com.gl05.bad.domain.Referencia;
import com.gl05.bad.domain.Telefono;
import com.gl05.bad.domain.VistaPropietariosProyecto;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CorreoService;
import com.gl05.bad.servicio.DocumentoService;
import com.gl05.bad.servicio.PersonaService;
import com.gl05.bad.servicio.PropietarioService;
import com.gl05.bad.servicio.ReferenciaService;
import com.gl05.bad.servicio.TelefonoService;
import com.gl05.bad.servicio.VistaPropietariosProyectoService;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    
    @GetMapping("/GestionarPropietarios")
    public String mostrarProyecto(Model model, Proyecto proyecto) {
        model.addAttribute("pageTitle", "Propietarios");
        return "/Datos de Proyecto/PropietariosSistema";
    }
    
    @GetMapping("/MostrarPropietario/{idPersona}")
    public String MostrarPropietario(Model model, Persona persona) {
        model.addAttribute("pageTitle", "Perfil Propietario");
        Persona newPersona = personaService.encontrarPersona(persona.getIdPersona());
        Propietario newPropietario = propietarioService.encontrarPropietarioPersona(newPersona);
        
        //Manejo de correos
        var correos = correoService.listarCorreos();
        List<String> tiposCorreo = listarTiposCorreos();
        List<Correo> correosPropietario = new ArrayList();
        for (var eCorreo : correos) {
            if(Objects.equals(eCorreo.getIdPropietario(), newPropietario.getIdPropietario())){
                correosPropietario.add(eCorreo);
            }
        }
        
        //Manejo de telefonos
        var telefonos = telefonoService.listarTelefonos();
        List<String> tiposTelefono = listarTipoTelefono();
        List<Telefono> telefonosPropietario = new ArrayList();
        for (var eTelefono: telefonos) {
            if(Objects.equals(eTelefono.getIdPropietario(), newPropietario.getIdPropietario())){
                telefonosPropietario.add(eTelefono);
            }
        }
        
        //Manejo de referencias
        var referencias = referenciaService.listarReferencias();
        List<Referencia> referenciasPropietario = new ArrayList();
        for (var eReferencia: referencias) {
            if(Objects.equals(eReferencia.getIdPropietario(), newPropietario.getIdPropietario())){
                referenciasPropietario.add(eReferencia);
            }
        }
        
        //Manejo de documentos
        List<Documento> listaDocumentos = documentoService.listarDocumentos();
        List<Documento> documentosPropietario = new ArrayList();
        for (var documento : listaDocumentos) {
            if(documento.getIdListDocumento() == (int) newPropietario.getIdDocumento()){
                documentosPropietario.add(documento);
            }
        }
        
        model.addAttribute("documentos", documentosPropietario);
        model.addAttribute("referencias", referenciasPropietario);
        model.addAttribute("correos", correosPropietario);
        model.addAttribute("tiposCorreo", tiposCorreo);
        model.addAttribute("telefonos", telefonosPropietario);
        model.addAttribute("tiposTelefonos", tiposTelefono);
        model.addAttribute("propietario", newPropietario);
        model.addAttribute("persona", newPersona);
        return "/Propietario/MostrarPropietarioAdministracion";
    }
    
    @GetMapping("/propietarios/data")
    @ResponseBody
    public DataTablesOutput<Persona> GetPropietarios(@Valid DataTablesInput input) {
        return propietarioService.listarPropietarios(input);
    }
    
    @GetMapping("/propietariosProyecto/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<VistaPropietariosProyecto> GetPropietariosProyecto(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return vistaPropietariosService.listarPropietarios(input, idProyecto);
    }

    @PostMapping("/AgregarPropietario")
    public ResponseEntity AgregarPropietario(Propietario propietario, Persona persona, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            if(personaService.encontrarPersona(persona.getDui())== null){
                personaService.agregarPersona(persona);
                Persona newPersona = personaService.encontrarPersona(persona.getDui());
                propietario.setPersona(newPersona);
                propietarioService.agregarPropietario(propietario);
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

    @PostMapping("/EliminarPropietario/{idPersona}")
    public ResponseEntity EliminarPropietario(Persona persona) {
        try {
             Propietario newPropietario = propietarioService.encontrarPropietarioPersona(persona);
             propietarioService.eliminarPropietario(newPropietario);
             personaService.eliminarPersona(persona);
             String mensaje = "Se ha eliminado un propietario correctamente.";
             bitacoraService.registrarAccion("Eliminar propietario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el propietario.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/ObtenerPropietario/{id}")
    public ResponseEntity<Object> ObtenerPropietario(@PathVariable Long id) {
        Persona persona = personaService.encontrarPersona(id);
        Propietario propietario = propietarioService.encontrarPropietarioPersona(persona);
        if (propietario != null && persona != null) {
            Map<String, Object> entidadesMap = new HashMap<>();
            entidadesMap.put("propietario", propietario);
            entidadesMap.put("persona", persona);
            return ResponseEntity.ok(entidadesMap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ActualizarPropietario")
    public ResponseEntity ActualizarPropietario( Propietario propietario, Persona persona, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            propietario.setPersona(persona);
            propietarioService.actualizarPropietario(propietario);
            personaService.actualizarPersona(persona);
            String mensaje = "Se ha actualizado el propietario correctamente.";
            bitacoraService.registrarAccion("Actualizar propietario");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar el propietario.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/AgregarCorreo")
    public ResponseEntity agregarCorreoPropietario(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("tipo") String tipoCorreo,
            @RequestParam("correo") String correo,
            @RequestParam("idPropietario") Long idPropietario) {
        try {
            if(correoService.encontrarCorreo(correo)== null){
                Correo newCorreo = new Correo();
                newCorreo.setTipo(tipoCorreo);
                newCorreo.setCorreo(correo);
                newCorreo.setIdPropietario(idPropietario);
                correoService.agregarC(newCorreo);
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
    
    @PostMapping("/EliminarCorreo/{idCorreo}")
    public ResponseEntity eliminarCorreoPropietario(Correo correo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            correoService.eliminarC(correo);
            String mensaje = "Se ha eliminado el correo correctamente.";
            bitacoraService.registrarAccion("Eliminar correo del propietario");
            return ResponseEntity.ok(mensaje);
        }catch(Exception e) {
            String error = "Ha ocurrido un error al eliminar el correo.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/AgregarTelefono")
    public ResponseEntity agregarTelefonoPropietario(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("tipo") String tipoTelefono,
            @RequestParam("telefono") String telefono,
            @RequestParam("idPropietario") Long idPropietario) {
        try {
            if(telefonoService.encontrarTelefono(telefono)== null){
                Telefono newTelefono = new Telefono();
                newTelefono.setTipo(tipoTelefono);
                newTelefono.setTelefono(telefono);
                newTelefono.setIdPropietario(idPropietario);
                telefonoService.agregarT(newTelefono);
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
    
    @PostMapping("/EliminarTelefono/{idTelefono}")
    public ResponseEntity eliminarTelefonoPropietario(Telefono telefono, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            telefonoService.eliminarT(telefono);
            String mensaje = "Se ha eliminado el teléfono correctamente.";
            bitacoraService.registrarAccion("Eliminar teléfono del propietario");
            return ResponseEntity.ok(mensaje);
        } catch(Exception e) {
            String error = "Ha ocurrido un error al eliminar el teléfono.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/AgregarReferencia")
    public ResponseEntity agregarReferenciaPropietario(HttpServletRequest request, RedirectAttributes redirectAttributes,
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
    
    @PostMapping("/EliminarReferencia/{idReferencia}")
    public ResponseEntity eliminarCorreoPropietario(Referencia referencia, HttpServletRequest request, RedirectAttributes redirectAttributes) {
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
    
    @PostMapping("/AgregarDocumento")
    public ResponseEntity agregarDocumentoPropietario(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("nombre") String nombre,
            @RequestParam("documento") MultipartFile documento,
            @RequestParam("idPropietario") Long idPropietario) {
        try {
            Documento newDocumento = new Documento();
            
            byte[] fileBytes = documento.getBytes();
            Blob documentoConvertido = new javax.sql.rowset.serial.SerialBlob(fileBytes);
            
            Propietario newPropietario = propietarioService.encontrarPropietario(idPropietario);
            
            newDocumento.setNombre(nombre);
            newDocumento.setDocumento(documentoConvertido);
            newDocumento.setIdListDocumento(newPropietario.getIdDocumento());
            documentoService.agregarDocumento(newDocumento);
            String mensaje = "Se ha agregado el documento correctamente.";
            bitacoraService.registrarAccion("Agregar documento del propietario");
            return ResponseEntity.ok(mensaje);
        } catch(Exception e) {
            String error = "Ha ocurrido un error al agregar el documento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/EliminarDocumento/{idDocumento}")
    public ResponseEntity eliminarDocumentoPropietario(Documento documento, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            documentoService.eliminarDocumento(documento);
            String mensaje = "Se ha eliminado el documento correctamente.";
            bitacoraService.registrarAccion("Eliminar documento del propietario");
            return ResponseEntity.ok(mensaje);
        }catch(Exception e) {
            String error = "Ha ocurrido un error al eliminar el documento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/VerDocumento/{IdDocumento}")
    public ResponseEntity <byte[]> mostrarDocumentoPDF(@PathVariable("IdDocumento") Long id) {
        Documento archivo = new Documento();
        archivo.setIdDocumento(id);
        Documento archivoExistente = documentoService.encontrarDoc(archivo);

        Blob pdfBlob = archivoExistente.getDocumento();
        byte[] pdfBytes;

        try {
            if (pdfBlob != null && pdfBlob.length() > 0) {
                pdfBytes = pdfBlob.getBytes(1, (int) pdfBlob.length());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.add("Content-Disposition", "inline; filename=" + archivoExistente.getNombre().replace(" ","_") + ".pdf");
                bitacoraService.registrarAccion("Ver documento propietario");
                return new ResponseEntity <>(pdfBytes, headers, HttpStatus.OK);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
    
    public List<String> listarTiposCorreos() {
        List<String> tiposCorreos = Arrays.asList("Trabajo", "Privado","Personal","Institucional");
        return tiposCorreos;
    }
    
    public List<String> listarTipoTelefono() {
        List<String> tipoTelefono = Arrays.asList("Casa", "Oficina","Fijo", "Móvil");
        return tipoTelefono;
    }
}
