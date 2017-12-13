/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.app.controllers;

/**
 *
 * @author Gonzalo
 */
import com.example.app.configuration.IUserService;
import com.example.app.entities.Pelicula;
import com.example.app.entities.User;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

@Controller
public class PeliculasController implements ErrorController {

    @Autowired
    private static final String URL_API_PELICULAS = "http://localhost:8080";

    @Autowired
    private IUserService userService;

    /**
     * Denied capture
     */
    @RequestMapping(value = "/denied")
    public String denied() {
        return "denied";
    }

    /**
     * Error capture
     */
    @RequestMapping(value = "/error")
    public String error() {
        return "error";
    }

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }


    /*
    PARTES DEL MENÚ
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView model = new ModelAndView("home");
        return model;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/root")
    public ModelAndView root() {
        return new ModelAndView("root");
    }

    /*
    PARTES PRODUCTOS
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/insertar")
    public ModelAndView ins(Pelicula pelicula) {
        return new ModelAndView("insertar");
    }

    @PostMapping("/insertar")
    public String insertar(@Valid Pelicula pelicula) {
        RestTemplate restTemplate = new RestTemplate();
        String url_insertar = "http://localhost:8080/insertar";
        restTemplate.postForObject(url_insertar, pelicula, String.class);
        return "/home";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/listado")
    public ModelAndView listado() {
        String url_listado = "http://localhost:8080/listado";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Pelicula[]> responseEntity = restTemplate.getForEntity(url_listado, Pelicula[].class);
        Pelicula[] peliculas = responseEntity.getBody();

        return new ModelAndView("listado").addObject("peliculas", peliculas);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/peliculaIndividual")
    public ModelAndView peliculaIndividual(@RequestParam int codigo, String Title) {
        RestTemplate restTemplate = new RestTemplate();
        //System.out.println(codigo);
        //AQUI ES DONDE SE PASA EL XML AL OBJETO
        //PARA CONSULTAR POR TITULO
        //http://www.omdbapi.com/?t=hola
        Pelicula pelicula = restTemplate.getForObject("http://localhost:8080/peliculaIndividual/" + codigo, Pelicula.class);
        Object film = restTemplate.getForObject("http://www.omdbapi.com/?t=" + Title + "&apikey=434e31cd", Object.class);
        //	          System.out.println(film);
        return new ModelAndView("peliculaIndividual").addObject("film", film).addObject("pelicula", pelicula);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/busquedaPelicula")
    public ModelAndView busquedaPelicula(@RequestParam String Title) {
        RestTemplate restTemplate = new RestTemplate();
        //System.out.println(codigo);
        //AQUI ES DONDE SE PASA EL XML AL OBJETO
        //PARA CONSULTAR POR TITULO
        //http://www.omdbapi.com/?t=hola
        //Pelicula pelicula = restTemplate.getForObject("http://localhost:8080/peliculaIndividual/"+codigo , Pelicula.class);
        Object film = restTemplate.getForObject("http://www.omdbapi.com/?t=" + Title + "&apikey=434e31cd", Object.class);
        //	          System.out.println(film);
        return new ModelAndView("busquedaPelicula").addObject("film", film);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/delete")
    public ModelAndView delete(@RequestParam int codigo) {
        RestTemplate restTemplate = new RestTemplate();
        //System.out.println(codigo);
        restTemplate.getForObject("http://localhost:8080/delete/" + codigo, Pelicula.class);
        
        String url_listado = "http://localhost:8080/listado";        
        ResponseEntity<Pelicula[]> responseEntity = restTemplate.getForEntity(url_listado, Pelicula[].class);
        Pelicula[] peliculas = responseEntity.getBody();

        return new ModelAndView("listado").addObject("peliculas", peliculas);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/editar")
    public ModelAndView getPeliculaeditar(@RequestParam int codigo) {
        RestTemplate restTemplate = new RestTemplate();
        //System.out.println(codigo);
        Pelicula pelicula = restTemplate.getForObject("http://localhost:8080/peliculaIndividual/" + codigo, Pelicula.class);

        return new ModelAndView("editar").addObject("pelicula", pelicula);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/editar")
    public ModelAndView editar1(@Valid Pelicula pelicula) {
        RestTemplate restTemplate = new RestTemplate();
        String url_insertar = "http://localhost:8080/editar";
        String response = restTemplate.postForObject(url_insertar, pelicula, String.class);
        
        String url_listado = "http://localhost:8080/listado";       
        ResponseEntity<Pelicula[]> responseEntity = restTemplate.getForEntity(url_listado, Pelicula[].class);
        Pelicula[] peliculas = responseEntity.getBody();

        return new ModelAndView("listado").addObject("peliculas", peliculas);
    }

    /*
    PARTE USUARIOS
     */
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/listadoUsuarios")
    public ModelAndView listadoUsuarios() {
        return new ModelAndView("listadoUsuarios").addObject("users", this.userService.findAllUsers()).addObject("mismoUsuario", false);
    }

    /*@PostMapping("/insertarUsuario")
    public String insertarUsuario(@Valid User user, String rol) {
        RestTemplate restTemplate1 = new RestTemplate();
        String url_insertar_usuario = "http://localhost:8080/insertarUsuario";
        String response = restTemplate1.postForObject(url_insertar_usuario, user, String.class);
        return "redirect:http://localhost:1234/home";
    }*/
    @Secured("ROLE_ADMIN")
    @GetMapping("/insertarUsuario")
    public ModelAndView insUs() {
        return new ModelAndView("insertarUsuario").addObject("usuarioExiste", false);
    }

    //INSERTAR UN USUARIO
    @RequestMapping("/insertarUsuario")
    public ModelAndView insertarUsuario(User user) {
        String nameUSer = user.getUser();
        User userAux = userService.getOneUser(nameUSer);
        if(userService.getOneUser(nameUSer) != null){
            return new ModelAndView("insertarUsuario").addObject("usuarioExiste", true);
        }
        userService.save(user);
        return new ModelAndView("listadoUsuarios").addObject("users", this.userService.findAllUsers()).addObject("mismoUsuario", false);
        //return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/userIndividual")
    public String userIndividual(Model model, String user) {
        model.addAttribute("us", this.userService.getOneUser(user));
        return "userIndividual";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/eliminarUsuario")
    public ModelAndView eliminarUsuario(@RequestParam String user) {
        RestTemplate restTemplate = new RestTemplate();
        //System.out.println(codigo);
        if(user.equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return new ModelAndView("listadoUsuarios").addObject("users", this.userService.findAllUsers()).addObject("mismoUsuario", true);
        }        
        User u = restTemplate.getForObject("http://localhost:8080/eliminarUsuario/" + user, User.class);
        
        return new ModelAndView("listadoUsuarios").addObject("users", this.userService.findAllUsers()).addObject("mismoUsuario", false);
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/editarUser")
    public String editarUser(Model model, String user) {
        model.addAttribute("us", this.userService.getOneUser(user));
        return "editarUser";
    }

    /*@RequestMapping(value = "/editarUs")
    public String editarUs(@Valid User user) {
        userService.edit(user);
        //return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
        return "redirect:http://localhost:1234/home";
    }*/
    @RequestMapping(value = "/editarUs")
    public String editar(User user) {
        userService.edit(user);
        return "/home";

    }

    /**
     * override template Error
     */
    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return "/error";
    }
}
