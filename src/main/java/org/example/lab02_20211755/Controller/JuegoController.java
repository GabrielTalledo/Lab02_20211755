package org.example.lab02_20211755.Controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.example.lab02_20211755.Entities.Bomba;
import org.example.lab02_20211755.Entities.Configuracion;
import org.example.lab02_20211755.Entities.Posicion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class JuegoController {

    private Configuracion configuracion;
    private List<Bomba> posicionesBombas = new ArrayList<>();
    private List<Posicion> posicionesElegidas = new ArrayList<>();
    private Integer numPosicionesSinBomba = 0;

    @PostMapping(value={"/jugar"})
    public String guardarConfiguracion(Configuracion conf){

        //Almacenamiento de la configuración/estado del juego:
        this.configuracion = conf;

        // Formato de la lista que contienen las posiciones de las bombas:

        String raw = conf.getPosicionesBombasStr();
        raw = raw.replace("(","");
        raw = raw.replace(")","");
        raw = raw.replace(" ","-");
        raw = raw.replace(","," ");
        List<String> posBombasStrList = List.of(raw.split("-"));

        for(String posicion: posBombasStrList){
            Bomba bomba = new Bomba();
            bomba.setFila(Integer.parseInt(posicion.split(" ")[0]));
            bomba.setColumna(Integer.parseInt(posicion.split(" ")[1]));
            posicionesBombas.add(bomba);
        }

        // Se asigna el número de las posiciones a abrir para ganar:
        numPosicionesSinBomba = conf.getNumFilas()*conf.getNumColumnas()-conf.getNumBombas();

        return "redirect:/jugar";
    }

    @GetMapping(value={"/jugar"})
    public String mostrarJuego(Model model){

        model.addAttribute("configuracion",configuracion);
        model.addAttribute("posicionesBombas",posicionesBombas);
        model.addAttribute("posicionesElegidas",posicionesElegidas);
        model.addAttribute("numPosicionesSinBomba",numPosicionesSinBomba);

        return "Juego";
    }

    @PostMapping(value={"/minar"})
    public String guardarMina(@RequestParam("posicionExplosion") String posicionExplosion){

        // Guardado de la posición
        Posicion posicion = new Posicion();
        posicion.setFila(Integer.parseInt(posicionExplosion.split(" ")[0]));
        posicion.setColumna(Integer.parseInt(posicionExplosion.split(" ")[1]));

        Integer numBombasCerca = 0;

        // Determinación de si es una bomba y bombas cercanas
        for(Bomba bomba: posicionesBombas){

            // (x,y)
            if(Objects.equals(posicion.getFila(), bomba.getFila()) && Objects.equals(posicion.getColumna(), bomba.getColumna())){
                posicion.setBomba(true);
                configuracion.setNumIntentos(configuracion.getNumIntentos()-1);
            }
            // (x-1,y)
            if(Objects.equals(posicion.getFila()-1, bomba.getFila()) && Objects.equals(posicion.getColumna(), bomba.getColumna())){
                numBombasCerca = numBombasCerca + 1;
            }
            // (x+1,y)
            if(Objects.equals(posicion.getFila()+1, bomba.getFila()) && Objects.equals(posicion.getColumna(), bomba.getColumna())){
                numBombasCerca = numBombasCerca + 1;
            }
            // (x,y-1)
            if(Objects.equals(posicion.getFila(), bomba.getFila()) && Objects.equals(posicion.getColumna()-1, bomba.getColumna())){
                numBombasCerca = numBombasCerca + 1;
            }
            // (x,y+1)
            if(Objects.equals(posicion.getFila(), bomba.getFila()) && Objects.equals(posicion.getColumna()+1, bomba.getColumna())){
                numBombasCerca = numBombasCerca + 1;
            }
            // (x-1,y-1)
            if(Objects.equals(posicion.getFila()-1, bomba.getFila()) && Objects.equals(posicion.getColumna()-1, bomba.getColumna())){
                numBombasCerca = numBombasCerca + 1;
            }
            // (x-1,y+1)
            if(Objects.equals(posicion.getFila()-1, bomba.getFila()) && Objects.equals(posicion.getColumna()+1, bomba.getColumna())){
                numBombasCerca = numBombasCerca + 1;
            }
            // (x+1,y-1)
            if(Objects.equals(posicion.getFila()+1, bomba.getFila()) && Objects.equals(posicion.getColumna()-1, bomba.getColumna())){
                numBombasCerca = numBombasCerca + 1;
            }

            posicionesElegidas.add(posicion);
        }

        return "redirect:/jugar";
    }


}
