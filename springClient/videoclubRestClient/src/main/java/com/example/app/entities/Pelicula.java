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
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int codigo;

    //@Size(min = 2, max = 45, message = "el nombre debe tener minimo 2 letras y maximo 45")
    private String Title;

    private String urlContenido;

    //@Size(min = 4, max = 45, message = "la descripcion debe tener minimo 4 letras y maximo 45")
    private String Plot;

    private int Year;

    private String Director;

    private String Actors;

    private String Poster;

    private float imdbRating;

    public Pelicula() {
        this.codigo = codigo;
        this.Title = "";
        this.urlContenido = "";
        this.Plot = "";
        this.Year = Year;
        this.Director = "";
        this.Actors = "";
        this.Poster = "";
        this.imdbRating = 0;
    }

    public Pelicula(int codigo, String Title, String urlContenido, String Plot, int Year, String Director, String Actors, String Poster, float imdbRating) {
        this.Title = Title;
        this.urlContenido = urlContenido;
        this.Plot = Plot;
        this.Year = Year;
        this.Director = Director;
        this.Plot = Plot;
        this.Poster = Poster;
        this.imdbRating = imdbRating;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getUrlContenido() {
        return urlContenido;
    }

    public void setUrlContenido(String urlContenido) {
        this.urlContenido = urlContenido;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String Plot) {
        this.Plot = Plot;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String Director) {
        this.Director = Director;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String Actors) {
        this.Actors = Actors;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String Poster) {
        this.Poster = Poster;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

}
