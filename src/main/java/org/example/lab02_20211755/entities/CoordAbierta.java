package org.example.lab02_20211755.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="coordenadasabiertas")
public class CoordAbierta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCoordenadasAbiertas",nullable = false)
    private Integer idCoordAbierta;

    @Column(name="x",nullable = true)
    private int x;

    @Column(name="y",nullable = true)
    private int y;

    @Column(name="nearBombs",nullable = true)
    private int nearBombs;
}
