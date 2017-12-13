/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.app.entities;

/**
 *
 * @author Gonzalo
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codigo;

    private String title;

    private String urlContenido;

    private String plot;

    private int year;

    private String director;

    private String actors;

    private String poster;

    private float imdbRating;

    public Pelicula(int codigo, String title, String urlContenido, String plot, int year, String director, String actors, String poster, float imdbRating) {
        this.title = title;
        this.urlContenido = urlContenido;
        this.plot = plot;
        this.year = year;
        this.director = director;
        this.poster = poster;
        this.imdbRating = imdbRating;
    }
}
