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
    private List<Bomba> posicionesBombas;
    private List<Posicion> posicionesElegidas;
    private List<String> posicionesElegidasStr;
    private Integer numPosicionesSinBomba;
    private Integer numIntentosDisponibles;
    private boolean winner;

    @PostMapping(value={"/jugar"})
    public String guardarConfiguracion(Configuracion conf){

        //Almacenamiento de la configuración/estado del juego:
        this.configuracion = conf;

        // Limpiado de variables:

        posicionesBombas = new ArrayList<>();
        posicionesElegidas = new ArrayList<>();
        posicionesElegidasStr = new ArrayList<>();
        winner = false;

        // Formato de la lista que contienen las posiciones de las bombas:

        String raw = conf.getPosicionesBombasStr();
        raw = raw.replace("(","");
        raw = raw.replace(")","");
        raw = raw.replace(" ","-");
        raw = raw.replace(","," ");
        List<String> posBombasStrList = List.of(raw.split("-"));

        if(conf.getNumBombas() != posBombasStrList.size()){
            return "redirect:/buscaminas";
        }

        for(String posicion: posBombasStrList){
            Bomba bomba = new Bomba();
            bomba.setFila(Integer.parseInt(posicion.split(" ")[0]));
            bomba.setColumna(Integer.parseInt(posicion.split(" ")[1]));
            posicionesBombas.add(bomba);
        }

        // Se asigna el número de las posiciones a abrir para ganar:
        numPosicionesSinBomba = conf.getNumFilas()*conf.getNumColumnas()-conf.getNumBombas();
        numIntentosDisponibles = conf.getNumIntentos();

        return "redirect:/jugar";
    }

    @GetMapping(value={"/jugar"})
    public String mostrarJuego(Model model){

        model.addAttribute("configuracion",configuracion);
        model.addAttribute("posicionesBombas",posicionesBombas);
        model.addAttribute("posicionesElegidas",posicionesElegidas);
        model.addAttribute("posicionesElegidasStr",posicionesElegidasStr);
        model.addAttribute("numPosicionesSinBomba",numPosicionesSinBomba);
        model.addAttribute("numIntentosDisponibles",numIntentosDisponibles);
        model.addAttribute("winner",winner);

        return "Juego";
    }

    @PostMapping(value={"/minar"})
    public String guardarMina(@RequestParam("posicionExplosion") String posicionExplosion, Model model){

        // Redireccionamiento por si se ganó o perdió:
        if(configuracion.getNumIntentos()<0){
            return "redirect:/buscaminas";
        }

        // Comprobación de posiciones repetidas:

        if(posicionesElegidasStr.contains(posicionExplosion)) {
            model.addAttribute("configuracion",configuracion);
            model.addAttribute("posicionesBombas",posicionesBombas);
            model.addAttribute("posicionesElegidas",posicionesElegidas);
            model.addAttribute("posicionesElegidasStr",posicionesElegidasStr);
            model.addAttribute("numPosicionesSinBomba",numPosicionesSinBomba);
            model.addAttribute("numIntentosDisponibles",numIntentosDisponibles);
            model.addAttribute("winner",winner);
            model.addAttribute("posicionRepetida",posicionExplosion);
            return "Juego";
        }

        // Guardado de la posición:
        posicionesElegidasStr.add(posicionExplosion);
        Posicion posicion = new Posicion();
        posicion.setFila(Integer.parseInt(posicionExplosion.split(" ")[0]));
        posicion.setColumna(Integer.parseInt(posicionExplosion.split(" ")[1]));


        Integer numBombasCerca = 0;

        // Determinación de si es una bomba y bombas cercanas:
        for(Bomba bomba: posicionesBombas){

            // (x,y)
            if(Objects.equals(posicion.getFila(), bomba.getFila()) && Objects.equals(posicion.getColumna(), bomba.getColumna())){
                posicion.setBomba(true);
                numIntentosDisponibles = numIntentosDisponibles - 1;
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

        }

        // Guardado:
        posicion.setNumBombasCerca(numBombasCerca);
        posicionesElegidas.add(posicion);

        // Comprobación de si se ganó la partida:
        Integer aux = 0;
        for(Posicion pos: posicionesElegidas){
            if(!pos.isBomba()){
                aux = aux + 1;
            }
        }

        if(aux.equals(numPosicionesSinBomba)){
            winner = true;
        }

        return "redirect:/jugar";
    }


}
