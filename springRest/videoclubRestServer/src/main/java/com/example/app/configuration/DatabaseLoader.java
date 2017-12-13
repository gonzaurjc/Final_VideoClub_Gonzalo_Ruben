/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.app.configuration;

/**
 *
 * @author Gonzalo
 */
import com.example.app.entities.Pelicula;
import com.example.app.entities.User;
import com.example.app.peliculas.PeliculasRepository;
import com.example.app.users.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class DatabaseLoader implements IPeliculaService, IUserService {

    @Autowired
    private PeliculasRepository peliculasRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    @Override
    public void initDatabase() {
        /* empty */
    }

    @Override
    public void save(Pelicula pelicula) {
        peliculasRepository.save(pelicula);
    }

    @Override
    public void delete(int codigo) {
        peliculasRepository.delete(codigo);
    }

    @Override
    public Pelicula getPelicula(int codigo) {
        return peliculasRepository.findOne(codigo);
    }

    @Override
    public void edit(Pelicula pelicula) {
        peliculasRepository.save(pelicula);
    }

    @Override
    public List<Pelicula> findAll() {
        ArrayList<Pelicula> peliculas = new ArrayList<>();

        for (Pelicula p : peliculasRepository.findAll()) {
            peliculas.add(p);
        }
        return peliculas;
    }

    @Override
    public void save(User user, String rol) {
        GrantedAuthority[] userRoles = {new SimpleGrantedAuthority(rol)};
        userRepository.save(new User(user.getUser(), user.getPassword(), user.getEmail(), Arrays.asList(userRoles)));
    }
    
    @Override
    public List<User> findAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        for (User u : userRepository.findAll()) {
            users.add(u);
        }
        return users;
    }
    
    @Override
    public void eliminarUsuario(String user) {
        userRepository.delete(user);
    }
    
    @Override
    public void editUser(User user) {
        userRepository.save(user);
    }
    
}
