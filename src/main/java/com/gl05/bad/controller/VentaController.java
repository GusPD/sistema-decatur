package com.gl05.bad.controller;

import com.gl05.bad.domain.AsignacionPropietario;
import com.gl05.bad.domain.AsignacionVisitante;
import com.gl05.bad.domain.Documento;
import com.gl05.bad.domain.Persona;
import com.gl05.bad.domain.Propietario;
import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Terreno;
import com.gl05.bad.domain.Venta;
import com.gl05.bad.domain.Visitante;
import com.gl05.bad.domain.VistaVentasActiva;
import com.gl05.bad.servicio.AsigPropietarioVentaService;
import com.gl05.bad.servicio.AsignacionVisitanteService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.DocumentoService;
import com.gl05.bad.servicio.PersonaService;
import com.gl05.bad.servicio.PropietarioService;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.TerrenoService;
import com.gl05.bad.servicio.VentaService;
import com.gl05.bad.servicio.VisitanteService;
import com.gl05.bad.servicio.VistaVentasActivaService;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private VistaVentasActivaService vistaVentasActivaService;
    
    @Autowired
    private TerrenoService terrenoService;
    
    @Autowired
    private DocumentoService documentoService;
    
    @Autowired
    private PersonaService personaService;
    
    @Autowired
    private PropietarioService propietarioService;
    
    @Autowired
    private VisitanteService visitanteService;
    
    @Autowired
    private AsigPropietarioVentaService asigPropietarioVentaService;
    
    @Autowired
    private AsignacionVisitanteService asigVisitanteService;
    
    @Autowired
    private ProyectoService proyectoService;
    
    //Función que redirige a la vista de las ventas del terreno
    @GetMapping("/Ventas/{idTerreno}")
    public String mostrarVentas(Model model, Terreno terreno) {
        model.addAttribute("pageTitle", "Ventas");
        Terreno terrenoEncontrado = terrenoService.encontrar(terreno.getIdTerreno());
        model.addAttribute("terreno", terrenoEncontrado);
        double prima = 0;
        Proyecto proyecto = terrenoEncontrado.getProyecto();
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("valorPrima", prima);
        return "/Terreno/VentasTerreno";
    }
    
    //Función que obtiene las ventas del terreno
    @GetMapping("/ventas/data/{idTerreno}")
    @ResponseBody
    public DataTablesOutput<Venta> GetVentas(@Valid DataTablesInput input, @PathVariable Long idTerreno) {
        return ventaService.listarVentas(input, idTerreno);
    }
    
    //Función que redirige a la vista de las ventas activas del proyecto
    @GetMapping("/VentasActivas/{idProyecto}")
    public String mostrarVentasActivas(Model model, Proyecto proyecto) {
        model.addAttribute("pageTitle", "Ventas Activas");
        Proyecto newProyecto = proyectoService.encontrar(proyecto.getIdProyecto());
        model.addAttribute("proyecto", newProyecto);
        return "/Proyecto/VentasActivasProyecto";
    }
    
    //Función que obtiene las ventas activas del proyecto
    @GetMapping("/ventasActiva/data/{idProyecto}")
    @ResponseBody
    public DataTablesOutput<VistaVentasActiva> GetVentasActivas(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return vistaVentasActivaService.listarTerrenos(input, idProyecto);
    }
    
    //Función que redirige a la vista de la información de la venta  
    @GetMapping("/InformacionVenta/{idVenta}")
    public String mostrarInformacionVenta(Model model, Venta venta) {
        model.addAttribute("pageTitle", "Venta");
        Venta ventaEncontrada = ventaService.encontrar(venta.getIdVenta());
        Terreno terrenoEncontrado = ventaEncontrada.getTerreno();
        double prima = 0;
        Proyecto proyecto = terrenoEncontrado.getProyecto();
        
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("terreno", terrenoEncontrado);
        model.addAttribute("venta", ventaEncontrada);
        model.addAttribute("valorPrima", prima);
        return "/Terreno/InformacionGeneral/ventaInformacion";
    }
    
    //Función que obtiene los propietarios de la venta
    @GetMapping("/propietarioVenta/data/{idVenta}")
    @ResponseBody
    public DataTablesOutput<AsignacionPropietario> GetPropietarios(@Valid DataTablesInput input, @PathVariable Long idVenta) {
        return asigPropietarioVentaService.listarPropietariosVenta(input, idVenta);
    }
    
    //Función que redirige a la vista de los propietarios de la venta
    @GetMapping("/PropietariosVenta/{idVenta}")
    public String mostrarPropietariosVenta(Model model, Venta venta) {
        model.addAttribute("pageTitle", "Venta");
        Venta ventaEncontrada = ventaService.encontrar(venta.getIdVenta());
        Terreno terrenoEncontrado = ventaEncontrada.getTerreno();
        Proyecto proyecto = terrenoEncontrado.getProyecto();
        
        List<Propietario> listaPropietarios = propietarioService.listaPropietarios();
        List<AsignacionPropietario> listaAsignaciones = asigPropietarioVentaService.listaAsignacion();
        List<Propietario> propietariosNoVenta = new ArrayList();
        boolean existePropietarioAsignado = false;
        for (var propietario : listaPropietarios) {
            for (var asignacion : listaAsignaciones) {
                if(Objects.equals(propietario.getIdPropietario(), asignacion.getPropietario().getIdPropietario()) && Objects.equals(asignacion.getVenta().getIdVenta(), ventaEncontrada.getIdVenta())){
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
        
        List<AsignacionPropietario> propietariosSeleccionados = new ArrayList();
        for (var propietario : propietarios) {
            if(Objects.equals(propietario.getEstado(), "Seleccionado")){
                propietariosSeleccionados.add(propietario);
            }
        }
        
        model.addAttribute("consumidorFinal", propietariosSeleccionados);
        model.addAttribute("propietariosAsignados", propietarios);
        model.addAttribute("propietariosNoVenta", propietariosNoVenta);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("terreno", terrenoEncontrado);
        model.addAttribute("venta", ventaEncontrada);
        return "/Terreno/InformacionGeneral/ventaPropietarios";
    }
    
    //Función para agregar un propietario a la venta
    @PostMapping("/AgregarPropietarioVenta")
    public ResponseEntity AgregarPropietarioVenta(@RequestParam("idVenta") Long idVenta, @RequestParam("estadoP") String estado,  @RequestParam("nombreP") String nombre, Propietario propietario, Persona persona, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            if(personaService.encontrarDui(persona.getDui())== null){
                persona.setNombre(nombre);
                personaService.agregar(persona);
                Persona newPersona = personaService.encontrarDui(persona.getDui());
                propietario.setPersona(newPersona);
                propietarioService.agregar(propietario);
                Venta venta= ventaService.encontrar(idVenta);
                AsignacionPropietario newAsignacionVenta = new AsignacionPropietario();
                newAsignacionVenta.setPropietario(propietario);
                newAsignacionVenta.setVenta(venta);
                newAsignacionVenta.setEstado(estado);
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
    
    //Función para seleccionar un propietario de otra venta a la venta    
    @PostMapping("/SeleccionarPropietariosVenta")
    public ResponseEntity SeleccionarPropietariosVenta(@RequestParam("idVenta") Long idVenta, @RequestParam("estadoS") String estado, @RequestParam("propietarios") List<Long> propietarios, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Venta venta= ventaService.encontrar(idVenta);
            for (var idpropietario : propietarios) {
                Propietario propietario = propietarioService.encontrar(idpropietario);
                AsignacionPropietario newAsignacionVenta = new AsignacionPropietario();
                newAsignacionVenta.setPropietario(propietario);
                newAsignacionVenta.setVenta(venta);
                newAsignacionVenta.setEstado(estado);
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

    //Función para eliminar un propietario de la venta
    @PostMapping("/EliminarPropietarioVenta/{idAsignacion}")
    public ResponseEntity EliminarPropietarioVenta(AsignacionPropietario asignacion) {
        try {
             asigPropietarioVentaService.eliminar(asignacion);
             String mensaje = "Se ha eliminado un propietario de la venta correctamente.";
             bitacoraService.registrarAccion("Eliminar propietario venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el propietario de la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función que obtiene los trabajadores de la venta
    @GetMapping("/trabajadorVenta/data/{idVenta}")
    @ResponseBody
    public DataTablesOutput<AsignacionVisitante> GetTrabjadoress(@Valid DataTablesInput input, @PathVariable Long idVenta) {
        return asigVisitanteService.listarTrabajadoresVenta(input, idVenta);
    }
    
    //Función que redirige a la vista de los trabajadores de la venta
    @GetMapping("/TrabajadoresVenta/{idVenta}")
    public String mostrarTrabajadoresVenta(Model model, Venta venta) {
        model.addAttribute("pageTitle", "Venta");
        Venta ventaEncontrada = ventaService.encontrar(venta.getIdVenta());
        Terreno terrenoEncontrado = ventaEncontrada.getTerreno();
        Proyecto proyecto = terrenoEncontrado.getProyecto();
        
        List<Visitante> listaVisitantes = visitanteService.listaVisitantes();
        List<AsignacionVisitante> listaAsignacionesVisitante = asigVisitanteService.listaAsignacionVisitantes();
        List<Visitante> visitantesNoVenta = new ArrayList();
        boolean existeVisitanteAsignado = false;
        for (var visitante : listaVisitantes) {
            for (var asignacion : listaAsignacionesVisitante) {
                if(Objects.equals(visitante.getIdVisitante(), asignacion.getVisitante().getIdVisitante()) && Objects.equals(asignacion.getVenta().getIdVenta(), ventaEncontrada.getIdVenta())){
                    existeVisitanteAsignado = true;
                }
            }
            if(existeVisitanteAsignado==false){
                visitantesNoVenta.add(visitante);
            }else{
                existeVisitanteAsignado = false;
            }
        }
        
        List<AsignacionVisitante> trabajadores = new ArrayList();
        for (var trabajador : listaAsignacionesVisitante) {
            if(Objects.equals(trabajador.getVenta().getIdVenta(), venta.getIdVenta())){
                trabajadores.add(trabajador);
            }
        }
        
        model.addAttribute("trabajadoresAsignados", trabajadores);
        model.addAttribute("trabajadoresNoVenta", visitantesNoVenta);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("terreno", terrenoEncontrado);
        model.addAttribute("venta", ventaEncontrada);
        return "/Terreno/InformacionGeneral/ventaTrabajadores";
    }
    
    //Función para agregar un trabajador a la venta
    @PostMapping("/AgregarTrabajadorVenta")
    public ResponseEntity AgregarTrabajadorVenta(@RequestParam("idVenta") Long idVenta, Visitante visitante, Persona persona, HttpServletRequest request, RedirectAttributes redirectAttributes) {
       try {
            if(personaService.encontrarDui(persona.getDui())== null){
                personaService.agregar(persona);
                Persona newPersona = personaService.encontrarDui(persona.getDui());
                visitante.setPersona(newPersona);
                visitanteService.agregar(visitante);
                Venta venta= ventaService.encontrar(idVenta);
                AsignacionVisitante newAsignacionVenta = new AsignacionVisitante();
                newAsignacionVenta.setVisitante(visitante);
                newAsignacionVenta.setVenta(venta);
                asigVisitanteService.agregar(newAsignacionVenta);
                String mensaje = "Se ha agregado un trabajador a la venta.";
                bitacoraService.registrarAccion("Agregar trabajador venta");
                return ResponseEntity.ok(mensaje);
            }else{
                String error = "Ya se encuentra registrado el trabajador.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el trabajador a la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función para seleccionar un trabajador de otra venta a la venta
    @PostMapping("/SeleccionarTrabajadoresVenta")
    public ResponseEntity SeleccionarTrabajadoresVenta(@RequestParam("idVenta") Long idVenta, @RequestParam("trabajadores") List<Long> trabajadores, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Venta venta= ventaService.encontrar(idVenta);
            for (var idtrabajador : trabajadores) {
                Visitante visitante = visitanteService.encontrar(idtrabajador);
                AsignacionVisitante newAsignacionVenta = new AsignacionVisitante();
                newAsignacionVenta.setVisitante(visitante);
                newAsignacionVenta.setVenta(venta);
                asigVisitanteService.agregar(newAsignacionVenta);
            }            
            String mensaje = "Se ha agregado un trabajador a la venta.";
            bitacoraService.registrarAccion("Agregar trabajador venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar el trabajador a la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función para eliminar un trabajador de la venta
    @PostMapping("/EliminarTrabajadorVenta/{idAsignacion}")
    public ResponseEntity EliminarTrabajadorVenta(AsignacionVisitante asignacion) {
        try {
             asigVisitanteService.eliminar(asignacion);
             String mensaje = "Se ha eliminado un trabajador de la venta correctamente.";
             bitacoraService.registrarAccion("Eliminar trabajador venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el trabajador de la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función que obtiene los documentos de la venta
    @GetMapping("/documentoVenta/data/{idListDocumento}")
    @ResponseBody
    public DataTablesOutput<Documento> GetDocumentos(@Valid DataTablesInput input, @PathVariable Integer idListDocumento) {
        return documentoService.listarDocumentos(input, idListDocumento);
    }
    
    //Función que redirige a la vista de los documentos de la venta
    @GetMapping("/DocumentosVenta/{idVenta}")
    public String mostrarDocumentoVenta(Model model, Venta venta) {
        model.addAttribute("pageTitle", "Venta");
        Venta ventaEncontrada = ventaService.encontrar(venta.getIdVenta());
        Terreno terrenoEncontrado = ventaEncontrada.getTerreno();
        Proyecto proyecto = terrenoEncontrado.getProyecto();
        List<Documento> listaDocumentos = documentoService.listarDocumentos();
        List<Documento> documentosVenta = new ArrayList();
        for (var documento : listaDocumentos) {
            if(Objects.equals(documento.getIdListDocumento(), ventaEncontrada.getIdListDocumento())){
                documentosVenta.add(documento);
            }
        }
        model.addAttribute("documentos", documentosVenta);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("terreno", terrenoEncontrado);
        model.addAttribute("venta", ventaEncontrada);
        return "/Terreno/InformacionGeneral/ventaDocumentos";
    }
    
    //Función que agrega un documento de la venta
    @PostMapping("/AgregarDocumentoVenta")
    public ResponseEntity agregarDocumentoVenta(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("nombre") String nombre,
            @RequestParam("documento") MultipartFile documento,
            @RequestParam("idVenta") Long idVenta) {
        try {
            Documento newDocumento = new Documento();
            
            byte[] fileBytes = documento.getBytes();
            Blob documentoConvertido = new javax.sql.rowset.serial.SerialBlob(fileBytes);
            
            Venta newVenta = ventaService.encontrar(idVenta);
            
            newDocumento.setNombre(nombre);
            newDocumento.setDocumento(documentoConvertido);
            newDocumento.setIdListDocumento(newVenta.getIdListDocumento());
            documentoService.agregar(newDocumento);
            String mensaje = "Se ha agregado el documento correctamente.";
            bitacoraService.registrarAccion("Agregar documento de la venta");
            return ResponseEntity.ok(mensaje);
        } catch(Exception e) {
            String error = "Ha ocurrido un error al agregar el documento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función que elimina un documento de la venta
    @PostMapping("/EliminarDocumentoVenta/{idDocumento}")
    public ResponseEntity eliminarDocumentoVenta(Documento documento, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            documentoService.eliminar(documento);
            String mensaje = "Se ha eliminado el documento correctamente.";
            bitacoraService.registrarAccion("Eliminar documento de la venta");
            return ResponseEntity.ok(mensaje);
        }catch(Exception e) {
            String error = "Ha ocurrido un error al eliminar el documento.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    //Función para visualizar un documento de la venta
    @GetMapping("/DocumentoVenta/{IdDocumento}")
    public ResponseEntity <byte[]> mostrarDocumentoPDF(@PathVariable("IdDocumento") Long id) {
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
                bitacoraService.registrarAccion("Ver documento venta");
                return new ResponseEntity <>(pdfBytes, headers, HttpStatus.OK);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
    
    //Función que redirige a la vista de la facturación de la venta
    @GetMapping("/FacturacionVenta/{idVenta}")
    public String mostrarFacturacionVenta(Model model, Venta venta) {
        model.addAttribute("pageTitle", "Venta");
        Venta ventaEncontrada = ventaService.encontrar(venta.getIdVenta());
        Terreno terrenoEncontrado = ventaEncontrada.getTerreno();
        Proyecto proyecto = terrenoEncontrado.getProyecto();
        
        List<AsignacionPropietario> listaAsignaciones = asigPropietarioVentaService.listaAsignacion();        
        List<AsignacionPropietario> propietarios = new ArrayList();
        for (var propietario : listaAsignaciones) {
            if(Objects.equals(propietario.getVenta().getIdVenta(), venta.getIdVenta())){
                propietarios.add(propietario);
            }
        }
        List<AsignacionPropietario> propietariosSeleccionados = new ArrayList();
        for (var propietario : propietarios) {
            if(Objects.equals(propietario.getEstado(), "Seleccionado")){
                propietariosSeleccionados.add(propietario);
            }
        }
        
        model.addAttribute("consumidorFinal", propietariosSeleccionados);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("terreno", terrenoEncontrado);
        model.addAttribute("venta", ventaEncontrada);
        return "/Terreno/InformacionGeneral/ventaFacturacion";
    }

    //Función que agrega un venta a la base de datos
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
            Terreno terreno = terrenoService.encontrar(idTerreno);
            venta.setTerreno(terreno);
            ventaService.agregar(venta);
            String mensaje = "Se ha agregado una venta.";
            bitacoraService.registrarAccion("Agregar venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ocurrió un error al agregar la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que elimina un venta de la base de datos
    @PostMapping("/EliminarVenta/{idVenta}")
    public ResponseEntity EliminarVenta(Venta venta) {
        try {
            Venta newVenta = ventaService.encontrar(venta.getIdVenta());
            ventaService.eliminar(newVenta);
            List<Venta> listadoVentas = ventaService.listaVentas();
            List<Venta> listadoVentasTerreno = new ArrayList();
            if(!listadoVentas.isEmpty()){
                for (Venta valorVenta : listadoVentas){
                    if(valorVenta.getTerreno().getIdTerreno().equals(newVenta.getTerreno().getIdTerreno())){
                        listadoVentasTerreno.add(valorVenta);
                    }
                }
            }
            if(!listadoVentasTerreno.isEmpty()){
                Venta ultimaVenta= listadoVentasTerreno.get(listadoVentasTerreno.size() - 1);
                if (ultimaVenta != null) {
                    ultimaVenta.setEstado("Activo");
                }
            }
            String mensaje = "Se ha eliminado una venta correctamente.";
            bitacoraService.registrarAccion("Eliminar venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar la venta";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Función que obtiene una venta de la base de datos
    @GetMapping("/ObtenerVenta/{id}")
    public ResponseEntity<Venta> ObtenerVenta(@PathVariable Long id) {
        Venta venta = ventaService.encontrar(id);
        if (venta != null) {
            return ResponseEntity.ok(venta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Función que actualiza una venta de la base de datos
    @PostMapping("/ActualizarVenta/{idTerreno}")
    public ResponseEntity ActualizarVenta(@PathVariable("idTerreno") Long idTerreno, Venta venta, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            Terreno terreno = terrenoService.encontrar(idTerreno);
            venta.setTerreno(terreno);
            ventaService.actualizar(venta);
            String mensaje = "Se ha actualizado la venta correctamente.";
            bitacoraService.registrarAccion("Actualizar venta");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al actualizar la venta.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
