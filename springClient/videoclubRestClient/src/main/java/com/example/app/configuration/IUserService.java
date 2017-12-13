/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.app.configuration;

import com.example.app.entities.User;
import java.util.ArrayList;

/**
 *
 * @author Gonzalo
 */
public interface IUserService {   
    public ArrayList<User> findAllUsers();
    public User getOneUser(String user);
    public void edit(User user);
    public void save(User user);
}
