package com.gl05.bad.controller;

import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.Correo;
import com.gl05.bad.domain.Documento;
import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Propietario;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Referencia;
import com.gl05.bad.domain.Telefono;
import com.gl05.bad.domain.Terreno;
import com.gl05.bad.domain.Venta;
import com.gl05.bad.servicio.AsigPropietarioVentaService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CorreoService;
import com.gl05.bad.servicio.DocumentoService;
import com.gl05.bad.servicio.PersonaService;
import com.gl05.bad.servicio.PropietarioService;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.ReferenciaService;
import com.gl05.bad.servicio.TelefonoService;
import com.gl05.bad.servicio.TerrenoService;
import com.gl05.bad.servicio.VentaService;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class VentaController {
    
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private TerrenoService terrenoService;
    
    @Autowired
    private DocumentoService documentoService;
    
    @Autowired
    private PersonaService personaService;
    
    @Autowired
    private PropietarioService propietarioService;
    
    @Autowired
    private AsigPropietarioVentaService asigPropietarioVentaService;
    
    @Autowired
    private TelefonoService telefonoService;
    
    @Autowired
    private CorreoService correoService;
    
    @Autowired
    private ReferenciaService referenciaService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    @GetMapping("/Ventas/{idTerreno}")
    public String mostrarVentas(Model model, Terreno terreno) {
        model.addAttribute("pageTitle", "Ventas");
        Terreno terrenoEncontrado = terrenoService.encontrarTerreno(terreno.getIdTerreno());
        model.addAttribute("terreno", terrenoEncontrado);
        double prima = 0;
        Proyecto proyecto = terrenoEncontrado.getProyecto();
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("valorPrima", prima);
        return "/Terreno/VentasTerreno";
    }
    
    @GetMapping("/Venta/{idVenta}")
    public String mostrarVenta(Model model, Venta venta) {
        model.addAttribute("pageTitle", "Venta");
        Venta ventaEncontrada = ventaService.encontrarVenta(venta.getIdVenta());
        Terreno terrenoEncontrado = ventaEncontrada.getTerreno();
        double prima = 0;
        Proyecto proyecto = terrenoEncontrado.getProyecto();
        
        //Manejo de documentos
        List<Documento> listaDocumentos = documentoService.listarDocumentos();
        List<Documento> documentosVenta = new ArrayList();
        for (var documento : listaDocumentos) {
            if(Objects.equals(documento.getIdListDocumento(), ventaEncontrada.getIdListDocumento())){
                documentosVenta.add(documento);
            }
        }
        
        //Manejo de propietarios
        List<Propietario> listaPropietarios = propietarioService.listaPropietarios();
        List<AsignacionPropietario> listaAsignaciones = asigPropietarioVentaService.listaAsignacion();
        List<Propietario> propietariosNoVenta = new ArrayList();
        boolean existePropietarioAsignado = false;
        for (var propietario : listaPropietarios) {
            for (var asignacion : listaAsignaciones) {
                if(Objects.equals(propietario.getIdPropietario(), asignacion.getPropietario().getIdPropietario())){
                    existePropietarioAsignado = true;
                }
            }
            if(existePropietarioAsignado==false){
                propietariosNoVenta.add(propietario);
            }else{
                existePropietarioAsignado = false;
            }
        }
        
        List<AsignacionPropietario> propietarios = new ArrayList();
        for (var propietario : listaAsignaciones) {
            if(Objects.equals(propietario.getVenta().getIdVenta(), venta.getIdVenta())){
                propietarios.add(propietario);
            }
        }
        
        model.addAttribute("propietariosAsignados", propietarios);
        model.addAttribute("propietariosNoVenta", propietariosNoVenta);
        model.addAttribute("documentos", documentosVenta);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("terreno", terrenoEncontrado);
        model.addAttribute("venta", ventaEncontrada);
        model.addAttribute("valorPrima", prima);
        return "/Terreno/MostrarVentaTerreno";
    }
    
    @GetMapping("/ventas/data/{idTerreno}")
    @ResponseBody
    public DataTablesOutput<Venta> GetVentas(@Valid DataTablesInput input, @PathVariable Long idTerreno) {
        return ventaService.listarVentas(input, idTerreno);
    }

    @PostMapping("/AgregarVenta/{idTerreno}")
    public ResponseEntity AgregarVenta(@PathVariable("idTerreno") Long idTerreno,Venta venta, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            List<Venta> listadoVentas = ventaService.listaVentas();
            if(!listadoVentas.isEmpty()){
                for (Venta valorVenta : listadoVentas){
                    if(valorVenta.getTerreno().getIdTerreno().equals(idTerreno)){
                        valorVenta.setEstado("Inactivo");
                    }
                }
            }
            //Obtener el proyecto por ID
            Terreno terreno = terrenoService.encontrarTerreno(idTerreno);
            venta.setTerreno(terreno);
            ventaService.agregarVenta(venta);
            String mensaje = "Se ha agregado una venta.";
            bitacoraService.registrarAccion("Agregar venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/EliminarVenta/{idVenta}")
    public ResponseEntity EliminarVenta(Venta venta) {
        try {
            ventaService.eliminarVenta(venta);
             String mensaje = "Se ha eliminado una venta correctamente.";
             bitacoraService.registrarAccion("Eliminar venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar la venta";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/ObtenerVenta/{id}")
    public ResponseEntity<Venta> ObtenerVenta(@PathVariable Long id) {
        Venta venta = ventaService.encontrarVenta(id);
        if (venta != null) {
            return ResponseEntity.ok(venta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ActualizarVenta/{idTerreno}")
    public ResponseEntity ActualizarVenta(@PathVariable("idTerreno") Long idTerreno, Venta venta, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            //Obtener el proyecto por ID
            Terreno terreno = terrenoService.encontrarTerreno(idTerreno);
            venta.setTerreno(terreno);
            ventaService.actualizarVenta(venta);
            String mensaje = "Se ha actualizado la venta correctamente.";
            bitacoraService.registrarAccion("Actualizar venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/AgregarDocumentoVenta")
    public ResponseEntity agregarDocumentoVenta(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("nombre") String nombre,
            @RequestParam("documento") MultipartFile documento,
            @RequestParam("idVenta") Long idVenta) {
        try {
            Documento newDocumento = new Documento();
            
            byte[] fileBytes = documento.getBytes();
            Blob documentoConvertido = new javax.sql.rowset.serial.SerialBlob(fileBytes);
            
            Venta newVenta = ventaService.encontrarVenta(idVenta);
            
            newDocumento.setNombre(nombre);
            newDocumento.setDocumento(documentoConvertido);
            newDocumento.setIdListDocumento(newVenta.getIdListDocumento());
            documentoService.agregarDocumento(newDocumento);
            String mensaje = "Se ha agregado el documento correctamente.";
            bitacoraService.registrarAccion("Agregar documento de la venta");
            return ResponseEntity.ok(mensaje);
        } catch(Exception e) {
            String error = "Ha ocurrido un error al agregar el documento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/EliminarDocumentoVenta/{idDocumento}")
    public ResponseEntity eliminarDocumentoVenta(Documento documento, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            documentoService.eliminarDocumento(documento);
            String mensaje = "Se ha eliminado el documento correctamente.";
            bitacoraService.registrarAccion("Eliminar documento de la venta");
            return ResponseEntity.ok(mensaje);
        }catch(Exception e) {
            String error = "Ha ocurrido un error al eliminar el documento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/VerDocumentoVenta/{IdDocumento}")
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
                bitacoraService.registrarAccion("Ver documento venta");
                return new ResponseEntity <>(pdfBytes, headers, HttpStatus.OK);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/AgregarPropietarioVenta")
    public ResponseEntity AgregarPropietarioVenta(@RequestParam("idVenta") Long idVenta, Propietario propietario, Persona persona, HttpServletRequest request, RedirectAttributes redirectAttributes) {
       try {
            if(personaService.encontrarPersona(persona.getDui())== null){
                personaService.agregarPersona(persona);
                Persona newPersona = personaService.encontrarPersona(persona.getDui());
                propietario.setPersona(newPersona);
                propietarioService.agregarPropietario(propietario);
                Venta venta= ventaService.encontrarVenta(idVenta);
                AsignacionPropietario newAsignacionVenta = new AsignacionPropietario();
                newAsignacionVenta.setPropietario(propietario);
                newAsignacionVenta.setVenta(venta);
                asigPropietarioVentaService.agregar(newAsignacionVenta);
                String mensaje = "Se ha agregado un propietario a la venta.";
                bitacoraService.registrarAccion("Agregar propietario venta");
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "Ya se encuentra registrado el propietario.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el propietario a la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/SeleccionarPropietariosVenta")
    public ResponseEntity SeleccionarPropietariosVenta(@RequestParam("idVenta") Long idVenta, @RequestParam("propietarios") List<Long> propietarios, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Venta venta= ventaService.encontrarVenta(idVenta);
            for (var idpropietario : propietarios) {
                Propietario propietario = propietarioService.encontrarPropietario(idpropietario);
                AsignacionPropietario newAsignacionVenta = new AsignacionPropietario();
                newAsignacionVenta.setPropietario(propietario);
                newAsignacionVenta.setVenta(venta);
                asigPropietarioVentaService.agregar(newAsignacionVenta);
            }            
            String mensaje = "Se ha agregado un propietario a la venta.";
            bitacoraService.registrarAccion("Agregar propietario venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el propietario a la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/EliminarPropietarioVenta/{idPersona}")
    public ResponseEntity EliminarPropietarioVenta(Persona persona) {
        try {
             Propietario newPropietario = propietarioService.encontrarPropietarioPersona(persona);
             AsignacionPropietario asignacionPropietario=asigPropietarioVentaService.encontrarPropietario(newPropietario);
             asigPropietarioVentaService.eliminar(asignacionPropietario);
             String mensaje = "Se ha eliminado un propietario de la venta correctamente.";
             bitacoraService.registrarAccion("Eliminar propietario venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el propietario de la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/VerPropietarioVenta/{idProyecto}/{idPersona}")
    public String VerPropietarioVenta(Model model, Persona persona, Proyecto proyecto) {
        model.addAttribute("pageTitle", "Perfil Propietario");
        Persona newPersona = personaService.encontrarPersona(persona.getIdPersona());
        Propietario newPropietario = propietarioService.encontrarPropietarioPersona(newPersona);
        Proyecto newProyecto = proyectoService.encontrarProyecto(proyecto.getIdProyecto());
        
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
        
        model.addAttribute("proyecto", newProyecto);
        model.addAttribute("documentos", documentosPropietario);
        model.addAttribute("referencias", referenciasPropietario);
        model.addAttribute("correos", correosPropietario);
        model.addAttribute("tiposCorreo", tiposCorreo);
        model.addAttribute("telefonos", telefonosPropietario);
        model.addAttribute("tiposTelefonos", tiposTelefono);
        model.addAttribute("propietario", newPropietario);
        model.addAttribute("persona", newPersona);
        return "/Propietario/MostrarPropietarioProyecto";
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
