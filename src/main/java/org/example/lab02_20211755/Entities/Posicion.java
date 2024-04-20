package org.example.lab02_20211755.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Posicion {
    private Integer fila;
    private Integer columna;
    private Integer numBombasCerca;
    private boolean isBomba = false;
}
