package org.example.lab02_20211755.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="configuracion")
public class Configuracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idConfiguracion",nullable = false)
    private Integer idConfiguracion;

    @Column(name="numFilas",nullable = true)
    private int numFilas;

    @Column(name="numColumnas",nullable = true)
    private int numColumnas;

    @Column(name="numIntentos",nullable = true)
    private int numIntentos;

    @Column(name="numBombas",nullable = true)
    private int numBombas;

    @Column(name="posicionesBombas",nullable = true)
    private String posicionesBombas;


}
