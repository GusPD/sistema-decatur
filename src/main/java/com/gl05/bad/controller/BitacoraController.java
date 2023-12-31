package com.gl05.bad.controller;

import com.gl05.bad.domain.Bitacora;
import com.gl05.bad.domain.Usuario;
import com.gl05.bad.servicio.BitacoraServiceImp;
import com.gl05.bad.servicio.UserServiceImp;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        model.addAttribute("usuario", usuario);
        return "/Bitacora/GestionarBitacora";
    }
    
    //Función que obtiene la registros de la bistacora de la base de datos
    @GetMapping("/bitacora/data")
    @ResponseBody
    public DataTablesOutput<Bitacora> getBitacora(@Valid DataTablesInput input) {
        return bitacoraService.listarBitacora(input);
    }
}
