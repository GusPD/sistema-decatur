package com.gl05.bad.controller;

import com.gl05.bad.domain.AspiranteProfesor;
import com.gl05.bad.domain.AtestadoTa;
import com.gl05.bad.domain.Correo;
import com.gl05.bad.domain.Documento;
import com.gl05.bad.domain.ExperienciaLaboral;
import com.gl05.bad.domain.ListadoDocumentacionPersonal;
import com.gl05.bad.domain.RedSocial;
import com.gl05.bad.domain.Telefono;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.AspiranteProfesorService;
import com.gl05.bad.servicio.AtestadoTaService;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.PaisService;
import com.gl05.bad.servicio.CorreoService;
import com.gl05.bad.servicio.DocumentoService;
import com.gl05.bad.servicio.ExperienciaLaboralService;
import com.gl05.bad.servicio.RedSocialService;
import com.gl05.bad.servicio.TelefonoService;
import com.gl05.bad.servicio.UserService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class AspiranteProfesorController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AspiranteProfesorService aspiranteService;
    
    @Autowired
    private PaisService paisService;
    
    @Autowired
    private CorreoService correoService;
    
    @Autowired
    private RedSocialService redSocialService;
    
    @Autowired
    private ExperienciaLaboralService experienciaLaboralService;
    
    @Autowired
    private TelefonoService telefonoService;
    
    @Autowired
    private DocumentoService docService;
    
    @Autowired
    private AtestadoTaService atestadoService;

    @GetMapping("/PerfilAspiranteProfesor/{idAspiranteProfesor}")
    public String perfilAspiranteProfesor(Model model, AspiranteProfesor aspirante) {
        model.addAttribute("pageTitle", "Perfil Aspirante Profesor");
        var aspiranteNew = aspiranteService.encontrarAP(aspirante);
        //Se cargan para la edicion de la informacion general del aspirante
        List<String> sexos = listarSexos();
        List<String> generos = listarGeneros();
        List<String> estadosCiviles = listarEstados();
        List<String> nacionalidades = listarNacionalidades();
        
        //Manejo de correos
        var correos = correoService.listarCorreos();
        List<String> tiposCorreo = listarTiposCorreos();
        List<Correo> correosAspirante = new ArrayList();
        for (var eCorreo : correos) {
            if(eCorreo.getIdListCorreo() == (int) aspiranteNew.getIdListCorreo()){
                correosAspirante.add(eCorreo);
            }
        }
        
        //Manejo de telefonos
        var telefonos = telefonoService.listarTelefonos();
        List<String> tiposTelefono = listarTipoTelefono();
        List<Telefono> telefonosAspirante = new ArrayList();
        for (var eTelefono: telefonos) {
            if(eTelefono.getIdListTelefono() == (int) aspiranteNew.getIdListTelefono()){
                telefonosAspirante.add(eTelefono);
            }
        }
        
        //Manejo de redes sociales
        var redesSociales = redSocialService.listarRedesSocial();
        List<String> tiposRedSocial = listarRedesSociales();
        List<RedSocial> redesSocialesAspirante = new ArrayList();
        for (var eRedSocial : redesSociales) {
            if(eRedSocial.getIdListRs() == (int) aspiranteNew.getIdListRs()){
                redesSocialesAspirante.add(eRedSocial);
            }
        }
        
        //Manejo de experiencias laborales
        var experienciasLaborales = experienciaLaboralService.listarExperienciasLaboral();
        List<ExperienciaLaboral> experienciasLaboralesAspirante = new ArrayList();
        for (var eExperienciaLaboral : experienciasLaborales) {
            if(eExperienciaLaboral.getIdListEl() == (int) aspiranteNew.getIdListEl()){
                experienciasLaboralesAspirante.add(eExperienciaLaboral);
            }
        }
        
        //Manejo de paises
        var paises = paisService.listarPaises();
        String paisSeleccionado="";
        if (aspiranteNew.getIdPais() != null) {
            for (var pais : paises) {
                if(pais.getIdPais() == (int) aspiranteNew.getIdPais()){
                    paisSeleccionado=pais.getNombrePais();
                }
            }
        }
        
        //Manejo de imagenes
        Blob imagenBlob = aspiranteNew.getFotografiaAp();
        String imagenBase64 = null;

        if (imagenBlob != null) {
            try {
                byte[] bytes = imagenBlob.getBytes(1, (int) imagenBlob.length());
                String base64Encoded = Base64Utils.encodeToString(bytes);
                bytes = null;
                imagenBase64 = new String(base64Encoded.getBytes(StandardCharsets.UTF_8));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        //Manejo de documentacion personal
        ListadoDocumentacionPersonal ldp= new ListadoDocumentacionPersonal();
        ldp.setIdListDp(Long.valueOf(aspiranteNew.getIdListDp()));
        var documentos = docService.listarDocumentoPorListado(ldp);
        
        //Manejo de atestados academicos
        List<String> tiposTitulos = listarTipoTitulos();
        var atestados = atestadoService.listarAtestados();
        List<AtestadoTa> atestadoAspirante = new ArrayList();
        for (var a : atestados) {
            if(a.getIdListTa() == (int) aspiranteNew.getIdListTa()){
                atestadoAspirante.add(a);
            }
        }

        model.addAttribute("atestados", atestadoAspirante);
        model.addAttribute("tiposTitulos", tiposTitulos);
        model.addAttribute("imagenBase64", imagenBase64);
        model.addAttribute("documentos", documentos);
        model.addAttribute("aspiranteAP", aspiranteNew);
        model.addAttribute("paises", paises);
        model.addAttribute("sexos", sexos);
        model.addAttribute("generos", generos);
        model.addAttribute("estadosCiviles", estadosCiviles);
        model.addAttribute("nacionalidades", nacionalidades);
        model.addAttribute("paisSeleccionado", paisSeleccionado);
        model.addAttribute("correos", correosAspirante);
        model.addAttribute("tiposCorreo", tiposCorreo);
        model.addAttribute("telefonos", telefonosAspirante);
        model.addAttribute("tiposTelefonos", tiposTelefono);
        model.addAttribute("redesSociales", redesSocialesAspirante);
        model.addAttribute("tiposRedesSociales", tiposRedSocial);
        model.addAttribute("experienciasLaborales", experienciasLaboralesAspirante);
        return "/AspiranteProfesor/perfilAspiranteProfesor";
    }
    
    @GetMapping("/aspirante/data")
    @ResponseBody
    public DataTablesOutput<AspiranteProfesor> getAspirantesProfesor(@Valid DataTablesInput input) {
      return aspiranteService.listarAspirantes(input);
    }
        
    @GetMapping("/GestionarAspiranteProfesor")
    public String mostrarAspirantesProfesor(Model model) {
        model.addAttribute("pageTitle", "Gestionar Aspirante");
        var elementos = aspiranteService.listarAspirantes();
        model.addAttribute("aspirantesAP", elementos);
        return "/AspiranteProfesor/gestionarAspiranteProfesor";
    }
    
    @PostMapping("/AgregarAspiranteProfesor")
    public String agregarAspiranteProfesor(
        @RequestParam("codAp") String codAp,
        @RequestParam("nombresAp") String nombresAp,
        @RequestParam("apellidosAp") String apellidosAp,
        @RequestParam("correo") String correo,
        RedirectAttributes redirectAttributes) {
        try {            
            if (userService.encontrarUsuarioPorUsername(codAp) != null) {
                redirectAttributes.addFlashAttribute("error", "Ya existe un usuario con el mismo código.");
                return "redirect:/GestionarAspiranteProfesor";
            }
            if (userService.encontrarUsuarioPorEmail(correo) != null) {
                redirectAttributes.addFlashAttribute("error", "Ya existe un usuario con el mismo correo electrónico.");
                return "redirect:/GestionarAspiranteProfesor";
            }
            //Creación del usuario aspirante a profesor
            String password = aspiranteService.generarPassword(12);
            String encryptedPassword = passwordEncoder.encode(password);
            Usuario usuarioAspirante = new Usuario();
            usuarioAspirante.setUsername(codAp);
            usuarioAspirante.setEmail(correo);
            usuarioAspirante.setPassword(encryptedPassword);
            usuarioAspirante.setEnabled(true);
            usuarioAspirante.setUsuarioBloqueado(0);
            usuarioAspirante.setNumerointentos(0);
            userService.AgregarUsuarios(usuarioAspirante);
            //Envio de credenciañes
            String asunto= "Credenciales de usuario del sistema de maestrías";
            String mensaje= "Bienvenid@ " + nombresAp + " " + apellidosAp + " las credenciales proporcionadas como aspirante a profesor son:\nUsuario: " + codAp.toLowerCase() + "\nContraseña: " + password;
            aspiranteService.enviarCorreo(correo, asunto, mensaje);
            
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error al registrar un aspirante a profesor.");
            return "redirect:/GestionarAspiranteProfesor";
        }
        try {
            //Creación del aspirante a profesor
            Usuario usuario=userService.encontrarUsuarioPorUsername(codAp);
            AspiranteProfesor aspirante = new AspiranteProfesor();
            aspirante.setCodAp(codAp);
            aspirante.setNombresAp(nombresAp);
            aspirante.setApellidosAp(apellidosAp);
            Integer idUsuarioAspirante=usuario.getIdUsuario().intValue();
            aspirante.setIdusuario(idUsuarioAspirante);
            aspiranteService.agregarAP(aspirante);
            bitacoraService.registrarAccion("Agregar aspirante a profesor");
            redirectAttributes.addFlashAttribute("mensaje", "Se ha registrado un aspirante a profesor, y se le ha habilitado un usuario.");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ya existe un aspirante a profesor con ese identificador.");
        }
        return "redirect:/GestionarAspiranteProfesor";
    }
    
    @PostMapping("/ActualizarAspiranteProfesor/{idAspiranteProfesor}")
    public String actualizarAspiranteProfesor(AspiranteProfesor aspirante, @PathVariable("idAspiranteProfesor") int idAspiranteProfesor, RedirectAttributes redirectAttributes) throws ParseException{
        
        try {
            //Arreglando formato del DUI, NIT, NUP, DOC PERSONAL, PASAPORTE
            String dui=aspirante.getDuiAp();
            dui=dui.replace("-","");
            aspirante.setDuiAp(dui);
            String nit=aspirante.getNitAp();
            nit=nit.replace("-","");
            aspirante.setNitAp(nit);
            String nup=aspirante.getNupAp();
            aspirante.setNupAp(nup);
            String pasaporte=aspirante.getPasaporteAp();
            pasaporte=pasaporte.replace("-","");
            pasaporte=pasaporte.replace("_","");
            aspirante.setPasaporteAp(pasaporte);
            String docPersonal=aspirante.getDocPersonalAp();
            docPersonal=docPersonal.replace("-","");
            docPersonal=docPersonal.replace("_","");
            aspirante.setDocPersonalAp(docPersonal);
            //Convirtiendo el pais en entero
            int idPais = (int)(aspirante.getIdPais());
            aspirante.setIdPais(idPais);
            aspiranteService.actualizarAP(aspirante);
            bitacoraService.registrarAccion("Actualizar aspirante a profesor");
            redirectAttributes.addFlashAttribute("mensaje", "Se ha actualizado la información general del aspirante a profesor.");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se actualizó la información general del aspirante a profesor.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PerfilAspiranteProfesor/{idAspiranteProfesor}").buildAndExpand(idAspiranteProfesor).toUriString();
        return "redirect:" + redirectUrl;  
    }
    
    @PostMapping("/EliminarAspiranteProfesor/{idAspiranteProfesor}")
    public ResponseEntity EliminarAspiranteProfesor(AspiranteProfesor aspiranteProfesor) {
        try {
            aspiranteService.eliminarAP(aspiranteProfesor);
            bitacoraService.registrarAccion("Eliminar aspirante a profesor");
            String mensaje = "Se ha eliminado el aspirante a profesor correctamente.";
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Ha ocurrido un error al eliminar el aspirante a profesor";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/AgregarCorreoAspiranteProfesor/{idAspiranteProfesor}/{idListCorreo}")
    public String agregarCorreoAspiranteProfesor(
        @RequestParam("tipoCorreo") String tipoCorreo,
        @RequestParam("correo") String correo,
        @PathVariable("idListCorreo") int idListCorreo, 
        @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, 
        RedirectAttributes redirectAttributes) {
        try {
            Correo correoNew = new Correo();
            correoNew.setIdListCorreo(idListCorreo);
            correoNew.setCorreo(correo);
            correoNew.setTipoCorreo(tipoCorreo);
            correoService.agregarC(correoNew);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha ingresado un correo.");
            bitacoraService.registrarAccion("Agregar correo del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ya se encuentra registrado el correo.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PerfilAspiranteProfesor/{idAspiranteProfesor}").buildAndExpand(idAspiranteProfesor).toUriString();
        return "redirect:" + redirectUrl;   
    }
    
    @PostMapping("/AgregarTelefonoAspiranteProfesor/{idAspiranteProfesor}/{idListTelefono}")
    public String agregarTelefonoAspiranteProfesor(
        @RequestParam("tipoTelefono") String tipoTelefono,
        @RequestParam("numero") String numero,
        @PathVariable("idListTelefono") int idListTelefono, 
        @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, 
        RedirectAttributes redirectAttributes) {
        try {
            Telefono telefonoNew = new Telefono();
            telefonoNew.setIdListTelefono(idListTelefono);
            telefonoNew.setNumero(numero);
            telefonoNew.setTipoTelefono(tipoTelefono);
            telefonoService.agregarT(telefonoNew);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha ingresado un telefono.");
            bitacoraService.registrarAccion("Agregar teléfono del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ya se encuentra registrado el telefono.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PerfilAspiranteProfesor/{idAspiranteProfesor}").buildAndExpand(idAspiranteProfesor).toUriString();
        return "redirect:" + redirectUrl;   
    }
    
    @PostMapping("/AgregarExperienciaLaboralAspiranteProfesor/{idAspiranteProfesor}/{idListEl}")
    public String agregarExperienciaLaboralAspiranteProfesor(
        @RequestParam("nombreInstitucion") String nombreInstitucion,
        @RequestParam("cargo") String cargo,
        @RequestParam("funciones") String funciones,
        @RequestParam("periodoInicio") String periodoInicio,
        @RequestParam("periodoFin") String periodoFin,
        @PathVariable("idListEl") int idListEl, 
        @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, 
        RedirectAttributes redirectAttributes) throws ParseException {
        try {
            SimpleDateFormat fechaInicio = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaInicioC = fechaInicio.parse(periodoInicio);
            SimpleDateFormat fechaFin = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaFinC = fechaFin.parse(periodoFin);
            ExperienciaLaboral experienciaLaboralNew = new ExperienciaLaboral();
            experienciaLaboralNew.setIdListEl(idListEl);
            experienciaLaboralNew.setNombreInstitucion(nombreInstitucion);
            experienciaLaboralNew.setCargo(cargo);
            experienciaLaboralNew.setFunciones(funciones);
            experienciaLaboralNew.setPeriodoInicio(fechaInicioC);
            experienciaLaboralNew.setPeriodoFin(fechaFinC);
            experienciaLaboralService.agregarEL(experienciaLaboralNew);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha ingresado una experiencia laboral.");
            bitacoraService.registrarAccion("Agregar experiencia laboral del aspirante a profesor");
        } catch(ParseException e) {
            redirectAttributes.addFlashAttribute("error", "No en el ingreso de la experiencia laboral .");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PerfilAspiranteProfesor/{idAspiranteProfesor}").buildAndExpand(idAspiranteProfesor).toUriString();
        return "redirect:" + redirectUrl;   
    }
    
    @PostMapping("/AgregarRedSocialAspiranteProfesor/{idAspiranteProfesor}/{idListRs}")
    public String agregarRedSocialAspiranteProfesor(
        @RequestParam("nombreRs") String nombreRs,
        @RequestParam("link") String link,
        @PathVariable("idListRs") int idListRs, 
        @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, 
        RedirectAttributes redirectAttributes) {
        try {
            RedSocial redSocialNew = new RedSocial();
            redSocialNew.setIdListRs(idListRs);
            redSocialNew.setNombreRs(nombreRs);
            redSocialNew.setLink(link);
            redSocialService.agregarRS(redSocialNew);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha ingresado una red social.");
            bitacoraService.registrarAccion("Agregar red social del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ya se encuentra registrado la red social.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PerfilAspiranteProfesor/{idAspiranteProfesor}").buildAndExpand(idAspiranteProfesor).toUriString();
        return "redirect:" + redirectUrl;   
    }
    
    @GetMapping("/EliminarCorreoAspiranteProfesor/{idCorreo}/{idAspiranteProfesor}")
    public String eliminarCorreoAspiranteProfesor(Correo correo, @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, RedirectAttributes redirectAttributes) {
        try {
            correoService.eliminarC(correo);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado el correo.");
            bitacoraService.registrarAccion("Eliminar correo del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar el correo.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PerfilAspiranteProfesor/{idAspiranteProfesor}").buildAndExpand(idAspiranteProfesor).toUriString();
        return "redirect:" + redirectUrl;
    }
    
    @GetMapping("/EliminarTelefonoAspiranteProfesor/{idTelefono}/{idAspiranteProfesor}")
    public String eliminarTelefonoAspiranteProfesor(Telefono telefono, @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, RedirectAttributes redirectAttributes) {
        try {
            telefonoService.eliminarT(telefono);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado el telefono.");
            bitacoraService.registrarAccion("Eliminar teléfono del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar el telefono.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PerfilAspiranteProfesor/{idAspiranteProfesor}").buildAndExpand(idAspiranteProfesor).toUriString();
        return "redirect:" + redirectUrl;
    }
    
    @GetMapping("/EliminarRedSocialAspiranteProfesor/{idRs}/{idAspiranteProfesor}")
    public String eliminarRedSocialAspiranteProfesor(RedSocial redSocial, @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, RedirectAttributes redirectAttributes) {
        try {
            redSocialService.eliminarRS(redSocial);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado una red social.");
            bitacoraService.registrarAccion("Eliminar red social del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar la red social.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PerfilAspiranteProfesor/{idAspiranteProfesor}").buildAndExpand(idAspiranteProfesor).toUriString();
        return "redirect:" + redirectUrl;
    }
    
    @GetMapping("/EliminarExperienciaLaboralAspiranteProfesor/{idEl}/{idAspiranteProfesor}")
    public String eliminarExperienciaLaboralAspiranteProfesor(ExperienciaLaboral experienciaLaboral, @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, RedirectAttributes redirectAttributes) {
        try {
            experienciaLaboralService.eliminarEL(experienciaLaboral);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado la experiencia laboral.");
            bitacoraService.registrarAccion("Eliminar experiencia laboral del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar la experiencia laboral.");
        }
        String redirectUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/PerfilAspiranteProfesor/{idAspiranteProfesor}").buildAndExpand(idAspiranteProfesor).toUriString();
        return "redirect:" + redirectUrl;
    }
    
    @PostMapping("/actualizarFotoAP/{idAspiranteProfesor}")
    public String actualizarFoto(
        @RequestParam Map<String, MultipartFile> campos,
        @PathVariable("idAspiranteProfesor") Long idAspiranteProfesor,
        RedirectAttributes redirectAttributes) {
        AspiranteProfesor aspirante = new AspiranteProfesor();
        try {
            aspirante.setIdAspiranteProfesor(idAspiranteProfesor);
            for (Map.Entry<String, MultipartFile> entry : campos.entrySet()) {
                String nombreCampo = entry.getKey();
                MultipartFile campo = entry.getValue();

                byte[] fileBytes = campo.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(fileBytes);

                aspiranteService.actualizarFoto(aspirante, nombreCampo, blob);
            }

            redirectAttributes.addFlashAttribute("mensaje", "Se han actualizado los campos correctamente.");
            bitacoraService.registrarAccion("Actualizar fotografía del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Sucedió un error al actualizar los campos.");
        }

        return "redirect:/PerfilAspiranteProfesor/" + aspirante.getIdAspiranteProfesor();  
    }

    @PostMapping("/actualizarDocumentoAP/{idAspiranteProfesor}")
    public String actualizarDocumento(
        @RequestParam("tipoDocumento") String tipoDocumento,
        @RequestParam Map<String, MultipartFile> campos,
        @PathVariable("idAspiranteProfesor") Long idAspiranteProfesor,
        RedirectAttributes redirectAttributes) {
      
        Documento documento = new Documento();
        AspiranteProfesor aspirante = new AspiranteProfesor();
        AspiranteProfesor aspiranteExistente = new AspiranteProfesor();
        ListadoDocumentacionPersonal listaDoc = new ListadoDocumentacionPersonal();
        
        try {
            aspirante.setIdAspiranteProfesor(idAspiranteProfesor);
            aspiranteExistente=aspiranteService.encontrarAP(aspirante);
            listaDoc.setIdListDp(Long.valueOf(aspiranteExistente.getIdListDp()));
            
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
              }catch(IOException e){
                redirectAttributes.addFlashAttribute("error", "El documento no cumple con las especificaciones dadas");
              }
            }
            redirectAttributes.addFlashAttribute("mensaje", "Se han actualizado sus documentos.");
            bitacoraService.registrarAccion("Actualizar documento personal del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Sucedió un error al subir el documento");
        }

        return "redirect:/PerfilAspiranteProfesor/" + aspiranteExistente.getIdAspiranteProfesor();  
    }

    @GetMapping("/eliminarDocumentoAP/{idAspiranteProfesor}/{IdDocumento}")
    public String eliminarDocumento(
            Documento doc, 
            @PathVariable("idAspiranteProfesor") Long idAspiranteProfesor,
            RedirectAttributes redirectAttributes) {
        try {
            docService.eliminarDocumento(doc);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado el documento.");
            bitacoraService.registrarAccion("Eliminar documento personal del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar el documento.");
        }
        return "redirect:/PerfilAspiranteProfesor/" + idAspiranteProfesor;
    }

    @GetMapping("/archivoAP/{IdDocumento}")
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
                bitacoraService.registrarAccion("Ver documento personal del aspirante a profesor");
                return new ResponseEntity <>(pdfBytes, headers, HttpStatus.OK);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }


    @PostMapping("/agregarTituloAcademicoAP/{idAspiranteProfesor}/{idListTa}")
    public String agregarTituloAcademicoAP(
        @RequestParam ("tipoAta") String tipoAta,
        @RequestParam ("nombreAta") String nombreAta,
        @RequestParam ("institucion") String institucion,
        @RequestParam ("anioTitulacion") Integer anioTitulacion,
        @RequestParam ("archivoAta") MultipartFile archivo,
        @PathVariable("idListTa") int idListTa, 
        @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, 
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
            redirectAttributes.addFlashAttribute("mensaje", "Se ha ingresado un título académico.");
            bitacoraService.registrarAccion("Agregar título académico del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha sucedido un error, vuelva a intentarlo");
        }
        return "redirect:/PerfilAspiranteProfesor/" + idAspiranteProfesor;   
    }
  
    @PostMapping("/modificarTituloAcademicoAP/{idAspiranteProfesor}/{idAtestadoTa}")
    public String modificarTituloAcademicoAP(
        @RequestParam ("tipoAta") String tipoAta,
        @RequestParam ("nombreAta") String nombreAta,
        @RequestParam ("institucion") String institucion,
        @RequestParam ("anioTitulacion") Integer anioTitulacion,
        @RequestParam ("archivoAta") MultipartFile archivo,
        @PathVariable("idAtestadoTa") Long idAtestadoTa, 
        @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, 
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
          redirectAttributes.addFlashAttribute("mensaje", "Se ha actualizado un título académico.");
          bitacoraService.registrarAccion("Actualizar títuli académico del aspirante a profesor");
        }else{
          try {
            byte[] fileBytes = archivo.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(fileBytes);
            atestadoActualizar.setArchivoAta(blob);
            atestadoService.actualizarAtestado(atestadoActualizar);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha actualizado un título académico.");
            bitacoraService.registrarAccion("Actualizar título académico del aspirante a profesor");
          } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha sucedido un error al actualizar el titulo. Intentelo de nuevo");
          }
        }
        
        return "redirect:/PerfilAspiranteProfesor/" + idAspiranteProfesor;   
    }
    
    @GetMapping("/eliminarTituloAcademicoAP/{idAspiranteProfesor}/{idAtestadoTa}")
    public String eliminarAtestadoTitulo(AtestadoTa atestado, @PathVariable("idAspiranteProfesor")int idAspiranteProfesor, RedirectAttributes redirectAttributes) {
        try {
            atestadoService.eliminarAtestado(atestado);
            redirectAttributes.addFlashAttribute("mensaje", "Se ha eliminado el título académico.");
            bitacoraService.registrarAccion("Eliminar título académico del aspirante a profesor");
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al eliminar el título académico.");
        }
        return "redirect:/PerfilAspiranteProfesor/" + idAspiranteProfesor;
    }
    
    @GetMapping("/archivoAP/tituloAcademico/{idAtestadoTa}")
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
                bitacoraService.registrarAccion("Ver título académico del aspirante a profesor");
                return new ResponseEntity <>(pdfBytes, headers, HttpStatus.OK);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
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
    public List<String> listarRedesSociales() {
        List<String> redesSociales = Arrays.asList("LinkedIn", "Twitter", "Instagram", "Facebook", "YouTube", "Slack", "GitHub");
        return redesSociales;
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