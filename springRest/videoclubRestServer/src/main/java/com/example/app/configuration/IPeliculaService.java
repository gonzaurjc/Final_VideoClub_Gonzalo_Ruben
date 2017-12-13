/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.app.configuration;

import com.example.app.entities.Pelicula;
import java.util.List;

/**
 *
 * @author Gonzalo
 */
public interface IPeliculaService {
    public void initDatabase();
    public void save(Pelicula pelicula);
    public void delete(int codigo);
    public Pelicula getPelicula(int codigo);
    public void edit(Pelicula pelicula);
    public List<Pelicula> findAll();
    
}
