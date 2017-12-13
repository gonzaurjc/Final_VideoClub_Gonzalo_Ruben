/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.app.peliculas;

import com.example.app.entities.Pelicula;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Gonzalo
 */
public interface PeliculasRepository extends CrudRepository<Pelicula, Integer> {
    
}
