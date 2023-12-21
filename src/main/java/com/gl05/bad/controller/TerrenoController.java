package com.gl05.bad.controller;

import com.gl05.bad.domain.Proyecto;
import com.gl05.bad.domain.Terreno;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.domain.VistaTerreno;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.ProyectoService;
import com.gl05.bad.servicio.TerrenoService;
import com.gl05.bad.servicio.UserServiceImp;
import com.gl05.bad.servicio.VistaTerrenoService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
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
public class TerrenoController {
    
    @Autowired
    private BitacoraServiceImp bitacoraService;
    
    @Autowired
    private TerrenoService terrenoService;
    
    @Autowired
    private VistaTerrenoService vistaTerrenoService;
    
    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private UserServiceImp usuarioService;
    
    //Función que redirige a la vista de los terrenos del proyecto
    @GetMapping("/Terrenos/{idProyecto}")
    public String mostrarTerrenoProyecto(Model model, Proyecto proyecto, Authentication authentication) {
        model.addAttribute("pageTitle", "Terrenos");
        Proyecto proyectoEncontrado = proyectoService.encontrar(proyecto.getIdProyecto());
        var listaProyectos = proyectoService.listaProyectos();
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        Set<Proyecto> listaProyectosAsignados = usuario.getProyectos();
        if(!listaProyectosAsignados.contains(proyectoEncontrado)){
            return "accesodenegado";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("proyectos", listaProyectos);
        model.addAttribute("proyecto", proyectoEncontrado);
        return "/Proyecto/TerrenosProyecto";
    }
    
    //Función que obtiene los terrenos del proyecto
    @GetMapping(value="/terrenos/data/{idProyecto}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<VistaTerreno> GetTerrenos(@Valid DataTablesInput input, @PathVariable Long idProyecto) {
        return vistaTerrenoService.listarTerrenos(input, idProyecto);
    }

    //Función que agrega un terreno a la base de datos
    @PostMapping("/AgregarTerreno/{idProyecto}")
    public ResponseEntity<String> AgregarTerreno(@PathVariable("idProyecto") Long idProyecto,Terreno terreno, HttpServletRequest request, RedirectAttributes redirectAttributes) {
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
    public ResponseEntity<String> EliminarTerreno(Terreno terreno) {
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
    @GetMapping(value = "/ObtenerTerreno/{id}", produces = "application/json; charset=UTF-8")
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
    public ResponseEntity<String> ActualizarTerreno(@PathVariable("idProyecto") Long idProyecto, Terreno terreno, HttpServletRequest request, RedirectAttributes redirectAttributes) {
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

    @PostMapping("/AgregarTerrenos")
    public ResponseEntity<String> agregarTerrenos(HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestParam("documento") MultipartFile documento,
            @RequestParam("idProyecto") Long idProyecto) {
        try {            
            byte[] fileBytes = documento.getBytes();
            if (fileBytes != null && fileBytes.length > 0) {
                Proyecto proyecto = proyectoService.encontrar(idProyecto);
                //Lectura del archivo para iserción en la base de datos de las cuotas
                try (
                    CSVReader csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(fileBytes), StandardCharsets.UTF_8))) {
                    //Variables a utilizar en el manejo del archivo
                    List<String> listaMatriculas = new ArrayList<>();
                    List<String> listaTerrenos = new ArrayList<>();
                    String[] nextRecord;
                    String matricula;
                    String poligono;
                    Long numero;
                    String seccion;
                    Double areaMetros;
                    Double areaVaras;
                    int contadorRegistros = 1;

                    //Validar que el archivo tenga el formato correcto
                    String mensajeErrores = "";
                    boolean existeError = false;
                    while ((nextRecord = csvReader.readNext()) != null) {
                        // Validar que el registro tenga el número correcto de campos
                        if(contadorRegistros==1){
                            if(!nextRecord[0].trim().trim().equals("Matrícula") || !nextRecord[1].trim().equals("Polígono") || !nextRecord[2].trim().equals("Número") || !nextRecord[3].trim().equals("Sección") || !nextRecord[4].trim().equals("Área (m²)") || !nextRecord[5].trim().equals("Área (v²)")){
                                mensajeErrores += "El encabezado debe estar identificado en el formato y orden siguiente: Matrícula, Polígono, Número, Sección, Área (m²), Área (m²).";
                                existeError = true;
                            }
                        }else{
                            if(nextRecord.length != 6) {
                                mensajeErrores += "\nEl registro N° "+contadorRegistros+", no contiene las 6 columnas del formato, deben estar separadas por una coma.";
                                existeError = true;
                                contadorRegistros++;
                                continue;
                            }
                            Terreno matriculaEncontrada = terrenoService.encontrarMatricula(nextRecord[0]);
                            if(nextRecord[0].length()>18){
                                mensajeErrores += "\nEl registro N° "+contadorRegistros+", la cantidad de caracteres de la matrícula supera el límite permitido de 18 caracteres.";
                                existeError = true;
                            }else if(listaMatriculas.contains(nextRecord[0]) || matriculaEncontrada != null){
                                mensajeErrores += "\nEl registro N° "+contadorRegistros+", la matrícula del terreno se encuentra duplicada.";
                                existeError = true;
                            }else{
                                listaMatriculas.add(nextRecord[0]);
                            }
                            if(nextRecord[1].length()>1){
                                mensajeErrores += "\nEl registro N° "+contadorRegistros+", la cantidad de caracteres del polígono supera el límite permitido de 1 carácter.";
                                existeError = true;
                            }
                            try {
                                numero = Long.parseLong(nextRecord[2]);
                            } catch (NumberFormatException e) {
                                mensajeErrores += "\nEl registro N° "+contadorRegistros+", el valor del número del terreno no es un número.";
                                existeError = true;
                            }
                            if(nextRecord[3].length()>1){
                                mensajeErrores += "\nEl registro N° "+contadorRegistros+", la cantidad de caracteres de la sección del terreno supera el límite permitido de 1 carácter.";
                                existeError = true;
                            }
                            try {
                                Terreno terrenoEncontrado = terrenoService.encontrarLote(nextRecord[1], Long.parseLong(nextRecord[2]), nextRecord[3], proyecto);
                                if(listaTerrenos.contains(nextRecord[1]+"-"+nextRecord[2]+nextRecord[3]) || terrenoEncontrado != null){
                                    mensajeErrores += "\nEl registro N° "+contadorRegistros+", el terreno se encuentra duplicado.";
                                    existeError = true;
                                }else{
                                    listaTerrenos.add(nextRecord[1]+"-"+nextRecord[2]+nextRecord[3]);
                                }
                            } catch (NumberFormatException e) {}
                            try {
                                areaMetros = Double.parseDouble(nextRecord[4]);
                            } catch (NumberFormatException e) {
                                mensajeErrores += "\nEl registro N° "+contadorRegistros+", el valor del área en metros no es un número.";
                                existeError = true;
                            }
                            try {
                                areaVaras = Double.parseDouble(nextRecord[5]);
                            } catch (NumberFormatException e) {
                                mensajeErrores += "\nEl registro N° "+contadorRegistros+", el valor del área en varas no es un número.";
                                existeError = true;
                            }
                        }
                        contadorRegistros++;
                    }
                    csvReader.close();
                    //Se envía el mensaje de errores
                    if(existeError){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errores en los registros:"+mensajeErrores);
                    }
                    //Registro de los pagos y las cuotas
                    CSVReader csvReader2 = new CSVReader(new InputStreamReader(new ByteArrayInputStream(fileBytes), StandardCharsets.UTF_8));
                    csvReader2.readNext();
                    contadorRegistros=0;
                    while ((nextRecord = csvReader2.readNext()) != null) {
                        matricula = nextRecord[0].trim();
                        poligono = nextRecord[1].toUpperCase().trim();
                        numero = Long.parseLong(nextRecord[2].trim());
                        seccion = nextRecord[3].toLowerCase().trim();
                        areaMetros = Double.parseDouble(nextRecord[4].trim());
                        areaVaras = Double.parseDouble(nextRecord[5].trim());
                        Terreno terreno = new Terreno();
                        terreno.setProyecto(proyecto);
                        terreno.setMatricula(matricula);
                        terreno.setPoligono(poligono);
                        terreno.setNumero(numero);
                        terreno.setSeccion(seccion);
                        terreno.setAreaMetros(areaMetros);
                        terreno.setAreaVaras(areaVaras);
                        terrenoService.agregar(terreno);
                    }
                    csvReader2.close();
                    String mensaje = "Se ha agregado el listado de terrenos correctamente.";
                    bitacoraService.registrarAccion("Agregar terrenos al proyecto");
                    return ResponseEntity.ok(mensaje);
                } catch (CsvValidationException | IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error durante el procesamiento del archivo CSV");
                } 
            }else{
                String error = "Ha ocurrido un error al agregar el listado de terrenos, documento vacío.";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);        
            }
        } catch(Exception e) {
            String error = "Ha ocurrido un error al agregar el listado de terrenos.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
