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

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class DatabaseLoader implements IProductoService, IUserService {

    @Autowired
    private PeliculasRepository peliculasRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    @Override
    public void initDatabase() {
     /*   
        // User #1: "user", with password "user1" and role "USER"
        GrantedAuthority[] userRoles = {new SimpleGrantedAuthority("ROLE_USER")};
        userRepository.save(new User("user", "user","user@user.es","ROLE_USER", Arrays.asList(userRoles)));

        // User #2: "root", with password "root1" and roles "USER" and "ADMIN"  
        GrantedAuthority[] adminRoles = {new SimpleGrantedAuthority("ROLE_USER"),
            new SimpleGrantedAuthority("ROLE_ADMIN")};
        userRepository.save(new User("admin", "admin","admin@admin.es","ROLE_ADMIN", Arrays.asList(adminRoles)));
         */
    }

    public ArrayList<User> findAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        for (User u : userRepository.findAll()) {
            users.add(u);
        }
        return users;
    }

    public User getOneUser(String user) {
        return userRepository.findOne(user);
    }

    public void edit(User user) {
        /*
        GrantedAuthority[] userRoles = {new SimpleGrantedAuthority("ROLE_USER")};
        userRepository.save(new User(user.getUser(), user.getPassword(), Arrays.asList(userRoles), user.getRol()));
         */
        GrantedAuthority[] userRoles = {new SimpleGrantedAuthority(user.getRol())};
        userRepository.save(new User(user.getUser(), user.getPassword(), user.getEmail(), user.getRol(), Arrays.asList(userRoles)));

    }

    /*
    public void save(User user, String roles) {
        //GrantedAuthority[] userRoles = {new SimpleGrantedAuthority("ROLE_USER")};
        GrantedAuthority[] userRoles = {new SimpleGrantedAuthority(roles)};
        userRepository.save(new User(user.getUser(), user.getPassword(), Arrays.asList(userRoles)));
        //userRepository.save(user);
    }*/
    public void save(User user) {
        /*String obtenido = user.getRol();
        String admin = "ROLE_ADMIN";
        String usuario = "ROLE_USER";
        System.out.println(obtenido);
        
        if (obtenido == usuario) {
            System.out.println("usuario");
            GrantedAuthority[] userRoles = {new SimpleGrantedAuthority("ROLE_USER")};
            userRepository.save(new User(user.getUser(), user.getPassword(),user.getRol(), Arrays.asList(userRoles)));
        } else if(obtenido == admin) {
            System.out.println("admin");
            GrantedAuthority[] adminRoles = {new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN")};
            userRepository.save(new User(user.getUser(), user.getPassword(),user.getRol(), Arrays.asList(adminRoles)));
        }else{
            System.out.println("Nada");
        }*/
        GrantedAuthority[] userRoles = {new SimpleGrantedAuthority(user.getRol())};
        userRepository.save(new User(user.getUser(), user.getPassword(),user.getEmail(), user.getRol(), Arrays.asList(userRoles)));

    }

}
