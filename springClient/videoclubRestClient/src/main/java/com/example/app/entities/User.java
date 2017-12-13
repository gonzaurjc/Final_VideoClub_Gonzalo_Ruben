/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.app.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Gonzalo
 */
@Entity
@NoArgsConstructor
//@JsonDeserialize(as = User.class)
public class User {

    @Id
    private String user;
    private String password;
    private String email;
    private String rol;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> roles;

    public User(String user, String password, String email, String rol, List<GrantedAuthority> roles) {
        this.user = user;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.email = email;
        this.rol = rol;
        this.roles = roles;

    }

    public String getPasswordHash() {
        return password;
    }

    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<GrantedAuthority> roles) {
        this.roles = roles;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
