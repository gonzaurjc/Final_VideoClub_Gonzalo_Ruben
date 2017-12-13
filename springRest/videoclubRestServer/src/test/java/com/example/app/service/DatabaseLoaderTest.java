/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.app.service;

import com.example.app.configuration.DatabaseLoader;
import com.example.app.entities.Pelicula;
import com.example.app.entities.User;
import com.example.app.peliculas.PeliculasRepository;
import com.example.app.users.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Lists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Morante
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseLoaderTest {

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
    private PeliculasRepository peliculasRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DatabaseLoader databaseLoader = new DatabaseLoader();

    private Pelicula pelicula;

    private User user;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        this.pelicula = new Pelicula();
        this.pelicula.setCodigo(DatabaseLoaderTest.CODIGO);
        this.pelicula.setTitle(DatabaseLoaderTest.TITLE);
        this.pelicula.setUrlContenido(DatabaseLoaderTest.URLCONTENIDO);
        this.pelicula.setPlot(DatabaseLoaderTest.PLOT);
        this.pelicula.setYear(DatabaseLoaderTest.YEAR);
        this.pelicula.setDirector(DatabaseLoaderTest.DIRECTOR);
        this.pelicula.setActors(DatabaseLoaderTest.ACTORS);
        this.pelicula.setPoster(DatabaseLoaderTest.POSTER);

        List<Pelicula> peliculaList = new ArrayList<>();
        peliculaList.add(this.pelicula);

        when(this.peliculasRepository.findAll()).then(answer -> {
            return peliculaList;
        });
        when(this.peliculasRepository.findOne(1)).then(answer -> {
            return this.pelicula;
        });
        when(this.peliculasRepository.findOne(3)).then(answer -> {
            return null;
        });

        this.user = new User();
        this.user.setUser(DatabaseLoaderTest.USER);
        this.user.setPassword(DatabaseLoaderTest.PASSWORD);
        this.user.setEmail(DatabaseLoaderTest.EMAIL);

        List<User> userList = new ArrayList<>();
        userList.add(this.user);

        when(this.userRepository.findAll()).then(answer -> {
            return userList;
        });
    }
    
    @Test
    public void initDatabaseTest() throws Exception {
        this.databaseLoader.initDatabase();
    }

    @Test
    public void savePeliculaTest() throws Exception {
        List<Pelicula> peliculaList = Lists.newArrayList(this.databaseLoader.findAll());
        when(this.peliculasRepository.findAll()).thenReturn(peliculaList);

        int size = peliculaList.size();
        assertEquals(peliculaList.size(), size);

        Pelicula pelicula2 = new Pelicula();

        this.databaseLoader.save(pelicula2);
        peliculaList.add(pelicula2);

        ArgumentCaptor<Pelicula> argumentCaptor = ArgumentCaptor.forClass(Pelicula.class);
        verify(this.peliculasRepository, times(1)).save(argumentCaptor.capture());

        assertEquals(pelicula2, argumentCaptor.getValue());
        assertFalse(Lists.newArrayList(this.databaseLoader.findAll()).size() == size);

        size++;
        assertEquals(Lists.newArrayList(this.databaseLoader.findAll()).size(), size);
    }

    @Test
    public void deleteTest() throws Exception {
        List<Pelicula> peliculaList = Lists.newArrayList(this.databaseLoader.findAll());
        when(this.peliculasRepository.findAll()).thenReturn(peliculaList);

        int num = 1;
        int size = peliculaList.size();

        this.databaseLoader.delete(num);
        peliculaList.remove(0);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(this.peliculasRepository).delete(argumentCaptor.capture());

        assertTrue(num == argumentCaptor.getValue());

        assertFalse(Lists.newArrayList(this.databaseLoader.findAll()).size() == size);

        size--;
        assertEquals(Lists.newArrayList(this.databaseLoader.findAll()).size(), size);
    }

    @Test
    public void getPeliculaTest() throws Exception {
        int num = 1;

        Pelicula pelicula1 = this.databaseLoader.getPelicula(num);
        verify(this.peliculasRepository).findOne(num);

        assertTrue(pelicula1.getCodigo() == DatabaseLoaderTest.CODIGO);
        assertTrue(pelicula1.getTitle().equals(DatabaseLoaderTest.TITLE));
        assertTrue(pelicula1.getUrlContenido().equals(DatabaseLoaderTest.URLCONTENIDO));
        assertTrue(pelicula1.getPlot().equals(DatabaseLoaderTest.PLOT));
        assertTrue(pelicula1.getYear() == DatabaseLoaderTest.YEAR);
        assertTrue(pelicula1.getDirector().equals(DatabaseLoaderTest.DIRECTOR));
        assertTrue(pelicula1.getActors().equals(DatabaseLoaderTest.ACTORS));
        assertTrue(pelicula1.getPoster().equals(DatabaseLoaderTest.POSTER));
    }

    @Test
    public void editTest() throws Exception {
        List<Pelicula> peliculaList = Lists.newArrayList(this.databaseLoader.findAll());
        when(this.peliculasRepository.findAll()).thenReturn(peliculaList);

        int size = peliculaList.size();
        assertEquals(peliculaList.size(), size);

        Pelicula pelicula2 = new Pelicula();

        this.databaseLoader.edit(pelicula2);
        peliculaList.add(pelicula2);

        ArgumentCaptor<Pelicula> argumentCaptor = ArgumentCaptor.forClass(Pelicula.class);
        verify(this.peliculasRepository, times(1)).save(argumentCaptor.capture());

        assertEquals(pelicula2, argumentCaptor.getValue());
        assertFalse(Lists.newArrayList(this.databaseLoader.findAll()).size() == size);

        size++;
        assertEquals(Lists.newArrayList(this.databaseLoader.findAll()).size(), size);
    }

    @Test
    public void findAllTest() throws Exception {
        assertTrue(Lists.newArrayList(this.databaseLoader.findAll()).size() == 1);
        verify(this.peliculasRepository).findAll();
    }

    @Test
    public void saveUserTest() throws Exception {
        List<User> userList = Lists.newArrayList(this.databaseLoader.findAllUsers());
        when(this.userRepository.findAll()).thenReturn(userList);

        int size = userList.size();
        assertEquals(userList.size(), size);

        User user2 = new User("Gonzalo", "gonzalo", "gonzalo@email.com", null);

        this.databaseLoader.save(user2, "USER");
        userList.add(user2);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(this.userRepository, times(1)).save(argumentCaptor.capture());

        assertFalse(Lists.newArrayList(this.databaseLoader.findAllUsers()).size() == size);

        size++;
        assertEquals(Lists.newArrayList(this.databaseLoader.findAllUsers()).size(), size);
    }

    @Test
    public void findAllUsersTest() throws Exception {
        assertTrue(Lists.newArrayList(this.databaseLoader.findAllUsers()).size() == 1);
        verify(this.userRepository).findAll();
    }

    @Test
    public void eliminarUsuarioTest() throws Exception {
        List<User> userList = Lists.newArrayList(this.databaseLoader.findAllUsers());
        when(this.userRepository.findAll()).thenReturn(userList);

        String name = DatabaseLoaderTest.USER;
        int size = userList.size();

        this.databaseLoader.eliminarUsuario(name);
        userList.remove(0);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.userRepository).delete(argumentCaptor.capture());

        assertTrue(name.equals(argumentCaptor.getValue()));

        assertFalse(Lists.newArrayList(this.databaseLoader.findAllUsers()).size() == size);

        size--;
        assertEquals(Lists.newArrayList(this.databaseLoader.findAllUsers()).size(), size);
    }

    @Test
    public void editUserTest() throws Exception {
        List<User> userList = Lists.newArrayList(this.databaseLoader.findAllUsers());
        when(this.userRepository.findAll()).thenReturn(userList);

        int size = userList.size();
        assertEquals(userList.size(), size);

        User user2 = new User("Gonzalo", "gonzalo", "gonzalo@email.com", null);

        this.databaseLoader.editUser(user2);
        userList.add(user2);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(this.userRepository, times(1)).save(argumentCaptor.capture());

        assertFalse(Lists.newArrayList(this.databaseLoader.findAllUsers()).size() == size);

        size++;
        assertEquals(Lists.newArrayList(this.databaseLoader.findAllUsers()).size(), size);
    }
}
