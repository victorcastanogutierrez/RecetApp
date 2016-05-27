package com.recetapp.model;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Victor on 26/05/2016.
 */
public class Recipe {
    private String user;

    private String nombre;
    private String descripcion;

    public Recipe(String user, String nombre, String descripcion) {
        this.user = user;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getUser() {
        return user;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setUser(String user) {

        this.user = user;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("user", user);
        result.put("nombre", nombre);
        result.put("descripcion", descripcion);
        return result;
    }
}
