/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.app.controllers;

import com.example.app.configuration.IPeliculaService;
import com.example.app.configuration.IUserService;
import com.example.app.entities.Pelicula;
import com.example.app.entities.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Gonzalo
 */
@RestController
public class PeliculasController {

    @Autowired
    private IPeliculaService peliculaService;

    @Autowired
    private IUserService userService;

    //OBTENER TODOS LAS PELICULAS
    @RequestMapping(value = "/listado", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Pelicula> getAllPeliculas() {
        return peliculaService.findAll();
    }

    //OBTENER TODOS LAS USUARIOS
    @RequestMapping(value = "/listadoUsuarios", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsuarios() {
        return userService.findAllUsers();
    }

    //OBTENER UNA SOLA PELICULA POR CODIGO
    @RequestMapping(value = "/peliculaIndividual/{codigo}", method = RequestMethod.GET)
    public ResponseEntity<Pelicula> getPelicula(@PathVariable int codigo) {
        Pelicula pelicula = peliculaService.getPelicula(codigo);
        ResponseEntity<Pelicula> response;
        if (pelicula == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(pelicula, HttpStatus.OK);
        }
        return response;
    }


    //INSERTAR UNA PELICULA
    @RequestMapping(value = "/insertar", method = RequestMethod.POST)
    public ResponseEntity<Boolean> insertar(@RequestBody Pelicula pelicula) {
        peliculaService.save(pelicula);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    //INSERTAR UN USUARIO
    @RequestMapping(value = "/insertarUsuario", method = RequestMethod.POST)
    public ResponseEntity<Boolean> insertarUsuario(@RequestBody User user, String rol) {
        userService.save(user,rol);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    //EDITAR UNA PELICULA
    @RequestMapping(value = "/editar", method = RequestMethod.POST)
    public ResponseEntity<Boolean> editar(@RequestBody Pelicula pelicula) {
        peliculaService.edit(pelicula);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    //ELIMINAR UNA PELICULA
    @RequestMapping(value = "/delete/{codigo}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePelicula(@PathVariable int codigo) {
        peliculaService.delete(codigo);
    }

    //ELIMINAR UN USUARIO
    @RequestMapping(value = "/eliminarUsuario/{user}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarUsuario(@PathVariable String user) {
        userService.eliminarUsuario(user);
    }
    

}
