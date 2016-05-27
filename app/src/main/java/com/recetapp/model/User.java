package com.recetapp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 26/05/2016.
 */
public class User {
    private String id;
    private String nombre;

    public User(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
    }

    private List<String> recipes;

    public void setId(String id) {
        this.id = id;
    }

    public void setRecipes(List<String> recipes) {
        this.recipes = recipes;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public List<String> getRecipes() {
        return recipes;
    }
}
