package org.example.lab02_20211755.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/buscaminas")
public class ConfiguracionController {

    @GetMapping(value={"","/"})
    public String mostrarConfiguracion(){
        return "Configuracion";
    }




}
