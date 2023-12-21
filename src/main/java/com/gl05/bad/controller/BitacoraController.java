package com.gl05.bad.controller;

import com.gl05.bad.domain.Bitacora;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.UserServiceImp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BitacoraController {
  
    @Autowired
    private BitacoraServiceImp bitacoraService;

    @Autowired
    private UserServiceImp usuarioService;

    //Función que redirige a la vista de la bitacora
    @GetMapping("/Bitacora")
    public String mostrarBitacora(Model model, Authentication authentication) {
        model.addAttribute("pageTitle", "Bitácora");
        String username = authentication.getName();
        Usuario usuario = usuarioService.encontrarUsername(username);
        List<Usuario> listaUsuarios = usuarioService.listaUsuarios();
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarios", listaUsuarios);
        return "/Bitacora/GestionarBitacora";
    }
    
    //Función que obtiene la registros de la bistacora de la base de datos
    @GetMapping(value="/bitacora/data", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public DataTablesOutput<Bitacora> getBitacora(@Valid DataTablesInput input,
    @RequestParam("fecha_inicio") String fechaInicio, @RequestParam("fecha_fin") String fechaFin,
    @RequestParam("usuario") String usuario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime fechaInicioDate = null;
        LocalDateTime fechaFinDate = null;
        ZoneId zona= ZoneId.of("America/El_Salvador");
        if (!fechaInicio.isEmpty()) {
            fechaInicioDate = LocalDate.parse(fechaInicio, formatter).atStartOfDay().atZone(zona).toLocalDateTime();
        }
        if (!fechaFin.isEmpty()) {
            fechaFinDate = LocalDate.parse(fechaFin, formatter).atTime(23, 59, 59).atZone(zona).toLocalDateTime();
        }
        return bitacoraService.listarBitacora(input, fechaInicioDate, fechaFinDate, usuario);
    }
}
