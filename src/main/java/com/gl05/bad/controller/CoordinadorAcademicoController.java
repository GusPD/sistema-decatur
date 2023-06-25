package com.gl05.bad.controller;

import com.gl05.bad.domain.AtestadoTa;
import com.gl05.bad.domain.CoordinadorAcademico;
import com.gl05.bad.domain.Correo;
import com.gl05.bad.domain.Documento;
import com.gl05.bad.domain.ListadoDocumentacionPersonal;
import com.gl05.bad.servicio.AtestadoTaService;
import com.gl05.bad.domain.Telefono;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.CoordinadorAcademicoService;
import com.gl05.bad.servicio.CorreoService;
import com.gl05.bad.servicio.DocumentoService;
import com.gl05.bad.servicio.PaisService;
import com.gl05.bad.servicio.TelefonoService;
import com.gl05.bad.servicio.UserService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class CoordinadorAcademicoController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private AtestadoTaService atestadoService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private CoordinadorAcademicoService coordinadorService;

    @Autowired
    private DocumentoService docService;
  
    @Autowired
    private PaisService paisService;
    
    @Autowired
    private CorreoService correoService;
    
    @Autowired
    private TelefonoService telefonoService;
  
    @GetMapping("/perfilCoordinadorAcademico/{idCoorAca}")
    public String perfilCoordinadorAcademico(Model model, CoordinadorAcademico coordinador) {
        model.addAttribute("pageTitle", "Perfil Coordinador Academico");
        
        var elemento = coordinadorService.encontrarCA(coordinador);
        
        //Manejo de documentacion personal
        ListadoDocumentacionPersonal ldp= new ListadoDocumentacionPersonal();
        ldp.setIdListDp(Long.valueOf(elemento.getIdListDp()));
        var documentos = docService.listarDocumentoPorListado(ldp);

        //Manejo de imagenes
        Blob imagenBlob = elemento.getFotografiaCa();
        String imagenBase64 = null;

        if (imagenBlob != null) {
            try {
                byte[] bytes = imagenBlob.getBytes(1, (int) imagenBlob.length());
                String base64Encoded = Base64Utils.encodeToString(bytes);
                bytes = null;
                imagenBase64 = new String(base64Encoded.getBytes(StandardCharsets.UTF_8));
                base64Encoded = "";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        //Manejo de atestados academicos
        List<String> tiposTitulos = listarTipoTitulos();
        var atestados = atestadoService.listarAtestados();
        List<AtestadoTa> atestadoCoordinador = new ArrayList();
        for (var a : atestados) {
            if(a.getIdListTa() == (int) elemento.getIdListTa()){
                atestadoCoordinador.add(a);
            }
        }
        
        //Manejo de correos
        var correos = correoService.listarCorreos();
        List<String> tiposCorreo = listarTiposCorreos();
        List<Correo> correosCoordinador = new ArrayList();
        for (var eCorreo : correos) {
            if(eCorreo.getIdListCorreo() == (int) elemento.getIdListCorreo()){
                correosCoordinador.add(eCorreo);
            }
        }
        
        //Manejo de telefonos
        var telefonos = telefonoService.listarTelefonos();
        List<String> tiposTelefono = listarTipoTelefono();
        List<Telefono> telefonosCoordinador = new ArrayList();
        for (var eTelefono: telefonos) {
            if(eTelefono.getIdListTelefono() == (int) elemento.getIdListTelefono()){
                telefonosCoordinador.add(eTelefono);
            }
        }
        
        //Se cargan para la edicion de la informacion general
        List<String> sexos = listarSexos();
        List<String> generos = listarGeneros();
        List<String> estadosCiviles = listarEstados();
        List<String> nacionalidades = listarNacionalidades();
        
        //Manejo de paises
        var paises = paisService.listarPaises();
        String paisSeleccionado="";
        if (elemento.getIdPais() != null) {
            for (var pais : paises) {
                if(pais.getIdPais() == (int) elemento.getIdPais()){
                    paisSeleccionado=pais.getNombrePais();
                }
            }
        }
        
        model.addAttribute("atestados", atestadoCoordinador);
        model.addAttribute("tiposTitulos", tiposTitulos);
        model.addAttribute("imagenBase64", imagenBase64);
        model.addAttribute("coordinadorCA", elemento);
        model.addAttribute("correos", correosCoordinador);
        model.addAttribute("tiposCorreo", tiposCorreo);
        model.addAttribute("telefonos", telefonosCoordinador);
        model.addAttribute("tiposTelefonos", tiposTelefono);
        model.addAttribute("paises", paises);
        model.addAttribute("sexos", sexos);
        model.addAttribute("generos", generos);
        model.addAttribute("estadosCiviles", estadosCiviles);
        model.addAttribute("nacionalidades", nacionalidades);
        model.addAttribute("paisSeleccionado", paisSeleccionado);
        model.addAttribute("documentos", documentos);
        return "/coordinadorAcademico/perfilCoordinadorAcademico";
    }

    @GetMapping("/gestionarCoordinadorAcademico")
    public String mostrarCoordinadoresAcademicos(Model model) {
        model.addAttribute("pageTitle", "Gestionar Coordinadores Académicos");
        return "/coordinadorAcademico/index";
    }
    
    @GetMapping("/coordinadores/data")
    @ResponseBody
    public DataTablesOutput<CoordinadorAcademico> getMaestrias(@Valid DataTablesInput input) {
      return coordinadorService.listarCoordinadorAcademico(input);
    }
 
    @PostMapping("/guardarCA")
    public String agregarCoordinadorAcademico(
        @RequestParam("codCa") String codCa,
        @RequestParam("nombresCa") String nombresCa,
        @RequestParam("apellidosCa") String apellidosCa,
        @RequestParam("email") String email,
        RedirectAttributes redirectAttributes) {

        try {
            if (userService.encontrarUsuarioPorUsername(codCa) != null) {
                redirectAttributes.addFlashAttribute("error", "Ya existe un usuario con el mismo código.");
                return "redirect:/gestionarCoordinadorAcademico";
            }
            if (userService.encontrarUsuarioPorEmail(email) != null) {
                redirectAttributes.addFlashAttribute("error", "Ya existe un usuario con el mismo correo electrónico.");
                return "redirect:/gestionarCoordinadorAcademico";
            }
            coordinadorService.proIsertarCA(codCa, nombresCa, apellidosCa, email);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha ingresado un coordinador.");
            
            bitacoraService.registrarAccion("Agregar coordinador académico");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error al agregar el coordinador académico.");
        }
        return "redirect:/gestionarCoordinadorAcademico";  
    }
    
    @PostMapping("/eliminarCoordinadorAcademico/{idCoorAca}")
    public ResponseEntity<String> eliminarCoordinadorAcademico(CoordinadorAcademico coordinador, RedirectAttributes redirectAttributes) {
        try {
            coordinadorService.eliminarCA(coordinador);
            String mensaje = "Se ha eliminado un Coordinador Académico.";
            bitacoraService.registrarAccion("Eliminar coordinador académico");
            return ResponseEntity.ok(mensaje);
        } catch(Exception e) {
          String error = "Ha ocurrido un error al eliminar el Coordinador Académico.";
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/actualizarFoto/{idCoorAca}")
    public String actualizarFoto(
        @RequestParam Map<String, MultipartFile> campos,
        @PathVariable("idCoorAca") Long idCoorAca,
        RedirectAttributes redirectAttributes) {
        CoordinadorAcademico coordinador = new CoordinadorAcademico();
        try {
            coordinador.setIdCoorAca(idCoorAca);
            for (Map.Entry<String, MultipartFile> entry : campos.entrySet()) {
                String nombreCampo = entry.getKey();
                MultipartFile campo = entry.getValue();

                byte[] fileBytes = campo.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(fileBytes);

                coordinadorService.actualizarCampo(coordinador, nombreCampo, blob);
            }

            redirectAttributes.addFlashAttribute("mensaje", "Se han actualizado los campos correctamente.");
            bitacoraService.registrarAccion("Actualizar foto del coordinador académico");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Sucedió un error al actualizar los campos.");
        }

        return "redirect:/perfilCoordinadorAcademico/" + coordinador.getIdCoorAca();  
    }

    
    @PostMapping("/actualizarDocumento/{idCoorAca}")
    public String actualizarDocumento(
        @RequestParam("tipoDocumento") String tipoDocumento,
        @RequestParam Map<String, MultipartFile> campos,
        @PathVariable("idCoorAca") Long idCoorAca,
        RedirectAttributes redirectAttributes) {
      
        Documento documento = new Documento();
        CoordinadorAcademico coordinador = new CoordinadorAcademico();
        CoordinadorAcademico coordinadorExistente = new CoordinadorAcademico();
        ListadoDocumentacionPersonal listaDoc = new ListadoDocumentacionPersonal();
        
        try {
            coordinador.setIdCoorAca(idCoorAca);
            coordinadorExistente=coordinadorService.encontrarCA(coordinador);
            listaDoc.setIdListDp(Long.valueOf(coordinadorExistente.getIdListDp()));
            
            documento.setIdListDp(listaDoc);
            documento.setTipoFile(tipoDocumento);
            for (Map.Entry<String, MultipartFile> entry : campos.entrySet()) {
              try{
                String tipoCampo = entry.getKey();
                MultipartFile campo = entry.getValue();

                byte[] fileBytes = campo.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(fileBytes);
                
                documento.setDocFile(blob);

                docService.agregarDocumento(documento);
                bitacoraService.registrarAccion("Actualizar documentación personal del coordinador académico");
              }catch(IOException e){
                redirectAttributes.addFlashAttribute("error", "El documento no cumple con las especificaciones dadas");
              }
            }
            redirectAttributes.addFlashAttribute("mensaje", "Se han actualizado sus documentos.");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Sucedió un error al subir el documento");
        }

        return "redirect:/perfilCoordinadorAcademico/" + coordinadorExistente.getIdCoorAca();  
    }

    @GetMapping("/eliminarDocumento/{idCoorAca}/{IdDocumento}")
    public String eliminarDocumento(
            Documento doc, 
            @PathVariable("idCoorAca") Long idCoorAca,
            RedirectAttributes redirectAttributes) {
        try {
            docService.eliminarDocumento(doc);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado el documento.");
            bitacoraService.registrarAccion("Eliminar documento personal del coordinador académico");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar el documento.");
        }
        return "redirect:/perfilCoordinadorAcademico/" + idCoorAca;
    }

    @GetMapping("/archivo/{IdDocumento}")
    public ResponseEntity <byte[]> mostrarArchivoPDF(@PathVariable("IdDocumento") Long id) {
        Documento archivo = new Documento();
        archivo.setIdDocumento(id);
        Documento archivoExistente = docService.encontrarDoc(archivo);

        Blob pdfBlob = archivoExistente.getDocFile();
        byte[] pdfBytes;

        try {
            if (pdfBlob != null && pdfBlob.length() > 0) {
                pdfBytes = pdfBlob.getBytes(1, (int) pdfBlob.length());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                
                bitacoraService.registrarAccion("Ver documento personal del coordinador académico");
                return new ResponseEntity <>(pdfBytes, headers, HttpStatus.OK);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/agregarTituloAcademicoCA/{idCoorAca}/{idListTa}")
    public String agregarTituloAcademicoCA(
        @RequestParam ("tipoAta") String tipoAta,
        @RequestParam ("nombreAta") String nombreAta,
        @RequestParam ("institucion") String institucion,
        @RequestParam ("anioTitulacion") Integer anioTitulacion,
        @RequestParam ("archivoAta") MultipartFile archivo,
        @PathVariable("idListTa") int idListTa, 
        @PathVariable("idCoorAca")int idCoorAca, 
        RedirectAttributes redirectAttributes) {
        
        AtestadoTa atestadoNew = new AtestadoTa();
        try {
            byte[] fileBytes = archivo.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(fileBytes);
            
            atestadoNew.setIdListTa(idListTa);
            atestadoNew.setTipoAta(tipoAta);
            atestadoNew.setNombreAta(nombreAta);
            atestadoNew.setInstitucion(institucion);
            atestadoNew.setAnioTitulacion(anioTitulacion);
            atestadoNew.setArchivoAta(blob);
            
            atestadoService.agregarAtestado(atestadoNew);
            bitacoraService.registrarAccion("Agregar título académico del coordinador académico");
            redirectAttributes.addFlashAttribute("mensaje", "Se ha ingresado un título académico.");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha sucedido un error, vuelva a intentarlo");
        }
        return "redirect:/perfilCoordinadorAcademico/" + idCoorAca;   
    }
  
    @PostMapping("/modificarTituloAcademicoCA/{idCoorAca}/{idAtestadoTa}")
    public String modificarTituloAcademicoCA(
        @RequestParam ("tipoAta") String tipoAta,
        @RequestParam ("nombreAta") String nombreAta,
        @RequestParam ("institucion") String institucion,
        @RequestParam ("anioTitulacion") Integer anioTitulacion,
        @RequestParam ("archivoAta") MultipartFile archivo,
        @PathVariable("idAtestadoTa") Long idAtestadoTa, 
        @PathVariable("idCoorAca")int idCoorAca, 
        RedirectAttributes redirectAttributes) {
        
        AtestadoTa atestadoActualizar = new AtestadoTa();
        atestadoActualizar.setIdAtestadoTa(idAtestadoTa);
        atestadoActualizar.setTipoAta(tipoAta);
        atestadoActualizar.setNombreAta(nombreAta);
        atestadoActualizar.setInstitucion(institucion);
        atestadoActualizar.setAnioTitulacion(anioTitulacion);
                   
        if(archivo == null || archivo.isEmpty()){
          atestadoActualizar.setArchivoAta(null);
          atestadoService.actualizarAtestado(atestadoActualizar);
          bitacoraService.registrarAccion("Actualizar título académico del coordinador académico");
          redirectAttributes.addFlashAttribute("mensaje", "Se ha actualizado un título académico.");
        }else{
          try {
            byte[] fileBytes = archivo.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(fileBytes);
            atestadoActualizar.setArchivoAta(blob);
            atestadoService.actualizarAtestado(atestadoActualizar);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha actualizado un título académico.");
            bitacoraService.registrarAccion("Actualizar título académico del coordinador académico");
          } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha sucedido un error al actualizar el titulo. Intentelo de nuevo");
          }
        }
        
        return "redirect:/perfilCoordinadorAcademico/" + idCoorAca;   
    }
    
    @GetMapping("/eliminarTituloAcademico/{idCoorAca}/{idAtestadoTa}")
    public String eliminarAtestadoTitulo(AtestadoTa atestado, @PathVariable("idCoorAca")int idCoorAca, RedirectAttributes redirectAttributes) {
        try {
            atestadoService.eliminarAtestado(atestado);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado el título académico.");
            bitacoraService.registrarAccion("Eliminar título académico del coordinador académico");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar el título académico.");
        }
        return "redirect:/perfilCoordinadorAcademico/" + idCoorAca;
    }
    
    @GetMapping("/archivoCA/tituloAcademico/{idAtestadoTa}")
    public ResponseEntity <byte[]> mostrarTituloAcademico(@PathVariable("idAtestadoTa") Long id) {
        AtestadoTa archivo = new AtestadoTa();
        archivo.setIdAtestadoTa(id);
        AtestadoTa archivoExistente = atestadoService.encontrarAtestado(archivo);

        Blob pdfBlob = archivoExistente.getArchivoAta();
        byte[] pdfBytes;

        try {
            if (pdfBlob != null && pdfBlob.length() > 0) {
                pdfBytes = pdfBlob.getBytes(1, (int) pdfBlob.length());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                archivoExistente=null;
                bitacoraService.registrarAccion("Ver título académico del coordinador académico");
                return new ResponseEntity <>(pdfBytes, headers, HttpStatus.OK);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }
  
    @PostMapping("/ActualizarCoordinadorAcademico/{idCoorAca}")
    public String actualizarCoordinadorAcademico (
            CoordinadorAcademico coordinadorAcademico, 
            @PathVariable("idCoorAca") int idCoorAca, 
            RedirectAttributes redirectAttributes) throws ParseException{
        
        try {
            //Arreglando formato del DUI, NIT, NUP, DOC PERSONAL, PASAPORTE
            String dui=coordinadorAcademico.getDuiCa();
            dui=dui.replace("-","");
            coordinadorAcademico.setDuiCa(dui);
            String nit=coordinadorAcademico.getNitCa();
            nit=nit.replace("-","");
            coordinadorAcademico.setNitCa(nit);
            String nup=coordinadorAcademico.getNupCa();
            coordinadorAcademico.setNupCa(nup);
            String pasaporte=coordinadorAcademico.getPasaporteCa();
            pasaporte=pasaporte.replace("-","");
            pasaporte=pasaporte.replace("_","");
            coordinadorAcademico.setPasaporteCa(pasaporte);
            String docPersonal=coordinadorAcademico.getDocPersonalCa();
            docPersonal=docPersonal.replace("-","");
            docPersonal=docPersonal.replace("_","");
            coordinadorAcademico.setDocPersonalCa(docPersonal);
            //Convirtiendo el pais en entero
            int idPais = (int)(coordinadorAcademico.getIdPais());
            coordinadorAcademico.setIdPais(idPais);
            coordinadorService.actualizarCA(coordinadorAcademico);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha actualizado la información general del coordinador académico.");
            bitacoraService.registrarAccion("Actualizar información del coordinador académico");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se actualizó la información general del coordinador académico.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/perfilCoordinadorAcademico/{idCoorAca}").buildAndExpand(idCoorAca).toUriString();
        return "redirect:" + redirectUrl;  
    } 
    
    @PostMapping("/AgregarCorreoCoordinadorAcademico/{idCoorAca}/{idListCorreo}")
    public String agregarCorreoCoordinadorAcademico(
        @RequestParam("tipoCorreo") String tipoCorreo,
        @RequestParam("correo") String correo,
        @PathVariable("idListCorreo") int idListCorreo, 
        @PathVariable("idCoorAca")int idCoorAca, 
        RedirectAttributes redirectAttributes) {
        try {
            Correo correoNew = new Correo();
            correoNew.setIdListCorreo(idListCorreo);
            correoNew.setCorreo(correo);
            correoNew.setTipoCorreo(tipoCorreo);
            correoService.agregarC(correoNew);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha ingresado un correo.");
            bitacoraService.registrarAccion("Agregar correo del coordinador académico");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ya se encuentra registrado el correo.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/perfilCoordinadorAcademico/{idCoorAca}").buildAndExpand(idCoorAca).toUriString();
        return "redirect:" + redirectUrl;   
    }
    
    @PostMapping("/AgregarTelefonoCoordinadorAcademico/{idCoorAca}/{idListTelefono}")
    public String agregarTelefonoCoordinadorAcademico(
        @RequestParam("tipoTelefono") String tipoTelefono,
        @RequestParam("numero") String numero,
        @PathVariable("idListTelefono") int idListTelefono, 
        @PathVariable("idCoorAca")int idCoorAca, 
        RedirectAttributes redirectAttributes) {
        try {
            Telefono telefonoNew = new Telefono();
            telefonoNew.setIdListTelefono(idListTelefono);
            telefonoNew.setNumero(numero);
            telefonoNew.setTipoTelefono(tipoTelefono);
            telefonoService.agregarT(telefonoNew);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha ingresado un telefono.");
            bitacoraService.registrarAccion("Agregar teléfono del coordinador académico");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ya se encuentra registrado el telefono.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/perfilCoordinadorAcademico/{idCoorAca}").buildAndExpand(idCoorAca).toUriString();
        return "redirect:" + redirectUrl;   
    }
    
    @GetMapping("/EliminarCorreoCoordinadorAcademico/{idCorreo}/{idCoorAca}")
    public String eliminarCorreoCoordinadorAcademico(Correo correo, @PathVariable("idCoorAca")int idCoorAca, RedirectAttributes redirectAttributes) {
        try {
            correoService.eliminarC(correo);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado el correo.");
            bitacoraService.registrarAccion("Eliminar correo del coordinador académico");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar el correo.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/perfilCoordinadorAcademico/{idCoorAca}").buildAndExpand(idCoorAca).toUriString();
        return "redirect:" + redirectUrl;
    }
    
    @GetMapping("/EliminarTelefonoCoordinadorAcademico/{idTelefono}/{idCoorAca}")
    public String eliminarTelefonoCoordinadorAcademico(Telefono telefono, @PathVariable("idCoorAca")int idCoorAca, RedirectAttributes redirectAttributes) {
        try {
            telefonoService.eliminarT(telefono);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado el telefono.");
            bitacoraService.registrarAccion("Eliminar teléfono del coordinador académico");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar el telefono.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/perfilCoordinadorAcademico/{idCoorAca}").buildAndExpand(idCoorAca).toUriString();
        return "redirect:" + redirectUrl;
    }
    
    public List<String> listarGeneros() {
        List<String> generos = Arrays.asList("Masculino", "Femenino","LGBTIQ+","Prefiero no decirlo");        
        return generos;
    }
    public List<String> listarSexos() {
        List<String> sexos= Arrays.asList("Hombre", "Mujer");  
        return sexos;
    }
    public List<String> listarEstados() {
        List<String> estados = Arrays.asList("Soltero", "Casado","Divorciado","Separado","Acompañado","Unión libre");  
        return estados;
    }
    public List<String> listarNacionalidades() {
        List<String> estados = Arrays.asList("Nacimiento", "Naturalización", "Residencia");
        return estados;
    }
    public List<String> listarTiposCorreos() {
        List<String> tiposCorreos = Arrays.asList("Trabajo", "Privado","Personal","Institucional");
        return tiposCorreos;
    }
    public List<String> listarTipoTelefono() {
        List<String> tipoTelefono = Arrays.asList("Casa", "Oficina","Fijo", "Móvil");
        return tipoTelefono;
    }
      
    public List<String> listarTipoTitulos() {
        List<String> tipoTitulos = Arrays.asList("Título Pregrado", "Maestría", "Postgrado", "Doctorado", "Especialidad", "Certificación", "Apostilla");
        return tipoTitulos;
    }
}

