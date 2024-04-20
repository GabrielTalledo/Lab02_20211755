package org.example.lab02_20211755.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfiguracionController {

    @GetMapping(value={"","/","/buscaminas"})
    public String mostrarConfiguracion(){
        return "Configuracion";
    }

}
