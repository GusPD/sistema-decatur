package com.gl05.bad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TerrenoController {

    @GetMapping("/MostrarProyecto/TerrenoProyecto")
    public String terrenoProyecto(Model model) {
        return "/Proyecto/TerrenoProyecto";
    }
}
