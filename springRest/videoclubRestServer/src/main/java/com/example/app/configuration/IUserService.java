/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.app.configuration;

import com.example.app.entities.User;
import java.util.List;

/**
 *
 * @author Gonzalo
 */
public interface IUserService {   
    public void save(User user,String rol);
    public List<User> findAllUsers();
    public void eliminarUsuario(String user);
    public void editUser(User user);
}
