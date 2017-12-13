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
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyInt;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 * @author Morante
 */
public class PeliculasControllerTest {

    private static final int CODIGO = 1;
    private static final String TITLE = "Tiburon";
    private static final String URLCONTENIDO = "https://www.youtube.com/watch?v=ycuUsbLkOII";
    private static final String PLOT = "Pelicula sobre un tiburon";
    private static final int YEAR = 1975;
    private static final String DIRECTOR = "Steven Spielberg";
    private static final String ACTORS = "Roy Scheider, Richard Dreyfuss, Robert Shaw, Lorraine Gary";
    private static final String POSTER = "https://i.ytimg.com/vi/qZw35DklLyA/hqdefault.jpg";
    private static final float IMDBRATING = 8.9f;

    private static final String USER = "Ruben";
    private static final String PASSWORD = "ruben";
    private static final String EMAIL = "ruben@email.com";

    @Mock
    private IPeliculaService peliculaService;

    @Mock
    private IUserService userService;

    @InjectMocks
    private PeliculasController peliculasController;

    private MockMvc mockMvc;

    private Pelicula pelicula;

    private User user;

    @Before
    public void init() {
        this.peliculasController = new PeliculasController();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.peliculasController).build();

        this.pelicula = new Pelicula();
        this.pelicula.setCodigo(PeliculasControllerTest.CODIGO);
        this.pelicula.setTitle(PeliculasControllerTest.TITLE);
        this.pelicula.setUrlContenido(PeliculasControllerTest.URLCONTENIDO);
        this.pelicula.setPlot(PeliculasControllerTest.PLOT);
        this.pelicula.setYear(PeliculasControllerTest.YEAR);
        this.pelicula.setDirector(PeliculasControllerTest.DIRECTOR);
        this.pelicula.setActors(PeliculasControllerTest.ACTORS);
        this.pelicula.setPoster(PeliculasControllerTest.POSTER);

        List<Pelicula> peliculaList = new ArrayList<>();
        peliculaList.add(this.pelicula);

        when(this.peliculaService.findAll()).then(answer -> {
            return peliculaList;
        });
        when(this.peliculaService.getPelicula(1)).then(answer -> {
            return this.pelicula;
        });
        when(this.peliculaService.getPelicula(3)).then(answer -> {
            return null;
        });

        this.user = new User();
        this.user.setUser(PeliculasControllerTest.USER);
        this.user.setPassword(PeliculasControllerTest.PASSWORD);
        this.user.setEmail(PeliculasControllerTest.EMAIL);

        List<User> userList = new ArrayList<>();
        userList.add(this.user);

        when(this.userService.findAllUsers()).then(answer -> {
            return userList;
        });
    }

    @Test
    public void getAllPeliculasTest() throws Exception {
        this.mockMvc.perform(get("/listado").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].title").value(PeliculasControllerTest.TITLE)).andExpect(status().isOk());

        verify(this.peliculaService, times(1)).findAll();
    }

    @Test
    public void getAllUsuariosTest() throws Exception {
        this.mockMvc.perform(get("/listadoUsuarios").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].user").value(PeliculasControllerTest.USER)).andExpect(status().isOk());

        verify(this.userService, times(1)).findAllUsers();
    }

    @Test
    public void getPeliculaTest() throws Exception {
        this.mockMvc.perform(get("/peliculaIndividual/1").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.codigo").value(PeliculasControllerTest.CODIGO)).andExpect(status().isOk());

        verify(this.peliculaService, times(1)).getPelicula(PeliculasControllerTest.CODIGO);

        this.mockMvc.perform(get("/peliculaIndividual/3").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void insertarTest() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"codigo\":\"").append(PeliculasControllerTest.CODIGO);
        sb.append("\",\"title\":\"").append(PeliculasControllerTest.TITLE);
        sb.append("\",\"urlContenido\":\"").append(PeliculasControllerTest.URLCONTENIDO);
        sb.append("\",\"plot\":\"").append(PeliculasControllerTest.PLOT);
        sb.append("\",\"year\":\"").append(PeliculasControllerTest.YEAR);
        sb.append("\",\"director\":\"").append(PeliculasControllerTest.DIRECTOR);
        sb.append("\",\"actors\":\"").append(PeliculasControllerTest.ACTORS);
        sb.append("\",\"poster\":\"").append(PeliculasControllerTest.POSTER);
        sb.append("\",\"imdbRating\":\"").append(PeliculasControllerTest.IMDBRATING);
        sb.append("\"}");

        String body = sb.toString();
        this.mockMvc.perform(post("/insertar").contentType(MediaType.APPLICATION_JSON).content(body));

        ArgumentCaptor<Pelicula> argumentCaptor = ArgumentCaptor.forClass(Pelicula.class);
        verify(this.peliculaService, times(1)).save(argumentCaptor.capture());

        assertTrue(argumentCaptor.getValue().getTitle().equals(PeliculasControllerTest.TITLE));
    }

    @Test
    public void insertarUsuarioTest() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"user\":\"").append(PeliculasControllerTest.USER);
        sb.append("\",\"password\":\"").append(PeliculasControllerTest.PASSWORD);
        sb.append("\",\"email\":\"").append(PeliculasControllerTest.EMAIL);
        sb.append("\"}");

        String body = sb.toString();
        this.mockMvc.perform(post("/insertarUsuario").contentType(MediaType.APPLICATION_JSON).content(body));

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<String> argumentCaptor2 = ArgumentCaptor.forClass(String.class);
        verify(this.userService, times(1)).save(argumentCaptor.capture(), argumentCaptor2.capture());

        assertTrue(argumentCaptor.getValue().getUser().equals(PeliculasControllerTest.USER));
    }

    @Test
    public void editarTest() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"codigo\":\"").append(PeliculasControllerTest.CODIGO);
        sb.append("\",\"title\":\"").append(PeliculasControllerTest.TITLE);
        sb.append("\",\"urlContenido\":\"").append(PeliculasControllerTest.URLCONTENIDO);
        sb.append("\",\"plot\":\"").append(PeliculasControllerTest.PLOT);
        sb.append("\",\"year\":\"").append(PeliculasControllerTest.YEAR);
        sb.append("\",\"director\":\"").append(PeliculasControllerTest.DIRECTOR);
        sb.append("\",\"actors\":\"").append(PeliculasControllerTest.ACTORS);
        sb.append("\",\"poster\":\"").append(PeliculasControllerTest.POSTER);
        sb.append("\",\"imdbRating\":\"").append(PeliculasControllerTest.IMDBRATING);
        sb.append("\"}");

        String body = sb.toString();
        this.mockMvc.perform(post("/editar").contentType(MediaType.APPLICATION_JSON).content(body));

        ArgumentCaptor<Pelicula> argumentCaptor = ArgumentCaptor.forClass(Pelicula.class);
        verify(this.peliculaService, times(1)).edit(argumentCaptor.capture());

        assertTrue(argumentCaptor.getValue().getTitle().equals(PeliculasControllerTest.TITLE));
    }

    @Test
    public void eliminarUsuarioTest() throws Exception {
        this.mockMvc.perform(get("/eliminarUsuario/" + PeliculasControllerTest.USER));
        verify(this.userService, times(1)).eliminarUsuario(PeliculasControllerTest.USER);
    }

    @Test
    public void deletePeliculaTest() throws Exception {
        this.mockMvc.perform(get("/delete/1"));
        verify(this.peliculaService, times(1)).delete(PeliculasControllerTest.CODIGO);
    }
}
