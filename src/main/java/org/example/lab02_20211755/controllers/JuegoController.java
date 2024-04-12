package org.example.lab02_20211755.controllers;

import org.example.lab02_20211755.entities.Configuracion;
import org.example.lab02_20211755.repositories.AbiertoRepository;
import org.example.lab02_20211755.repositories.BombaRepository;
import org.example.lab02_20211755.repositories.ConfiguracionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/jugar")
public class JuegoController {

    final AbiertoRepository abiertoRepository;
    final BombaRepository bombaRepository;
    final ConfiguracionRepository configuracionRepository;
    JuegoController(AbiertoRepository abiertoRepository,BombaRepository bombaRepository,ConfiguracionRepository configuracionRepository){
        this.abiertoRepository = abiertoRepository;
        this.bombaRepository = bombaRepository;
        this.configuracionRepository = configuracionRepository;
    }



    @PostMapping(value={"","/"})
    public String guardarConfiguracion(Configuracion configuracion){
        configuracionRepository.save(configuracion);
        return "redirect:/jugar";
    }
    @GetMapping(value={"/jugar"})
    public String mostrarJuego(Model model){


        return "Juego";
    }


}
